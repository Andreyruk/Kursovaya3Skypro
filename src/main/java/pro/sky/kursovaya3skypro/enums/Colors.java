package pro.sky.kursovaya3skypro.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Цвета
 */
public enum Colors {
    /**
     * черный
     */
    BLACK("черный"),
    /**
     * желтый
     */
    YELLOW("желтый"),
    /**
     * красный
     */
    RED("красный"),
    /**
     * белый
     */
    WHITE("белый");

    private final String description;

    Colors(String description) {
        this.description = description;
    }
}
