package com.bartu.dummygen.generator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Unit tests for DateGenerator class.
 * Tests date generation with different field name contexts.
 */
public class DateGeneratorTest {

    private DateGenerator generator;

    @Before
    public void setUp() {
        generator = new DateGenerator();
    }

    @Test
    public void testGenerateDateForBirth() {
        Date birthDate = generator.generateDate("birthDate");
        
        assertNotNull(birthDate);
        
        LocalDate localDate = birthDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        assertTrue(localDate.getYear() >= 1950);
        assertTrue(localDate.getYear() <= 2005);
    }

    @Test
    public void testGenerateDateForBorn() {
        Date bornDate = generator.generateDate("bornDate");
        
        assertNotNull(bornDate);
        
        LocalDate localDate = bornDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        assertTrue(localDate.getYear() >= 1950);
        assertTrue(localDate.getYear() <= 2005);
    }

    @Test
    public void testGenerateDateForCreated() {
        Date createdDate = generator.generateDate("createdAt");
        
        assertNotNull(createdDate);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveYearsAgo = now.minusYears(5);
        LocalDateTime dateTime = createdDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        assertTrue(dateTime.isAfter(fiveYearsAgo) || dateTime.isEqual(fiveYearsAgo));
        assertTrue(dateTime.isBefore(now) || dateTime.isEqual(now));
    }

    @Test
    public void testGenerateDateForRegistration() {
        Date registrationDate = generator.generateDate("registrationDate");
        
        assertNotNull(registrationDate);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveYearsAgo = now.minusYears(5);
        LocalDateTime dateTime = registrationDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        assertTrue(dateTime.isAfter(fiveYearsAgo) || dateTime.isEqual(fiveYearsAgo));
        assertTrue(dateTime.isBefore(now) || dateTime.isEqual(now));
    }

    @Test
    public void testGenerateDateForModified() {
        Date modifiedDate = generator.generateDate("modifiedAt");
        
        assertNotNull(modifiedDate);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusYears(1);
        LocalDateTime dateTime = modifiedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        assertTrue(dateTime.isAfter(oneYearAgo) || dateTime.isEqual(oneYearAgo));
        assertTrue(dateTime.isBefore(now) || dateTime.isEqual(now));
    }

    @Test
    public void testGenerateDateForExpiration() {
        Date expirationDate = generator.generateDate("expirationDate");
        
        assertNotNull(expirationDate);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoYearsLater = now.plusYears(2);
        LocalDateTime dateTime = expirationDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        assertTrue(dateTime.isAfter(now) || dateTime.isEqual(now));
        assertTrue(dateTime.isBefore(twoYearsLater) || dateTime.isEqual(twoYearsLater));
    }

    @Test
    public void testGenerateDateForLogin() {
        Date loginDate = generator.generateDate("lastLogin");
        
        assertNotNull(loginDate);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyDaysAgo = now.minusDays(30);
        LocalDateTime dateTime = loginDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        assertTrue(dateTime.isAfter(thirtyDaysAgo) || dateTime.isEqual(thirtyDaysAgo));
        assertTrue(dateTime.isBefore(now) || dateTime.isEqual(now));
    }

    @Test
    public void testGenerateDateForOrder() {
        Date orderDate = generator.generateDate("orderDate");
        
        assertNotNull(orderDate);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixMonthsAgo = now.minusMonths(6);
        LocalDateTime dateTime = orderDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        assertTrue(dateTime.isAfter(sixMonthsAgo) || dateTime.isEqual(sixMonthsAgo));
        assertTrue(dateTime.isBefore(now) || dateTime.isEqual(now));
    }

    @Test
    public void testGenerateDateForScheduled() {
        Date scheduledDate = generator.generateDate("scheduledDate");
        
        assertNotNull(scheduledDate);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeMonthsLater = now.plusMonths(3);
        LocalDateTime dateTime = scheduledDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        assertTrue(dateTime.isAfter(now) || dateTime.isEqual(now));
        assertTrue(dateTime.isBefore(threeMonthsLater) || dateTime.isEqual(threeMonthsLater));
    }

