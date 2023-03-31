package pro.sky.kursovaya3skypro.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Размеры
 */
public enum Sizes {
    /**
     * Размер 10
     */
    SIZE10(10),
    /**
     * Размер 20
     */
    SIZE20(20),
    /**
     * Размер 30
     */
    SIZE30(30);

    private final int size;

    Sizes(int size) {
        this.size = size;
    }
}
