package com.urise.webapp.util;

public class StringUtil {

    /**
     * Используется для сериализации где недопуcтимо передавать пустую строку
     * возвращает строку "null" если строка равна null
     * иначе возвращает строку без изменений
     * @param string - входная строка
     * @return - возвращаемое значение
     */
    public static String write(String string) {
        return string == null ? "null" : string;
    }

    /**
     * Используется для десериализации где необхрдимо явно указать пустое значение
     * возвращает  null если строка равна "null"
     * иначе возвращает строку без изменений
     * @param string - входная строка
     * @return - возвращаемое значение
     */
    public static String read(String string) {
        return string.equals("null") ? null : string;
    }
}
