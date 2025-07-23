package com.bartu.dummygen;

import com.bartu.dummygen.config.DummyConfig;
import com.bartu.dummygen.generator.DateGenerator;
import com.bartu.dummygen.generator.NumberGenerator;
import com.bartu.dummygen.generator.StringGenerator;
import com.bartu.dummygen.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Handles the population of fields in objects with dummy data.
 * 
 * This class is responsible for analyzing object fields using reflection,
 * determining appropriate data types, and generating meaningful dummy values
 * based on field names and types. It supports nested objects, collections,
 * enums, and various primitive and wrapper types.
 * 
 * @author Bartu Can Palamut
 * @since 1.0.0
 */
class FieldPopulator {

    private final StringGenerator stringGenerator;
    private final NumberGenerator numberGenerator;
    private final DateGenerator dateGenerator;
    private final Random random;

    public FieldPopulator() {
        this.stringGenerator = new StringGenerator();
        this.numberGenerator = new NumberGenerator();
        this.dateGenerator = new DateGenerator();
        this.random = new Random();
    }

    /**
     * Populate an object of the given class with dummy data.
     * 
     * This method creates a new instance of the specified class and populates
     * all non-static, non-final fields with appropriate dummy data. The method
     * uses reflection to analyze field types and names to generate contextually
     * appropriate values.
     * 
     * @param <T> the type of the class
     * @param clazz the class to create and populate
     * @param config the configuration for data generation (language, content type, etc.)
     * @return a populated instance of the class
     * @throws RuntimeException if the class cannot be instantiated or populated
     */
    @SuppressWarnings("unchecked")
    public <T> T populate(Class<T> clazz, DummyConfig config) {
        try {
            T instance = ReflectionUtils.createInstance(clazz);
            
            Field[] fields = ReflectionUtils.getAllFields(clazz);
            
            for (Field field : fields) {
                if (shouldSkipField(field)) {
                    continue;
                }
                
                Object value = generateValueForField(field, config);
                
                if (value != null || !config.isAllowNullFields()) {
                    ReflectionUtils.setFieldValue(field, instance, value);
                }
            }
            
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to populate class: " + clazz.getName(), e);
        }
    }

    /**
     * Determines whether a field should be skipped during population.
     * Fields are skipped if they are static or final.
     * 
     * @param field the field to check
     * @return true if the field should be skipped, false otherwise
     */
    private boolean shouldSkipField(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
    }

    /**
     * Generates an appropriate value for a field based on its type and name.
     * 
     * This method analyzes the field type and delegates to appropriate generators.
     * It supports:
     * - Primitive types and their wrappers (int, Integer, String, etc.)
     * - Date and time types (Date, LocalDate, LocalDateTime)
     * - Collections (List, Set, Map)
     * - Optional types
     * - Enums
     * - Nested POJOs (recursive generation)
     * 
     * @param field the field to generate a value for
     * @param config the configuration for data generation
     * @return a generated value appropriate for the field type, or null if unsupported
     */
    private Object generateValueForField(Field field, DummyConfig config) {
        Class<?> fieldType = field.getType();
        String fieldName = field.getName().toLowerCase();

        // Handle primitives and their wrappers
        if (fieldType == String.class) {
            return generateStringValue(fieldName, config);
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return numberGenerator.generateInt(fieldName);
        } else if (fieldType == long.class || fieldType == Long.class) {
            return numberGenerator.generateLong(fieldName);
        } else if (fieldType == double.class || fieldType == Double.class) {
            return numberGenerator.generateDouble(fieldName);
        } else if (fieldType == float.class || fieldType == Float.class) {
            return numberGenerator.generateFloat(fieldName);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return random.nextBoolean();
        } else if (fieldType == Date.class) {
            return dateGenerator.generateDate(fieldName);
        } else if (fieldType == LocalDate.class) {
            return dateGenerator.generateLocalDate(fieldName);
        } else if (fieldType == LocalDateTime.class) {
            return dateGenerator.generateLocalDateTime(fieldName);
        } else if (fieldType.isEnum()) {
            return generateEnumValue(fieldType);
        } else if (List.class.isAssignableFrom(fieldType)) {
            return generateListValue(field, config);
        } else if (Set.class.isAssignableFrom(fieldType)) {
            return generateSetValue(field, config);
        } else if (Map.class.isAssignableFrom(fieldType)) {
            return generateMapValue(field, config);
        } else if (Optional.class.isAssignableFrom(fieldType)) {
            return generateOptionalValue(field, config);
        } else if (!isPrimitiveOrWrapper(fieldType) && !fieldType.getName().startsWith("java.")) {
            // Handle nested POJOs
            return populate(fieldType, config);
        }

        return null;
    }

