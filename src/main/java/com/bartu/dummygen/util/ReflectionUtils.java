package com.bartu.dummygen.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reflection operations.
 * 
 * Provides helper methods for common reflection tasks including:
 * - Creating instances using default constructors
 * - Accessing and setting field values (including private fields)
 * - Retrieving all fields from a class hierarchy
 * - Working with generic types and parameterized types
 * 
 * This class handles the complexities of Java reflection API and provides
 * simplified methods with proper error handling for the DummyGen library.
 * 
 * @author Bartu Can Palamut
 * @since 1.0.0
 */
public class ReflectionUtils {

    /**
     * Create a new instance of the given class using default constructor.
     * 
     * Attempts to create an instance using the deprecated newInstance() method first,
     * then falls back to using getDeclaredConstructor().newInstance() for better
     * Java 9+ compatibility. Provides clear error messages for common issues.
     * 
     * @param <T> the type of the class
     * @param clazz the class to instantiate
     * @return a new instance of the class
     * @throws Exception if instantiation fails
     * @throws RuntimeException if the class has no default constructor
     */
    public static <T> T createInstance(Class<T> clazz) throws Exception {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // Try to find and use a no-args constructor
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                throw new RuntimeException("Cannot create instance of class: " + clazz.getName() + 
                                         ". Make sure it has a default constructor.", ex);
            }
        }
    }

    /**
     * Get all fields from a class including inherited fields.
     * 
     * Traverses the entire class hierarchy up to (but not including) Object.class
     * to collect all declared fields. This ensures that fields from parent classes
     * are also populated with dummy data.
     * 
     * @param clazz the class to get fields from
     * @return array of all fields (declared and inherited)
     */
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            for (Field field : declaredFields) {
                fields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        
        return fields.toArray(new Field[0]);
    }

    /**
     * Set value to a field, making it accessible if necessary.
     * 
     * Temporarily makes the field accessible if it's private/protected,
     * sets the value, and then restores the original accessibility state.
     * This allows setting values to private fields without permanently
     * changing their accessibility.
     * 
     * @param field the field to set
     * @param target the target object containing the field
     * @param value the value to set in the field
     * @throws Exception if setting the field value fails
     */
    public static void setFieldValue(Field field, Object target, Object value) throws Exception {
        boolean wasAccessible = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(target, value);
        } finally {
            field.setAccessible(wasAccessible);
        }
    }

    /**
     * Get the Class object from a Type (handles generics).
     * 
     * @param type the type to get class from
     * @return the Class object
     */
    @SuppressWarnings("unchecked")
    public static Class<?> getClassFromType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            return (Class<?>) paramType.getRawType();
        } else {
            return Object.class;
        }
    }

}
