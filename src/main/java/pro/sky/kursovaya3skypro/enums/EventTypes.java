package pro.sky.kursovaya3skypro.enums;

/**
 * Типы событий
 */
public enum EventTypes {
    /**
     * Поступление
     */
    ADDITION("Поступление"),
    /**
     * Выдача товара
     */
    CONSUMPTION("Выдача товара"),
    /**
     * Списание брака
     */
    WRITEOFF("Списание брака");

    private final String description;

    EventTypes(String description) {
        this.description = description;
    }
}
