package com.bartu.dummygen;

import com.bartu.dummygen.config.DummyConfig;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Unit tests for FieldPopulator class.
 * Tests object population with different field types and configurations.
 */
public class FieldPopulatorTest {

    private FieldPopulator fieldPopulator;
    private DummyConfig defaultConfig;
    private DummyConfig turkishConfig;
    private DummyConfig gibberishConfig;

    @Before
    public void setUp() {
        fieldPopulator = new FieldPopulator();
        defaultConfig = DummyConfig.defaultConfig();
        
        turkishConfig = DummyConfig.builder()
                .language("tr")
                .meaningfulContent(true)
                .maxListSize(3)
                .allowNullFields(false)
                .build();
                
        gibberishConfig = DummyConfig.builder()
                .language("en")
                .meaningfulContent(false)
                .maxListSize(2)
                .allowNullFields(true)
                .build();
    }

    @Test
    public void testPopulateSimpleClass() {
        TestUser user = fieldPopulator.populate(TestUser.class, defaultConfig);
        
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertTrue(user.getAge() > 0);
        assertNotNull(user.getRegistrationDate());
    }

    @Test
    public void testPopulateWithNullConfig() {
        TestUser user = fieldPopulator.populate(TestUser.class, null);
        
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
    }

    @Test
    public void testPopulateWithTurkishConfig() {
        TestUser user = fieldPopulator.populate(TestUser.class, turkishConfig);
        
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertTrue(user.getAge() > 0);
    }

    @Test
    public void testPopulateWithGibberishConfig() {
        TestUser user = fieldPopulator.populate(TestUser.class, gibberishConfig);
        
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
    }

    @Test
    public void testPopulateComplexObject() {
        TestCompany company = fieldPopulator.populate(TestCompany.class, defaultConfig);
        
        assertNotNull(company);
        assertNotNull(company.getName());
        assertNotNull(company.getAddress());
        assertNotNull(company.getEmployees());
        assertFalse(company.getEmployees().isEmpty());
        assertTrue(company.getEmployees().size() <= defaultConfig.getMaxListSize());
        
        // Test nested object population
        for (TestUser employee : company.getEmployees()) {
            assertNotNull(employee);
            assertNotNull(employee.getName());
            assertNotNull(employee.getEmail());
        }
    }

    @Test
    public void testPopulateWithCollections() {
        TestCollections collections = fieldPopulator.populate(TestCollections.class, defaultConfig);
        
        assertNotNull(collections);
        assertNotNull(collections.getStringList());
        assertNotNull(collections.getIntegerSet());
        assertNotNull(collections.getStringMap());
        
        assertFalse(collections.getStringList().isEmpty());
        assertFalse(collections.getIntegerSet().isEmpty());
        assertFalse(collections.getStringMap().isEmpty());
        
        assertTrue(collections.getStringList().size() <= defaultConfig.getMaxListSize());
        assertTrue(collections.getIntegerSet().size() <= defaultConfig.getMaxListSize());
        assertTrue(collections.getStringMap().size() <= defaultConfig.getMaxListSize());
    }

    @Test
    public void testPopulateWithOptionals() {
        TestOptionals optionals = fieldPopulator.populate(TestOptionals.class, defaultConfig);
        
        assertNotNull(optionals);
        assertNotNull(optionals.getOptionalString());
        assertNotNull(optionals.getOptionalInteger());
        assertNotNull(optionals.getOptionalUser());
    }

    @Test
    public void testPopulateWithEnums() {
        TestEnums enums = fieldPopulator.populate(TestEnums.class, defaultConfig);
        
        assertNotNull(enums);
        assertNotNull(enums.getStatus());
        assertNotNull(enums.getPriority());
        
        // Verify enum values are valid
        assertTrue(Arrays.asList(TestStatus.values()).contains(enums.getStatus()));
        assertTrue(Arrays.asList(TestPriority.values()).contains(enums.getPriority()));
    }

    @Test
    public void testPopulateWithPrimitives() {
        TestPrimitives primitives = fieldPopulator.populate(TestPrimitives.class, defaultConfig);
        
        assertNotNull(primitives);
        assertTrue(primitives.getIntValue() >= 0);
        assertTrue(primitives.getLongValue() >= 0);
        assertTrue(primitives.getDoubleValue() >= 0);
        assertTrue(primitives.getFloatValue() >= 0);
        assertTrue(primitives.getCharValue() != '\0');
        // Boolean can be true or false, so no specific assertion needed
    }

