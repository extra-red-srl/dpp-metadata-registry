package it.extrared.registry.utils;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/** UUID related utils methods. */
public class CommonUtils {

    private static final TimeBasedGenerator TIME_GENERATOR = Generators.timeBasedGenerator();

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * @return a time based UUID.
     */
    public static String generateTimeBasedUUID() {
        return TIME_GENERATOR.generate().toString();
    }
}
