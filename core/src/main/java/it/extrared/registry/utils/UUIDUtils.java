package it.extrared.registry.utils;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class UUIDUtils {

    private static final TimeBasedGenerator TIME_GENERATOR = Generators.timeBasedGenerator();

    private static final RandomBasedGenerator RANDOM_GENERATOR = Generators.randomBasedGenerator();

    public static String generateTimeBasedUUID() {
        return TIME_GENERATOR.generate().toString();
    }
}
