package org.carcinus.tools.utils;


import org.carcinus.tools.exception.EnumConstantNotFountException;

public class EnumUtils {

    public static <T extends Enum<T>> T parseIgnoreCase(Class<T> clazz, String enumName) throws EnumConstantNotFountException {
        T[] constants = clazz.getEnumConstants();
        if (enumName == null) {
            return null;
        }
        for (T t : constants) {
            if (t.name().equalsIgnoreCase(enumName)) {
                return t;
            }
        }
        throw new EnumConstantNotFountException(clazz, enumName);
    }
}