    @Test
    public void testPopulateWithWrapperTypes() {
        TestWrappers wrappers = fieldPopulator.populate(TestWrappers.class, defaultConfig);
        
        assertNotNull(wrappers);
        assertNotNull(wrappers.getIntegerValue());
        assertNotNull(wrappers.getLongValue());
        assertNotNull(wrappers.getDoubleValue());
        assertNotNull(wrappers.getFloatValue());
        assertNotNull(wrappers.getShortValue());
        assertNotNull(wrappers.getCharacterValue());
        assertNotNull(wrappers.getBooleanValue());
    }

    @Test
    public void testPopulateWithDates() {
        TestDates dates = fieldPopulator.populate(TestDates.class, defaultConfig);
        
        assertNotNull(dates);
        assertNotNull(dates.getUtilDate());
        assertNotNull(dates.getLocalDate());
        assertNotNull(dates.getLocalDateTime());
        assertNotNull(dates.getBirthDate());
        assertNotNull(dates.getCreatedAt());
    }

    @Test
    public void testPopulateWithSpecialStrings() {
        TestSpecialStrings strings = fieldPopulator.populate(TestSpecialStrings.class, defaultConfig);
        
        assertNotNull(strings);
        assertNotNull(strings.getName());
        assertNotNull(strings.getEmail());
        assertNotNull(strings.getUsername());
        assertNotNull(strings.getCity());
        assertNotNull(strings.getAddress());
        assertNotNull(strings.getPhoneNumber());
        assertNotNull(strings.getUrl());
        assertNotNull(strings.getDescription());
        
        // Test email format
        assertTrue(strings.getEmail().contains("@"));
        assertTrue(strings.getEmail().contains("."));
        
        // Test phone number format
        assertTrue(strings.getPhoneNumber().startsWith("+"));
        
        // Test URL format
        assertTrue(strings.getUrl().startsWith("http"));
    }

    @Test
    public void testPopulateWithNullableFields() {
        DummyConfig nullableConfig = DummyConfig.builder()
                .allowNullFields(true)
                .meaningfulContent(true)
                .build();
                
        TestUser user = fieldPopulator.populate(TestUser.class, nullableConfig);
        
        assertNotNull(user);
        // Some fields might be null with allowNullFields=true, but the object should not be null
    }

    @Test
    public void testPopulateRecursiveObject() {
        TestRecursive recursive = fieldPopulator.populate(TestRecursive.class, defaultConfig);
        
        assertNotNull(recursive);
        assertNotNull(recursive.getName());
        assertNotNull(recursive.getChildren());
        
        // Check that nested recursive objects are populated
        for (TestRecursive child : recursive.getChildren()) {
            assertNotNull(child);
            assertNotNull(child.getName());
        }
    }

