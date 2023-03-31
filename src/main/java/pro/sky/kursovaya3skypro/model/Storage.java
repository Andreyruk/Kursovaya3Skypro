package pro.sky.kursovaya3skypro.model;

import pro.sky.kursovaya3skypro.enums.EventTypes;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Склад
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "storage", schemaVersion= "1.0")
public class Storage {
    /**
     * Идентификатор
     */
    @Id
    private int id;
    /**
     * Носки
     */
    private Socks socks;
    /**
     * Дата и время операции
     */
    private LocalDateTime operationDate;
    /**
     * Тип события
     */
    private EventTypes eventType;
    /**
     * Количество
     */
    private int amount;
}
