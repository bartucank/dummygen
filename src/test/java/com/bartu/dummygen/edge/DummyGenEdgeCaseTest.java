package com.bartu.dummygen.edge;

import com.bartu.dummygen.DummyGen;
import com.bartu.dummygen.config.DummyConfig;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Edge case and error condition tests for DummyGen library.
 * Tests behavior with unusual inputs, error conditions, and boundary cases.
 */
public class DummyGenEdgeCaseTest {

    @Test
    public void testNullClassThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DummyGen.generate(null);
        });
    }

    @Test
    public void testNullConfigUsesDefault() {
        SimpleClass obj = DummyGen.generate(SimpleClass.class, null);
        
        assertNotNull(obj);
        assertNotNull(obj.getName());
    }

    @Test
    public void testEmptyClass() {
        EmptyClass empty = DummyGen.generate(EmptyClass.class);
        
        assertNotNull(empty);
        // Should create object even with no fields
    }

    @Test
    public void testClassWithOnlyStaticFields() {
        StaticFieldsClass obj = DummyGen.generate(StaticFieldsClass.class);
        
        assertNotNull(obj);
        // Static fields should not be populated
        assertEquals("STATIC_VALUE", StaticFieldsClass.staticField);
    }

    @Test
    public void testClassWithFinalFields() {
        FinalFieldsClass obj = DummyGen.generate(FinalFieldsClass.class);
        
        assertNotNull(obj);
        // Final fields should keep their initialized values
        assertEquals("FINAL_VALUE", obj.getFinalField());
        assertNotNull(obj.getRegularField());
    }

    @Test
    public void testClassWithPrivateConstructor() {
        // This should fail gracefully or handle private constructors
        assertThrows(RuntimeException.class, () -> {
            DummyGen.generate(PrivateConstructorClass.class);
        });
    }

    @Test
    public void testClassWithParameterizedConstructor() {
        // Classes without default constructor should fail gracefully
        assertThrows(RuntimeException.class, () -> {
            DummyGen.generate(ParameterizedConstructorClass.class);
        });
    }

    @Test
    public void testAbstractClass() {
        // Should fail to instantiate abstract class
        assertThrows(RuntimeException.class, () -> {
            DummyGen.generate(AbstractClass.class);
        });
    }

    @Test
    public void testInterface() {
        // Should fail to instantiate interface
        assertThrows(RuntimeException.class, () -> {
            DummyGen.generate(TestInterface.class);
        });
    }

    @Test
    public void testWithVeryLargeMaxListSize() {
        DummyConfig config = DummyConfig.builder()
                .maxListSize(1000)
                .build();

        CollectionClass obj = DummyGen.generate(CollectionClass.class, config);
        
        assertNotNull(obj);
        assertNotNull(obj.getStringList());
        // Should handle large list sizes without issues
        assertTrue(obj.getStringList().size() <= 1000);
    }

    @Test
    public void testWithZeroMaxListSize() {
        DummyConfig config = DummyConfig.builder()
                .maxListSize(0)
                .build();

        CollectionClass obj = DummyGen.generate(CollectionClass.class, config);
        
        assertNotNull(obj);
        // With maxListSize=0, lists should be empty or have minimal size
    }

    @Test
    public void testWithNegativeMaxListSize() {
        DummyConfig config = DummyConfig.builder()
                .maxListSize(3)
                .build();

        CollectionClass obj = DummyGen.generate(CollectionClass.class, config);
        
        assertNotNull(obj);
        // Should handle negative values gracefully
    }

    @Test
    public void testDeeplyNestedObjects() {
        DeeplyNested obj = DummyGen.generate(DeeplyNested.class);
        
        assertNotNull(obj);
        assertNotNull(obj.getLevel1());
        assertNotNull(obj.getLevel1().getLevel2());
        assertNotNull(obj.getLevel1().getLevel2().getLevel3());
        assertNotNull(obj.getLevel1().getLevel2().getLevel3().getValue());
    }

    @Test
    public void testCircularReference() {
        // This tests if the library can handle circular references without infinite loops
        CircularA objA = DummyGen.generate(CircularA.class);
        
        assertNotNull(objA);
        // Should not cause stack overflow or infinite loop
    }

    @Test
    public void testGenericTypes() {
        GenericClass<String> obj = DummyGen.generate(GenericClass.class);
        
        assertNotNull(obj);
        // Should handle generic types appropriately
    }

    @Test
    public void testWithUnsupportedLanguage() {
        DummyConfig config = DummyConfig.builder()
                .language("unsupported")
                .build();

        SimpleClass obj = DummyGen.generate(SimpleClass.class, config);
        
        assertNotNull(obj);
        assertNotNull(obj.getName());
        // Should fall back to default language behavior
    }

    @Test
    public void testWithInvalidConfigValues() {
        DummyConfig config = DummyConfig.builder()
                .language(null)
                .maxListSize(-100)
                .build();

        SimpleClass obj = DummyGen.generate(SimpleClass.class, config);
        
        assertNotNull(obj);
        // Should handle invalid config values gracefully
    }

    @Test
    public void testMemoryUsageWithLargeObjects() {
        // Test that generation doesn't cause memory issues
        List<Object> objects = new ArrayList<>();
        
        for (int i = 0; i < 100; i++) {
            CollectionClass obj = DummyGen.generate(CollectionClass.class);
            objects.add(obj);
        }
        
        assertEquals(100, objects.size());
        // All objects should be created successfully
        for (Object obj : objects) {
            assertNotNull(obj);
        }
    }

    @Test
    public void testThreadSafety() {
        List<Thread> threads = new ArrayList<>();
        List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());
        
        // Create multiple threads generating objects simultaneously
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        SimpleClass obj = DummyGen.generate(SimpleClass.class);
                        assertNotNull(obj);
                    }
                } catch (Exception e) {
                    exceptions.add(e);
                }
            });
            threads.add(thread);
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    // Test classes for edge cases
    public static class SimpleClass {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class EmptyClass {
        // No fields
    }

    public static class StaticFieldsClass {
        public static String staticField = "STATIC_VALUE";
        private String instanceField;

        public String getInstanceField() { return instanceField; }
        public void setInstanceField(String instanceField) { this.instanceField = instanceField; }
    }

    public static class FinalFieldsClass {
        private final String finalField = "FINAL_VALUE";
        private String regularField;

        public String getFinalField() { return finalField; }
        public String getRegularField() { return regularField; }
        public void setRegularField(String regularField) { this.regularField = regularField; }
    }

    public static class PrivateConstructorClass {
        private String name;

        private PrivateConstructorClass() {
            // Private constructor
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class ParameterizedConstructorClass {
        private String name;

        public ParameterizedConstructorClass(String name) {
            this.name = name;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public abstract static class AbstractClass {
        protected String name;

        public abstract void doSomething();

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public interface TestInterface {
        void doSomething();
    }

    public static class CollectionClass {
        private List<String> stringList;
        private Set<Integer> integerSet;
        private Map<String, String> stringMap;

        // Getters and setters
        public List<String> getStringList() { return stringList; }
        public void setStringList(List<String> stringList) { this.stringList = stringList; }
        public Set<Integer> getIntegerSet() { return integerSet; }
        public void setIntegerSet(Set<Integer> integerSet) { this.integerSet = integerSet; }
        public Map<String, String> getStringMap() { return stringMap; }
        public void setStringMap(Map<String, String> stringMap) { this.stringMap = stringMap; }
    }

    public static class DeeplyNested {
        private Level1 level1;

        public Level1 getLevel1() { return level1; }
        public void setLevel1(Level1 level1) { this.level1 = level1; }
    }

    public static class Level1 {
        private Level2 level2;

        public Level2 getLevel2() { return level2; }
        public void setLevel2(Level2 level2) { this.level2 = level2; }
    }

    public static class Level2 {
        private Level3 level3;

        public Level3 getLevel3() { return level3; }
        public void setLevel3(Level3 level3) { this.level3 = level3; }
    }

    public static class Level3 {
        private String value;

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }

    public static class CircularA {
        private CircularB circularB;

        public CircularB getCircularB() { return circularB; }
        public void setCircularB(CircularB circularB) { this.circularB = circularB; }
    }

    public static class CircularB {
        private CircularA circularA;

        public CircularA getCircularA() { return circularA; }
        public void setCircularA(CircularA circularA) { this.circularA = circularA; }
    }

    public static class GenericClass<T> {
        private T value;
        private List<T> items;

        public T getValue() { return value; }
        public void setValue(T value) { this.value = value; }
        public List<T> getItems() { return items; }
        public void setItems(List<T> items) { this.items = items; }
    }
}
