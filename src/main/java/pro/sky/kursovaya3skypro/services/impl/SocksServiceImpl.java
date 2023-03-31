package pro.sky.kursovaya3skypro.services.impl;

import pro.sky.kursovaya3skypro.enums.Colors;
import pro.sky.kursovaya3skypro.enums.EventTypes;
import pro.sky.kursovaya3skypro.enums.Sizes;
import pro.sky.kursovaya3skypro.model.Socks;
import pro.sky.kursovaya3skypro.model.Storage;
import pro.sky.kursovaya3skypro.services.SocksService;
import pro.sky.kursovaya3skypro.utils.CommonUtils;
import io.jsondb.JsonDBTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SocksServiceImpl implements SocksService {
    private final JsonDBTemplate jsonDBTemplate;
    private static int id;
    @Override
    public int addSocks(Colors color, Sizes size, int cottonPart, int quantity, EventTypes eventType, LocalDateTime operationDate) {
        if (!(cottonPart >= 0 && cottonPart <= 100))  {
            throw new RuntimeException("Не допустимое содержание хлопка");
        }
        List<Storage> collection = jsonDBTemplate.getCollection(Storage.class);
        if (!collection.isEmpty()) {
//            jsonDBTemplate.createCollection(Socks.class);
            Storage current = collection.get(collection.size()-1);
            if (current != null) {
                id = current.getId();
            }
        }

        Socks socks = new Socks();
        socks.setColor(color);
        socks.setSize(size);
        socks.setCottonPart(cottonPart);
        Storage storage = new Storage();
        storage.setId(++id);
        storage.setSocks(socks);
        if (operationDate == null) {
            storage.setOperationDate(LocalDateTime.now());
        } else {
            storage.setOperationDate(operationDate);
        }
        storage.setEventType(eventType);
        storage.setAmount(quantity);

        jsonDBTemplate.insert(storage);
        return id;
    }

    @Override
    public Long getAmountSocks(Colors color, Sizes size, Integer cottonMin, Integer cottonMax) {
        long count = 0;
        List<Storage> list = getStorage(color, size, cottonMin, cottonMax);

        for (Storage item : list) {
            count += item.getAmount();
        }
        return count;
    }

    private List<Storage> getStorage(Colors color, Sizes size, Integer cottonMin, Integer cottonMax) {
        List<Storage> collection = jsonDBTemplate.getCollection(Storage.class);
        if (!collection.isEmpty()) {
            List<String> queryList = new ArrayList<>();
            if (color != null) {
                queryList.add(String.format("socks[color='%s']", color));
            }
            if (size != null) {
                queryList.add(String.format("socks[size='%s']", size));
            }
            if (cottonMin != null) {
                queryList.add(String.format("socks[cottonPart>='%s']", cottonMin));
            }
            if (cottonMax != null) {
                queryList.add(String.format("socks[cottonPart<='%s']", cottonMax));
            }
            String jxQuery = String.format("/.%s", !queryList.isEmpty()?"["+String.join(" and ", queryList)+"]":"");
            return jsonDBTemplate.find(jxQuery, Storage.class);
        }
        return Collections.emptyList();
    }

    @Override
    public void consumptionSocks(Colors color, Sizes size, int cottonPart, int quantity) {
        checkAmount(color,size,cottonPart,quantity);
        addSocks(color, size, cottonPart,quantity*-1, EventTypes.CONSUMPTION, null);
    }

    @Override
    public void removeSocks(Colors color, Sizes size, int cottonPart, int quantity) {
        checkAmount(color,size,cottonPart,quantity);
        addSocks(color, size, cottonPart,quantity*-1, EventTypes.WRITEOFF, null);
    }

    private void checkAmount(Colors color, Sizes size, int cottonPart, int quantity) {
        if (getAmountSocks(color,size,cottonPart,cottonPart) < quantity) {
            throw new RuntimeException("На складе недостаточно товара");
        }
    }

    @Override
    public byte[] downloadSocks() {
        List<Storage> list = getStorage(null, null, null, null);
        String str = CommonUtils.objectToString(list);
        return str.getBytes();
    }

    @Override
    public void upload(MultipartFile file) {
        if (file != null) {
            try {
                byte[] fileBytes = file.getBytes();
                String s = new String(fileBytes, StandardCharsets.UTF_8);
                List<Storage> collection = jsonDBTemplate.getCollection(Storage.class);
                if (!collection.isEmpty()) {
                    jsonDBTemplate.findAllAndRemove("/.", Storage.class);
                }

                List<Storage> list = CommonUtils.objectFromString(s, Storage.class);
                list.forEach(item -> {
                    addSocks(item.getSocks().getColor(), item.getSocks().getSize(), item.getSocks().getCottonPart(),item.getAmount(), item.getEventType(), item.getOperationDate());
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
