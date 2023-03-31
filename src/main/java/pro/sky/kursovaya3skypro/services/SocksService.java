package pro.sky.kursovaya3skypro.services;

import pro.sky.kursovaya3skypro.enums.Colors;
import pro.sky.kursovaya3skypro.enums.EventTypes;
import pro.sky.kursovaya3skypro.enums.Sizes;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * Сервис для работы с носками
 */
public interface SocksService {
    /**
     * Добавление носков
     *
     * @param color         цвет
     * @param size          размер
     * @param cottonPart    содержание хлопка
     * @param quantity      количество
     * @param eventType     тип события
     * @param operationDate дата и время операции
     * @return Идентификатор созданной пары
     */
    int addSocks(Colors color, Sizes size, int cottonPart, int quantity, EventTypes eventType, LocalDateTime operationDate);

    /**
     * Получение количества носков на складе по параметрам
     * @param color цвет
     * @param size размер
     * @param cottonMin минимальное содержание хлопка
     * @param cottonMax максимальное содержание хлопка
     * @return Найденное количество
     */
    Long getAmountSocks(Colors color, Sizes size, Integer cottonMin, Integer cottonMax);

    /**
     * Выдача носков по параметрам
     * @param color цвет
     * @param size размер
     * @param cottonPart содержание хлопка в товаре
     * @param quantity количество
     */
    void consumptionSocks(Colors color, Sizes size, int cottonPart, int quantity);
    /**
     * Списание брака по параметрам
     * @param color цвет
     * @param size размер
     * @param cottonPart содержание хлопка
     * @param quantity количество
     */
    void removeSocks(Colors color, Sizes size, int cottonPart, int quantity);

    /**
     * Скачивание json
     * @return byte[]
     */
    byte[] downloadSocks();

    /**
     * Загрузка json
     * @param file файл json
     */
    void upload(MultipartFile file);
}