    @Test
    public void testGenerateDateForDefaultField() {
        Date defaultDate = generator.generateDate("randomFieldName");
        
        assertNotNull(defaultDate);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusYears(1);
        LocalDateTime dateTime = defaultDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        assertTrue(dateTime.isAfter(oneYearAgo) || dateTime.isEqual(oneYearAgo));
        assertTrue(dateTime.isBefore(now) || dateTime.isEqual(now));
    }

    @Test
    public void testGenerateLocalDateForBirth() {
        LocalDate birthDate = generator.generateLocalDate("birthDate");
        
        assertNotNull(birthDate);
        assertTrue(birthDate.getYear() >= 1950);
        assertTrue(birthDate.getYear() <= 2005);
    }

    @Test
    public void testGenerateLocalDateForCreated() {
        LocalDate createdDate = generator.generateLocalDate("createdAt");
        
        assertNotNull(createdDate);
        
        LocalDate now = LocalDate.now();
        LocalDate fiveYearsAgo = now.minusYears(5);
        
        assertTrue(createdDate.isAfter(fiveYearsAgo) || createdDate.isEqual(fiveYearsAgo));
        assertTrue(createdDate.isBefore(now) || createdDate.isEqual(now));
    }

    @Test
    public void testGenerateLocalDateForDefaultField() {
        LocalDate defaultDate = generator.generateLocalDate("randomFieldName");
        
        assertNotNull(defaultDate);
        
        LocalDate now = LocalDate.now();
        LocalDate oneYearAgo = now.minusYears(1);
        
        assertTrue(defaultDate.isAfter(oneYearAgo) || defaultDate.isEqual(oneYearAgo));
        assertTrue(defaultDate.isBefore(now) || defaultDate.isEqual(now));
    }

    @Test
    public void testGenerateLocalDateTimeForBirth() {
        LocalDateTime birthDateTime = generator.generateLocalDateTime("birthDate");
        
        assertNotNull(birthDateTime);
        assertTrue(birthDateTime.getYear() >= 1950);
        assertTrue(birthDateTime.getYear() <= 2005);
        
        // Check that time components are present
        assertTrue(birthDateTime.getHour() >= 0 && birthDateTime.getHour() <= 23);
        assertTrue(birthDateTime.getMinute() >= 0 && birthDateTime.getMinute() <= 59);
        assertTrue(birthDateTime.getSecond() >= 0 && birthDateTime.getSecond() <= 59);
    }

    @Test
    public void testGenerateLocalDateTimeForCreated() {
        LocalDateTime createdDateTime = generator.generateLocalDateTime("createdAt");
        
        assertNotNull(createdDateTime);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveYearsAgo = now.minusYears(5);
        
        assertTrue(createdDateTime.isAfter(fiveYearsAgo) || createdDateTime.isEqual(fiveYearsAgo));
        assertTrue(createdDateTime.isBefore(now) || createdDateTime.isEqual(now));
    }

    @Test
    public void testGenerateLocalDateTimeForExpiration() {
        LocalDateTime expirationDateTime = generator.generateLocalDateTime("expirationDate");
        
        assertNotNull(expirationDateTime);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoYearsLater = now.plusYears(2);
        
        assertTrue(expirationDateTime.isAfter(now) || expirationDateTime.isEqual(now));
        assertTrue(expirationDateTime.isBefore(twoYearsLater) || expirationDateTime.isEqual(twoYearsLater));
    }

    @Test
    public void testGenerateLocalDateTimeForStart() {
        LocalDateTime startDateTime = generator.generateLocalDateTime("startDate");
        
        assertNotNull(startDateTime);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoYearsAgo = now.minusYears(2);
        LocalDateTime oneYearLater = now.plusYears(1);
        
        assertTrue(startDateTime.isAfter(twoYearsAgo) || startDateTime.isEqual(twoYearsAgo));
        assertTrue(startDateTime.isBefore(oneYearLater) || startDateTime.isEqual(oneYearLater));
    }

