package pro.sky.kursovaya3skypro.model;

import pro.sky.kursovaya3skypro.enums.Colors;
import pro.sky.kursovaya3skypro.enums.Sizes;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Носки
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Socks {
    /**
     * Цвет
     */
    private Colors color;
    /**
     * Размер
     */
    private Sizes size;
    /**
     * Процентное содержание хлопка
     */
    private int cottonPart;
}
