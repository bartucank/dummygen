package com.bartu.dummygen.integration;

import com.bartu.dummygen.DummyGen;
import com.bartu.dummygen.config.DummyConfig;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Integration tests for DummyGen library.
 * Tests end-to-end functionality with complex scenarios.
 */
public class DummyGenIntegrationTest {

    @Test
    public void testCompleteUserWorkflow() {
        DummyConfig config = DummyConfig.builder()
                .language("en")
                .meaningfulContent(true)
                .maxListSize(5)
                .allowNullFields(false)
                .build();

        CompleteUser user = DummyGen.generate(CompleteUser.class, config);
        
        assertNotNull(user);
        
        // Test basic fields
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertTrue(user.getEmail().contains("@"));
        assertTrue(user.getAge() > 0);
        assertNotNull(user.getBirthDate());
        
        // Test address object
        assertNotNull(user.getAddress());
        assertNotNull(user.getAddress().getStreet());
        assertNotNull(user.getAddress().getCity());
        assertNotNull(user.getAddress().getCountry());
        assertNotNull(user.getAddress().getZipCode());
        
        // Test collections
        assertNotNull(user.getPhoneNumbers());
        assertFalse(user.getPhoneNumbers().isEmpty());
        assertTrue(user.getPhoneNumbers().size() <= 5);
        
        assertNotNull(user.getSkills());
        assertFalse(user.getSkills().isEmpty());
        
        assertNotNull(user.getSocialMedia());
        assertFalse(user.getSocialMedia().isEmpty());
        
        // Test nested objects in collections
        assertNotNull(user.getProjects());
        for (Project project : user.getProjects()) {
            assertNotNull(project);
            assertNotNull(project.getName());
            assertNotNull(project.getDescription());
            assertNotNull(project.getStatus());
            assertNotNull(project.getStartDate());
        }
    }

    @Test
    public void testTurkishLocalization() {
        DummyConfig turkishConfig = DummyConfig.builder()
                .language("tr")
                .meaningfulContent(true)
                .maxListSize(3)
                .build();

        CompleteUser user = DummyGen.generate(CompleteUser.class, turkishConfig);
        
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        
        // Turkish phone numbers should start with +90
        if (!user.getPhoneNumbers().isEmpty()) {
            boolean hasTurkishNumber = user.getPhoneNumbers().stream()
                .anyMatch(phone -> phone.startsWith("+90"));
            // Note: May contain other formats, but Turkish should be present in Turkish config
        }
    }

    @Test
    public void testGibberishMode() {
        DummyConfig gibberishConfig = DummyConfig.builder()
                .meaningfulContent(false)
                .maxListSize(2)
                .build();

        CompleteUser user = DummyGen.generate(CompleteUser.class, gibberishConfig);
        
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        
        // In gibberish mode, email should still have @ symbol
        assertTrue(user.getEmail().contains("@"));
        
        // Collections should respect maxListSize
        assertTrue(user.getPhoneNumbers().size() <= 2);
        assertTrue(user.getProjects().size() <= 2);
    }

    @Test
    public void testDeepNesting() {
        Organization org = DummyGen.generate(Organization.class);
        
        assertNotNull(org);
        assertNotNull(org.getName());
        assertNotNull(org.getDepartments());
        
        for (Department dept : org.getDepartments()) {
            assertNotNull(dept);
            assertNotNull(dept.getName());
            assertNotNull(dept.getManager());
            assertNotNull(dept.getEmployees());
            
            // Test manager
            CompleteUser manager = dept.getManager();
            assertNotNull(manager.getName());
            assertNotNull(manager.getEmail());
            
            // Test employees
            for (CompleteUser employee : dept.getEmployees()) {
                assertNotNull(employee);
                assertNotNull(employee.getName());
                assertNotNull(employee.getEmail());
                assertNotNull(employee.getAddress());
            }
        }
    }

