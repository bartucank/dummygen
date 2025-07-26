package com.bartu.dummygen.util;

import org.junit.Test;
import static org.junit.Assert.*;


import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for ReflectionUtils class.
 * Tests reflection operations and utility methods.
 */
public class ReflectionUtilsTest {

    @Test
    public void testCreateInstanceWithDefaultConstructor() throws Exception {
        TestClass instance = ReflectionUtils.createInstance(TestClass.class);
        
        assertNotNull(instance);
        assertEquals("default", instance.getName());
        assertEquals(0, instance.getValue());
    }

    @Test
    public void testCreateInstanceWithPrivateConstructor() {
        assertThrows(RuntimeException.class, () -> {
            ReflectionUtils.createInstance(TestClassWithPrivateConstructor.class);
        });
    }

    @Test
    public void testCreateInstanceWithNoDefaultConstructor() {
        assertThrows(RuntimeException.class, () -> {
            ReflectionUtils.createInstance(TestClassWithoutDefaultConstructor.class);
        });
    }

    @Test
    public void testGetAllFieldsFromSimpleClass() {
        Field[] fields = ReflectionUtils.getAllFields(TestClass.class);
        
        assertNotNull(fields);
        assertEquals(2, fields.length);
        
        // Check field names (order might vary)
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        
        assertTrue(java.util.Arrays.asList(fieldNames).contains("name"));
        assertTrue(java.util.Arrays.asList(fieldNames).contains("value"));
    }

    @Test
    public void testGetAllFieldsFromInheritedClass() {
        Field[] fields = ReflectionUtils.getAllFields(TestChildClass.class);
        
        assertNotNull(fields);
        assertEquals(3, fields.length); // childField + inherited fields (name, value)
        
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        
        assertTrue(java.util.Arrays.asList(fieldNames).contains("name"));
        assertTrue(java.util.Arrays.asList(fieldNames).contains("value"));
        assertTrue(java.util.Arrays.asList(fieldNames).contains("childField"));
    }

    @Test
    public void testGetAllFieldsExcludesObjectFields() {
        Field[] fields = ReflectionUtils.getAllFields(TestClass.class);
        
        // Should not include Object class fields like getClass, hashCode, etc.
        for (Field field : fields) {
            assertNotEquals("class", field.getName());
        }
    }

    @Test
    public void testSetFieldValueOnPublicField() throws Exception {
        TestClass instance = new TestClass();
        Field nameField = TestClass.class.getDeclaredField("name");
        
        ReflectionUtils.setFieldValue(nameField, instance, "testName");
        
        assertEquals("testName", instance.getName());
    }

    @Test
    public void testSetFieldValueOnPrivateField() throws Exception {
        TestClassWithPrivateField instance = new TestClassWithPrivateField();
        Field privateField = TestClassWithPrivateField.class.getDeclaredField("privateValue");
        
        ReflectionUtils.setFieldValue(privateField, instance, 42);
        
        assertEquals(42, instance.getPrivateValue());
    }

    @Test
    public void testSetFieldValuePreservesAccessibility() throws Exception {
        TestClassWithPrivateField instance = new TestClassWithPrivateField();
        Field privateField = TestClassWithPrivateField.class.getDeclaredField("privateValue");
        
        boolean originalAccessibility = privateField.isAccessible();
        
        ReflectionUtils.setFieldValue(privateField, instance, 42);
        
        // Accessibility should be restored to original state
        assertEquals(originalAccessibility, privateField.isAccessible());
    }

    @Test
    public void testGetClassFromTypeWithClass() {
        Class<?> result = ReflectionUtils.getClassFromType(String.class);
        
        assertEquals(String.class, result);
    }

    @Test
    public void testGetClassFromTypeWithParameterizedType() throws Exception {
        // Get the generic type from a List<String> field
        Field listField = TestGenericClass.class.getDeclaredField("stringList");
        Type genericType = listField.getGenericType();
        
        Class<?> result = ReflectionUtils.getClassFromType(genericType);
        
        assertEquals(List.class, result);
    }

    @Test
    public void testGetClassFromTypeWithParameterizedTypeArgument() throws Exception {
        // Get the parameter type from List<String>
        Field listField = TestGenericClass.class.getDeclaredField("stringList");
        ParameterizedType paramType = (ParameterizedType) listField.getGenericType();
        Type[] actualTypes = paramType.getActualTypeArguments();
        
        Class<?> result = ReflectionUtils.getClassFromType(actualTypes[0]);
        
        assertEquals(String.class, result);
    }

    @Test
    public void testGetClassFromTypeWithUnknownType() {
        Type unknownType = new Type() {
            // Custom type implementation
        };
        
        Class<?> result = ReflectionUtils.getClassFromType(unknownType);
        
        assertEquals(Object.class, result);
    }

    @Test
    public void testCreateInstanceWithInterface() {
        assertThrows(RuntimeException.class, () -> {
            ReflectionUtils.createInstance(TestInterface.class);
        });
    }

    @Test
    public void testCreateInstanceWithAbstractClass() {
        assertThrows(RuntimeException.class, () -> {
            ReflectionUtils.createInstance(TestAbstractClass.class);
        });
    }

    @Test
    public void testSetFieldValueWithNullValue() throws Exception {
        TestClass instance = new TestClass();
        Field nameField = TestClass.class.getDeclaredField("name");
        
        ReflectionUtils.setFieldValue(nameField, instance, null);
        
        assertNull(instance.getName());
    }

    @Test
    public void testSetFieldValueWithWrongType() throws Exception {
        TestClass instance = new TestClass();
        Field valueField = TestClass.class.getDeclaredField("value");
        
        // This should throw an exception when trying to set String to int field
        assertThrows(Exception.class, () -> {
            ReflectionUtils.setFieldValue(valueField, instance, "notAnInteger");
        });
    }

    // Test classes for validation
    public static class TestClass {
        private String name = "default";
        private int value = 0;

        public TestClass() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getValue() { return value; }
        public void setValue(int value) { this.value = value; }
    }

    public static class TestClassWithPrivateConstructor {
        private TestClassWithPrivateConstructor() {}
    }

    public static class TestClassWithoutDefaultConstructor {
        private String name;
        
        public TestClassWithoutDefaultConstructor(String name) {
            this.name = name;
        }
    }

    public static class TestChildClass extends TestClass {
        private String childField = "child";

        public String getChildField() { return childField; }
        public void setChildField(String childField) { this.childField = childField; }
    }

    public static class TestClassWithPrivateField {
        private int privateValue = 0;

        public int getPrivateValue() { return privateValue; }
    }

    public static class TestGenericClass {
        private List<String> stringList;
        private Map<String, Integer> stringIntegerMap;

        public List<String> getStringList() { return stringList; }
        public void setStringList(List<String> stringList) { this.stringList = stringList; }
        public Map<String, Integer> getStringIntegerMap() { return stringIntegerMap; }
        public void setStringIntegerMap(Map<String, Integer> stringIntegerMap) { this.stringIntegerMap = stringIntegerMap; }
    }

    public interface TestInterface {
        void doSomething();
    }

    public abstract static class TestAbstractClass {
        public abstract void doSomething();
    }
}
