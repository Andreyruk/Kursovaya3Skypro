package pro.sky.kursovaya3skypro.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Общие утилиты
 */
@UtilityClass
public class CommonUtils {

    /**
     * Получение списка объектов из строки
     * @param jsonString строка json
     * @param type тип объекта
     * @param <T> наименование типа объектов
     * @return Список объектов
     */
    public <T> List<T> objectFromString(String jsonString, Class<T> type) {
        List<T> obj;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.registerModule(new JavaTimeModule());
            CollectionType listType =
                    objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            obj = objectMapper.readValue(jsonString, listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * Конвертация объекта в строку json
     * @param obj объект
     * @param <T> тип объекта
     * @return строка json
     */
    public <T> String objectToString(T obj) {
        String jsonString;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writer().withDefaultPrettyPrinter();
        try {
            jsonString = objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать json");
        }
        return jsonString;
    }
}