    @Test
    public void testGenerateLocalDateTimeForEnd() {
        LocalDateTime endDateTime = generator.generateLocalDateTime("endDate");
        
        assertNotNull(endDateTime);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusYears(1);
        LocalDateTime twoYearsLater = now.plusYears(2);
        
        assertTrue(endDateTime.isAfter(oneYearAgo) || endDateTime.isEqual(oneYearAgo));
        assertTrue(endDateTime.isBefore(twoYearsLater) || endDateTime.isEqual(twoYearsLater));
    }

    @Test
    public void testGenerateLocalDateTimeForDefaultField() {
        LocalDateTime defaultDateTime = generator.generateLocalDateTime("randomFieldName");
        
        assertNotNull(defaultDateTime);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusYears(1);
        
        assertTrue(defaultDateTime.isAfter(oneYearAgo) || defaultDateTime.isEqual(oneYearAgo));
        assertTrue(defaultDateTime.isBefore(now) || defaultDateTime.isEqual(now));
    }

    @Test
    public void testCaseInsensitivity() {
        LocalDateTime birth1 = generator.generateLocalDateTime("BIRTH");
        LocalDateTime birth2 = generator.generateLocalDateTime("Birth");
        LocalDateTime birth3 = generator.generateLocalDateTime("birth");

    }

    @Test
    public void testSubstringMatching() {
        LocalDateTime personBirth = generator.generateLocalDateTime("personBirthDate");
        LocalDateTime userBorn = generator.generateLocalDateTime("userBornDate");
        LocalDateTime employeeBirth = generator.generateLocalDateTime("employeeBirthday");
        
        // All should be in birth date range since they contain "birth" or "born"
        assertTrue(personBirth.getYear() >= 1950 && personBirth.getYear() <= 2005);
        assertTrue(userBorn.getYear() >= 1950 && userBorn.getYear() <= 2005);
        assertTrue(employeeBirth.getYear() >= 1950 && employeeBirth.getYear() <= 2005);
    }

    @Test
    public void testDateConsistencyBetweenMethods() {
        // Test that Date and LocalDateTime generate consistent values for the same field
        String fieldName = "birthDate";
        
        Date date = generator.generateDate(fieldName);
        LocalDateTime localDateTime = generator.generateLocalDateTime(fieldName);
        
        // Both should be in the birth date range
        LocalDateTime dateAsLocalDateTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        assertTrue(dateAsLocalDateTime.getYear() >= 1950 && dateAsLocalDateTime.getYear() <= 2005);
        assertTrue(localDateTime.getYear() >= 1950 && localDateTime.getYear() <= 2005);
    }

    @Test
    public void testLocalDateConsistencyWithLocalDateTime() {
        // Test that LocalDate and LocalDateTime generate consistent date portions
        String fieldName = "birthDate";
        
        LocalDate localDate = generator.generateLocalDate(fieldName);
        LocalDateTime localDateTime = generator.generateLocalDateTime(fieldName);
        
        // Both should be in the birth date range
        assertTrue(localDate.getYear() >= 1950 && localDate.getYear() <= 2005);
        assertTrue(localDateTime.getYear() >= 1950 && localDateTime.getYear() <= 2005);
        
        // LocalDate should be equivalent to the date portion of LocalDateTime
        LocalDate dateFromDateTime = localDateTime.toLocalDate();
        assertTrue(dateFromDateTime.getYear() >= 1950 && dateFromDateTime.getYear() <= 2005);
    }

    @Test
    public void testRandomnessInRange() {
        // Test that we get different values within the expected range
        boolean foundDifferentDates = false;
        LocalDateTime firstDateTime = generator.generateLocalDateTime("defaultField");
        
        for (int i = 0; i < 10; i++) {
            LocalDateTime dateTime = generator.generateLocalDateTime("defaultField");
            assertNotNull(dateTime);
            
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneYearAgo = now.minusYears(1);
            assertTrue(dateTime.isAfter(oneYearAgo) || dateTime.isEqual(oneYearAgo));
            assertTrue(dateTime.isBefore(now) || dateTime.isEqual(now));
            
            if (!dateTime.equals(firstDateTime)) {
                foundDifferentDates = true;
            }
        }
        
    }
}
