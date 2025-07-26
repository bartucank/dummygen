package com.bartu.dummygen;

import com.bartu.dummygen.config.DummyConfig;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Unit tests for DummyGen class.
 * Tests the main generation methods with different configurations.
 */
public class DummyGenTest {

    @Test
    public void testGenerateWithDefaultConfig() {
        // Test with simple class
        TestUser user = DummyGen.generate(TestUser.class);
        
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertTrue(user.getAge() > 0);
        assertNotNull(user.getRegistrationDate());
    }

    @Test
    public void testGenerateWithCustomConfig() {
        DummyConfig config = DummyConfig.builder()
                .language("tr")
                .meaningfulContent(true)
                .maxListSize(5)
                .allowNullFields(false)
                .build();

        TestUser user = DummyGen.generate(TestUser.class, config);
        
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertTrue(user.getAge() > 0);
        assertNotNull(user.getRegistrationDate());
    }

    @Test
    public void testGenerateWithNullClass() {
        assertThrows(IllegalArgumentException.class, () -> {
            DummyGen.generate(null);
        });
    }

    @Test
    public void testGenerateWithNullConfig() {
        // Should use default config when null is passed
        TestUser user = DummyGen.generate(TestUser.class, null);
        
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
    }

    @Test
    public void testGenerateComplexObject() {
        TestCompany company = DummyGen.generate(TestCompany.class);
        
        assertNotNull(company);
        assertNotNull(company.getName());
        assertNotNull(company.getAddress());
        assertNotNull(company.getEmployees());
        assertFalse(company.getEmployees().isEmpty());
        assertNotNull(company.getDepartments());
        assertFalse(company.getDepartments().isEmpty());
    }

    @Test
    public void testGenerateWithEnums() {
        TestProduct product = DummyGen.generate(TestProduct.class);
        
        assertNotNull(product);
        assertNotNull(product.getName());
        assertNotNull(product.getStatus());
        assertTrue(product.getPrice() > 0);
    }

    @Test
    public void testGenerateWithOptional() {
        TestProduct product = DummyGen.generate(TestProduct.class);
        
        assertNotNull(product);
        assertNotNull(product.getDescription()); // Optional should be present
    }

    @Test
    public void testGenerateWithCollections() {
        TestCompany company = DummyGen.generate(TestCompany.class);
        
        assertNotNull(company.getEmployees());
        assertTrue(company.getEmployees().size() > 0);
        assertTrue(company.getEmployees().size() <= 3); // Default max list size
        
        assertNotNull(company.getDepartments());
        assertTrue(company.getDepartments().size() > 0);
    }

    @Test
    public void testGenerateWithPrimitives() {
        TestPrimitives primitives = DummyGen.generate(TestPrimitives.class);
        
        assertNotNull(primitives);
        assertTrue(primitives.getIntValue() >= 0);
        assertTrue(primitives.getLongValue() >= 0);
        assertTrue(primitives.getDoubleValue() >= 0);
        assertTrue(primitives.getFloatValue() >= 0);
        // Boolean can be true or false, so no specific assertion
    }

    @Test
    public void testGenerateWithDateTypes() {
        TestDates dates = DummyGen.generate(TestDates.class);
        
        assertNotNull(dates);
        assertNotNull(dates.getBirthDate());
        assertNotNull(dates.getCreatedAt());
        assertNotNull(dates.getLocalDate());
        assertNotNull(dates.getLocalDateTime());
    }

    // Test classes for validation
    public static class TestUser {
        private String name;
        private String email;
        private int age;
        private Date registrationDate;
        private boolean active;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public Date getRegistrationDate() { return registrationDate; }
        public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
    }

    public static class TestCompany {
        private String name;
        private String address;
        private List<TestUser> employees;
        private Map<String, String> departments;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public List<TestUser> getEmployees() { return employees; }
        public void setEmployees(List<TestUser> employees) { this.employees = employees; }
        public Map<String, String> getDepartments() { return departments; }
        public void setDepartments(Map<String, String> departments) { this.departments = departments; }
    }

    public enum ProductStatus {
        ACTIVE, INACTIVE, PENDING, ARCHIVED
    }

    public static class TestProduct {
        private String name;
        private ProductStatus status;
        private double price;
        private Optional<String> description;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public ProductStatus getStatus() { return status; }
        public void setStatus(ProductStatus status) { this.status = status; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public Optional<String> getDescription() { return description; }
        public void setDescription(Optional<String> description) { this.description = description; }
    }

    public static class TestPrimitives {
        private int intValue;
        private long longValue;
        private double doubleValue;
        private float floatValue;
        private boolean booleanValue;

        // Getters and setters
        public int getIntValue() { return intValue; }
        public void setIntValue(int intValue) { this.intValue = intValue; }
        public long getLongValue() { return longValue; }
        public void setLongValue(long longValue) { this.longValue = longValue; }
        public double getDoubleValue() { return doubleValue; }
        public void setDoubleValue(double doubleValue) { this.doubleValue = doubleValue; }
        public float getFloatValue() { return floatValue; }
        public void setFloatValue(float floatValue) { this.floatValue = floatValue; }
        public boolean isBooleanValue() { return booleanValue; }
        public void setBooleanValue(boolean booleanValue) { this.booleanValue = booleanValue; }
    }

    public static class TestDates {
        private Date birthDate;
        private Date createdAt;
        private LocalDate localDate;
        private LocalDateTime localDateTime;

        // Getters and setters
        public Date getBirthDate() { return birthDate; }
        public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
        public Date getCreatedAt() { return createdAt; }
        public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
        public LocalDate getLocalDate() { return localDate; }
        public void setLocalDate(LocalDate localDate) { this.localDate = localDate; }
        public LocalDateTime getLocalDateTime() { return localDateTime; }
        public void setLocalDateTime(LocalDateTime localDateTime) { this.localDateTime = localDateTime; }
    }
}