    @Test
    public void testPopulateWithMaxListSize() {
        DummyConfig smallListConfig = DummyConfig.builder()
                .maxListSize(1)
                .build();
                
        TestCollections collections = fieldPopulator.populate(TestCollections.class, smallListConfig);
        
        assertNotNull(collections);
        assertTrue(collections.getStringList().size() <= 1);
        assertTrue(collections.getIntegerSet().size() <= 1);
        assertTrue(collections.getStringMap().size() <= 1);
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

    public static class TestCollections {
        private List<String> stringList;
        private Set<Integer> integerSet;
        private Map<String, Integer> stringMap;

        // Getters and setters
        public List<String> getStringList() { return stringList; }
        public void setStringList(List<String> stringList) { this.stringList = stringList; }
        public Set<Integer> getIntegerSet() { return integerSet; }
        public void setIntegerSet(Set<Integer> integerSet) { this.integerSet = integerSet; }
        public Map<String, Integer> getStringMap() { return stringMap; }
        public void setStringMap(Map<String, Integer> stringMap) { this.stringMap = stringMap; }
    }

    public static class TestOptionals {
        private Optional<String> optionalString;
        private Optional<Integer> optionalInteger;
        private Optional<TestUser> optionalUser;

        // Getters and setters
        public Optional<String> getOptionalString() { return optionalString; }
        public void setOptionalString(Optional<String> optionalString) { this.optionalString = optionalString; }
        public Optional<Integer> getOptionalInteger() { return optionalInteger; }
        public void setOptionalInteger(Optional<Integer> optionalInteger) { this.optionalInteger = optionalInteger; }
        public Optional<TestUser> getOptionalUser() { return optionalUser; }
        public void setOptionalUser(Optional<TestUser> optionalUser) { this.optionalUser = optionalUser; }
    }

    public enum TestStatus {
        ACTIVE, INACTIVE, PENDING, ARCHIVED
    }

    public enum TestPriority {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public static class TestEnums {
        private TestStatus status;
        private TestPriority priority;

        // Getters and setters
        public TestStatus getStatus() { return status; }
        public void setStatus(TestStatus status) { this.status = status; }
        public TestPriority getPriority() { return priority; }
        public void setPriority(TestPriority priority) { this.priority = priority; }
    }

    public static class TestPrimitives {
        private int intValue;
        private long longValue;
        private double doubleValue;
        private float floatValue;
        private byte byteValue;
        private short shortValue;
        private char charValue;
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
        public byte getByteValue() { return byteValue; }
        public void setByteValue(byte byteValue) { this.byteValue = byteValue; }
        public short getShortValue() { return shortValue; }
        public void setShortValue(short shortValue) { this.shortValue = shortValue; }
        public char getCharValue() { return charValue; }
        public void setCharValue(char charValue) { this.charValue = charValue; }
        public boolean isBooleanValue() { return booleanValue; }
        public void setBooleanValue(boolean booleanValue) { this.booleanValue = booleanValue; }
    }

    public static class TestWrappers {
        private Integer integerValue;
        private Long longValue;
        private Double doubleValue;
        private Float floatValue;
        private Byte byteValue;
        private Short shortValue;
        private Character characterValue;
        private Boolean booleanValue;

        // Getters and setters
        public Integer getIntegerValue() { return integerValue; }
        public void setIntegerValue(Integer integerValue) { this.integerValue = integerValue; }
        public Long getLongValue() { return longValue; }
        public void setLongValue(Long longValue) { this.longValue = longValue; }
        public Double getDoubleValue() { return doubleValue; }
        public void setDoubleValue(Double doubleValue) { this.doubleValue = doubleValue; }
        public Float getFloatValue() { return floatValue; }
        public void setFloatValue(Float floatValue) { this.floatValue = floatValue; }
        public Byte getByteValue() { return byteValue; }
        public void setByteValue(Byte byteValue) { this.byteValue = byteValue; }
        public Short getShortValue() { return shortValue; }
        public void setShortValue(Short shortValue) { this.shortValue = shortValue; }
        public Character getCharacterValue() { return characterValue; }
        public void setCharacterValue(Character characterValue) { this.characterValue = characterValue; }
        public Boolean getBooleanValue() { return booleanValue; }
        public void setBooleanValue(Boolean booleanValue) { this.booleanValue = booleanValue; }
    }

    public static class TestDates {
        private Date utilDate;
        private LocalDate localDate;
        private LocalDateTime localDateTime;
        private Date birthDate;
        private Date createdAt;

        // Getters and setters
        public Date getUtilDate() { return utilDate; }
        public void setUtilDate(Date utilDate) { this.utilDate = utilDate; }
        public LocalDate getLocalDate() { return localDate; }
        public void setLocalDate(LocalDate localDate) { this.localDate = localDate; }
        public LocalDateTime getLocalDateTime() { return localDateTime; }
        public void setLocalDateTime(LocalDateTime localDateTime) { this.localDateTime = localDateTime; }
        public Date getBirthDate() { return birthDate; }
        public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
        public Date getCreatedAt() { return createdAt; }
        public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    }

    public static class TestSpecialStrings {
        private String name;
        private String email;
        private String username;
        private String city;
        private String address;
        private String phoneNumber;
        private String url;
        private String description;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class TestRecursive {
        private String name;
        private List<TestRecursive> children;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public List<TestRecursive> getChildren() { return children; }
        public void setChildren(List<TestRecursive> children) { this.children = children; }
    }
}