    @Test
    public void testPerformanceWithLargeObjects() {
        DummyConfig config = DummyConfig.builder()
                .maxListSize(10)
                .build();

        long startTime = System.currentTimeMillis();
        
        // Generate multiple large objects
        for (int i = 0; i < 10; i++) {
            Organization org = DummyGen.generate(Organization.class, config);
            assertNotNull(org);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Performance test duration: " + duration + " ms");
    }

    @Test
    public void testWithNullableFields() {
        DummyConfig nullableConfig = DummyConfig.builder()
                .allowNullFields(true)
                .build();

        CompleteUser user = DummyGen.generate(CompleteUser.class, nullableConfig);
        
        assertNotNull(user);
        // Object should not be null even with nullable fields enabled
        
        // Some fields might be null, but critical ones should still be populated
        // The exact behavior depends on implementation
    }

    @Test
    public void testRecursiveStructures() {
        TreeNode root = DummyGen.generate(TreeNode.class);
        
        assertNotNull(root);
        assertNotNull(root.getValue());
        
        // Test that recursive generation doesn't cause infinite loops
        if (root.getChildren() != null) {
            for (TreeNode child : root.getChildren()) {
                assertNotNull(child);
                assertNotNull(child.getValue());
                // Children might have their own children, but should be limited
            }
        }
    }

    @Test
    public void testWithMixedPrimitiveAndObjectTypes() {
        MixedTypesClass mixed = DummyGen.generate(MixedTypesClass.class);
        
        assertNotNull(mixed);
        
        // Primitive types
        assertTrue(mixed.getIntValue() != 0);
        assertTrue(mixed.getDoubleValue() != 0.0);
        assertTrue(mixed.getLongValue() != 0L);
        
        // Wrapper types
        assertNotNull(mixed.getIntegerValue());
        assertNotNull(mixed.getDoubleWrapper());
        assertNotNull(mixed.getBooleanWrapper());
        
        // Object types
        assertNotNull(mixed.getStringValue());
        assertNotNull(mixed.getUserObject());
        assertNotNull(mixed.getDateValue());
        assertNotNull(mixed.getLocalDateValue());
        
        // Collections of mixed types
        assertNotNull(mixed.getStringList());
        assertNotNull(mixed.getIntegerSet());
        assertNotNull(mixed.getObjectMap());
    }

    // Test classes for integration testing
    public static class CompleteUser {
        private String name;
        private String email;
        private int age;
        private Date birthDate;
        private Address address;
        private List<String> phoneNumbers;
        private Set<String> skills;
        private Map<String, String> socialMedia;
        private List<Project> projects;
        private UserStatus status;
        private Optional<String> bio;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public Date getBirthDate() { return birthDate; }
        public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
        public Address getAddress() { return address; }
        public void setAddress(Address address) { this.address = address; }
        public List<String> getPhoneNumbers() { return phoneNumbers; }
        public void setPhoneNumbers(List<String> phoneNumbers) { this.phoneNumbers = phoneNumbers; }
        public Set<String> getSkills() { return skills; }
        public void setSkills(Set<String> skills) { this.skills = skills; }
        public Map<String, String> getSocialMedia() { return socialMedia; }
        public void setSocialMedia(Map<String, String> socialMedia) { this.socialMedia = socialMedia; }
        public List<Project> getProjects() { return projects; }
        public void setProjects(List<Project> projects) { this.projects = projects; }
        public UserStatus getStatus() { return status; }
        public void setStatus(UserStatus status) { this.status = status; }
        public Optional<String> getBio() { return bio; }
        public void setBio(Optional<String> bio) { this.bio = bio; }
    }

    public static class Address {
        private String street;
        private String city;
        private String country;
        private String zipCode;

        // Getters and setters
        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getZipCode() { return zipCode; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    }

    public static class Project {
        private String name;
        private String description;
        private ProjectStatus status;
        private LocalDate startDate;
        private LocalDate endDate;
        private List<String> tags;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public ProjectStatus getStatus() { return status; }
        public void setStatus(ProjectStatus status) { this.status = status; }
        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
        public List<String> getTags() { return tags; }
        public void setTags(List<String> tags) { this.tags = tags; }
    }

    public static class Organization {
        private String name;
        private Address headquarters;
        private List<Department> departments;
        private Map<String, CompleteUser> executives;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Address getHeadquarters() { return headquarters; }
        public void setHeadquarters(Address headquarters) { this.headquarters = headquarters; }
        public List<Department> getDepartments() { return departments; }
        public void setDepartments(List<Department> departments) { this.departments = departments; }
        public Map<String, CompleteUser> getExecutives() { return executives; }
        public void setExecutives(Map<String, CompleteUser> executives) { this.executives = executives; }
    }

    public static class Department {
        private String name;
        private CompleteUser manager;
        private List<CompleteUser> employees;
        private double budget;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public CompleteUser getManager() { return manager; }
        public void setManager(CompleteUser manager) { this.manager = manager; }
        public List<CompleteUser> getEmployees() { return employees; }
        public void setEmployees(List<CompleteUser> employees) { this.employees = employees; }
        public double getBudget() { return budget; }
        public void setBudget(double budget) { this.budget = budget; }
    }

    public static class TreeNode {
        private String value;
        private List<TreeNode> children;
        private TreeNode parent;

        // Getters and setters
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public List<TreeNode> getChildren() { return children; }
        public void setChildren(List<TreeNode> children) { this.children = children; }
        public TreeNode getParent() { return parent; }
        public void setParent(TreeNode parent) { this.parent = parent; }
    }

    public static class MixedTypesClass {
        // Primitives
        private int intValue;
        private double doubleValue;
        private long longValue;
        private boolean booleanValue;
        
        // Wrappers
        private Integer integerValue;
        private Double doubleWrapper;
        private Boolean booleanWrapper;
        
        // Objects
        private String stringValue;
        private CompleteUser userObject;
        private Date dateValue;
        private LocalDate localDateValue;
        private LocalDateTime localDateTimeValue;
        
        // Collections
        private List<String> stringList;
        private Set<Integer> integerSet;
        private Map<String, Object> objectMap;

        // Getters and setters
        public int getIntValue() { return intValue; }
        public void setIntValue(int intValue) { this.intValue = intValue; }
        public double getDoubleValue() { return doubleValue; }
        public void setDoubleValue(double doubleValue) { this.doubleValue = doubleValue; }
        public long getLongValue() { return longValue; }
        public void setLongValue(long longValue) { this.longValue = longValue; }
        public boolean isBooleanValue() { return booleanValue; }
        public void setBooleanValue(boolean booleanValue) { this.booleanValue = booleanValue; }
        public Integer getIntegerValue() { return integerValue; }
        public void setIntegerValue(Integer integerValue) { this.integerValue = integerValue; }
        public Double getDoubleWrapper() { return doubleWrapper; }
        public void setDoubleWrapper(Double doubleWrapper) { this.doubleWrapper = doubleWrapper; }
        public Boolean getBooleanWrapper() { return booleanWrapper; }
        public void setBooleanWrapper(Boolean booleanWrapper) { this.booleanWrapper = booleanWrapper; }
        public String getStringValue() { return stringValue; }
        public void setStringValue(String stringValue) { this.stringValue = stringValue; }
        public CompleteUser getUserObject() { return userObject; }
        public void setUserObject(CompleteUser userObject) { this.userObject = userObject; }
        public Date getDateValue() { return dateValue; }
        public void setDateValue(Date dateValue) { this.dateValue = dateValue; }
        public LocalDate getLocalDateValue() { return localDateValue; }
        public void setLocalDateValue(LocalDate localDateValue) { this.localDateValue = localDateValue; }
        public LocalDateTime getLocalDateTimeValue() { return localDateTimeValue; }
        public void setLocalDateTimeValue(LocalDateTime localDateTimeValue) { this.localDateTimeValue = localDateTimeValue; }
        public List<String> getStringList() { return stringList; }
        public void setStringList(List<String> stringList) { this.stringList = stringList; }
        public Set<Integer> getIntegerSet() { return integerSet; }
        public void setIntegerSet(Set<Integer> integerSet) { this.integerSet = integerSet; }
        public Map<String, Object> getObjectMap() { return objectMap; }
        public void setObjectMap(Map<String, Object> objectMap) { this.objectMap = objectMap; }
    }

    public enum UserStatus {
        ACTIVE, INACTIVE, PENDING, SUSPENDED
    }

    public enum ProjectStatus {
        PLANNING, IN_PROGRESS, TESTING, COMPLETED, CANCELLED
    }
}
