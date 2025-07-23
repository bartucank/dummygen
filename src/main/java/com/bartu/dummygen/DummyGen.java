package com.bartu.dummygen;

import com.bartu.dummygen.config.DummyConfig;

/**
 * DummyGen - A zero-dependency Java library to generate meaningful dummy data for any POJO class.
 * 
 * @author Bartu Can Palamut
 * @version 1.0.0
 */
public class DummyGen {

    private static final FieldPopulator fieldPopulator = new FieldPopulator();

    /**
     * Generate a dummy object of the specified class with default configuration.
     * Default: English language, meaningful content enabled.
     * 
     * @param <T> the type of the class
     * @param clazz the class to generate dummy data for
     * @return a new instance of the class populated with dummy data
     */
    public static <T> T generate(Class<T> clazz) {
        return generate(clazz, DummyConfig.defaultConfig());
    }

    /**
     * Generate a dummy object of the specified class with custom configuration.
     * 
     * @param <T> the type of the class
     * @param clazz the class to generate dummy data for
     * @param config the configuration to use for generation
     * @return a new instance of the class populated with dummy data
     */
    public static <T> T generate(Class<T> clazz, DummyConfig config) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        if (config == null) {
            config = DummyConfig.defaultConfig();
        }

        return fieldPopulator.populate(clazz, config);
    }
}