    private String generateStringValue(String fieldName, DummyConfig config) {
        if (fieldName.contains("email") || fieldName.contains("mail")) {
            return stringGenerator.generateEmail(config);
        } else if (fieldName.contains("name") && !fieldName.contains("username") && !fieldName.contains("filename")) {
            return stringGenerator.generateName(config);
        } else if (fieldName.contains("username") || fieldName.contains("user")) {
            return stringGenerator.generateUsername(config);
        } else if (fieldName.contains("city") || fieldName.contains("location")) {
            return stringGenerator.generateCity(config);
        } else if (fieldName.contains("address")) {
            return stringGenerator.generateAddress(config);
        } else if (fieldName.contains("phone") || fieldName.contains("mobile")) {
            return stringGenerator.generatePhoneNumber(config);
        } else if (fieldName.contains("url") || fieldName.contains("website")) {
            return stringGenerator.generateUrl(config);
        } else if (fieldName.contains("description") || fieldName.contains("comment") || fieldName.contains("note")) {
            return stringGenerator.generateSentence(config);
        } else {
            return stringGenerator.generateRandomString(config);
        }
    }

    @SuppressWarnings("unchecked")
    private Object generateEnumValue(Class<?> enumType) {
        Object[] enumConstants = enumType.getEnumConstants();
        if (enumConstants.length > 0) {
            return enumConstants[random.nextInt(enumConstants.length)];
        }
        return null;
    }

    private List<?> generateListValue(Field field, DummyConfig config) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) genericType;
            Type[] actualTypes = paramType.getActualTypeArguments();
            if (actualTypes.length > 0) {
                Class<?> elementType = ReflectionUtils.getClassFromType(actualTypes[0]);
                
                List<Object> list = new ArrayList<>();
                int size = random.nextInt(config.getMaxListSize()) + 1;
                
                for (int i = 0; i < size; i++) {
                    Object element = generateValueForType(elementType, config);
                    if (element != null) {
                        list.add(element);
                    }
                }
                
                return list;
            }
        }
        
        return new ArrayList<>();
    }

    private Set<?> generateSetValue(Field field, DummyConfig config) {
        List<?> list = generateListValue(field, config);
        return new HashSet<>(list);
    }

    private Map<?, ?> generateMapValue(Field field, DummyConfig config) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) genericType;
            Type[] actualTypes = paramType.getActualTypeArguments();
            if (actualTypes.length == 2) {
                Class<?> keyType = ReflectionUtils.getClassFromType(actualTypes[0]);
                Class<?> valueType = ReflectionUtils.getClassFromType(actualTypes[1]);
                
                Map<Object, Object> map = new HashMap<>();
                int size = random.nextInt(config.getMaxListSize()) + 1;
                
                for (int i = 0; i < size; i++) {
                    Object key = generateValueForType(keyType, config);
                    Object value = generateValueForType(valueType, config);
                    if (key != null && value != null) {
                        map.put(key, value);
                    }
                }
                
                return map;
            }
        }
        
        return new HashMap<>();
    }

    private Optional<?> generateOptionalValue(Field field, DummyConfig config) {
        // Sometimes return empty Optional
        if (random.nextBoolean()) {
            return Optional.empty();
        }
        
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) genericType;
            Type[] actualTypes = paramType.getActualTypeArguments();
            if (actualTypes.length > 0) {
                Class<?> elementType = ReflectionUtils.getClassFromType(actualTypes[0]);
                Object value = generateValueForType(elementType, config);
                return Optional.ofNullable(value);
            }
        }
        
        return Optional.empty();
    }

    private Object generateValueForType(Class<?> type, DummyConfig config) {
        if (type == String.class) {
            return stringGenerator.generateRandomString(config);
        } else if (type == int.class || type == Integer.class) {
            return numberGenerator.generateInt("value");
        } else if (type == long.class || type == Long.class) {
            return numberGenerator.generateLong("value");
        } else if (type == double.class || type == Double.class) {
            return numberGenerator.generateDouble("value");
        } else if (type == boolean.class || type == Boolean.class) {
            return random.nextBoolean();
        } else if (type == Date.class) {
            return dateGenerator.generateDate("value");
        } else if (type == LocalDate.class) {
            return dateGenerator.generateLocalDate("value");
        } else if (type == LocalDateTime.class) {
            return dateGenerator.generateLocalDateTime("value");
        } else if (type.isEnum()) {
            return generateEnumValue(type);
        } else if (!isPrimitiveOrWrapper(type) && !type.getName().startsWith("java.")) {
            return populate(type, config);
        }
        
        return null;
    }

    private boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive() ||
               type == Boolean.class || type == Character.class || type == Byte.class ||
               type == Short.class || type == Integer.class || type == Long.class ||
               type == Float.class || type == Double.class || type == String.class;
    }
}
