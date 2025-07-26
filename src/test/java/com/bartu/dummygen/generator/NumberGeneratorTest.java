package com.bartu.dummygen.generator;

import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Unit tests for NumberGenerator class.
 * Tests numeric value generation with different field name contexts.
 */
public class NumberGeneratorTest {

    private NumberGenerator generator;

    @Before
    public void setUp() {
        generator = new NumberGenerator();
    }

    @Test
    public void testGenerateIntForAge() {
        int age = generator.generateInt("age");
        assertTrue(age >= 1 && age <= 80);
    }

    @Test
    public void testGenerateIntForUserAge() {
        int userAge = generator.generateInt("userAge");
        assertTrue(userAge >= 1 && userAge <= 80);
    }

    @Test
    public void testGenerateIntForYear() {
        int year = generator.generateInt("year");
        assertTrue(year >= 1990 && year <= 2019);
    }

    @Test
    public void testGenerateIntForBirthYear() {
        int birthYear = generator.generateInt("birthYear");
        assertTrue(birthYear >= 1990 && birthYear <= 2019);
    }

    @Test
    public void testGenerateIntForMonth() {
        int month = generator.generateInt("month");
        assertTrue(month >= 1 && month <= 12);
    }

    @Test
    public void testGenerateIntForDay() {
        int day = generator.generateInt("day");
        assertTrue(day >= 1 && day <= 28);
    }

    @Test
    public void testGenerateIntForHour() {
        int hour = generator.generateInt("hour");
        assertTrue(hour >= 0 && hour <= 23);
    }

    @Test
    public void testGenerateIntForMinute() {
        int minute = generator.generateInt("minute");
        assertTrue(minute >= 0 && minute <= 59);
    }

    @Test
    public void testGenerateIntForSecond() {
        int second = generator.generateInt("second");
        assertTrue(second >= 0 && second <= 59);
    }

    @Test
    public void testGenerateIntForPrice() {
        int price = generator.generateInt("price");
        assertTrue(price >= 1 && price <= 1000);
    }

    @Test
    public void testGenerateIntForCost() {
        int cost = generator.generateInt("cost");
        assertTrue(cost >= 1 && cost <= 1000);
    }

    @Test
    public void testGenerateIntForAmount() {
        int amount = generator.generateInt("amount");
        assertTrue(amount >= 1 && amount <= 1000);
    }

    @Test
    public void testGenerateIntForSalary() {
        int salary = generator.generateInt("salary");
        assertTrue(salary >= 30000 && salary <= 129999);
    }

    @Test
    public void testGenerateIntForCount() {
        int count = generator.generateInt("count");
        assertTrue(count >= 1 && count <= 100);
    }

    @Test
    public void testGenerateIntForQuantity() {
        int quantity = generator.generateInt("quantity");
        assertTrue(quantity >= 1 && quantity <= 100);
    }

    @Test
    public void testGenerateIntForSize() {
        int size = generator.generateInt("size");
        assertTrue(size >= 1 && size <= 100);
    }

    @Test
    public void testGenerateIntForId() {
        int id = generator.generateInt("id");
        assertTrue(id >= 1 && id <= 10000);
    }

    @Test
    public void testGenerateIntForUserId() {
        int userId = generator.generateInt("userId");
        assertTrue(userId >= 1 && userId <= 10000);
    }

    @Test
    public void testGenerateIntForPercentage() {
        int percentage = generator.generateInt("percentage");
        assertTrue(percentage >= 0 && percentage <= 100);
    }

    @Test
    public void testGenerateIntForPercent() {
        int percent = generator.generateInt("percent");
        assertTrue(percent >= 0 && percent <= 100);
    }

    @Test
    public void testGenerateIntForScore() {
        int score = generator.generateInt("score");
        assertTrue(score >= 0 && score <= 100);
    }

    @Test
    public void testGenerateIntForLevel() {
        int level = generator.generateInt("level");
        assertTrue(level >= 1 && level <= 10);
    }

    @Test
    public void testGenerateIntForWeight() {
        int weight = generator.generateInt("weight");
        assertTrue(weight >= 30 && weight <= 229);
    }

    @Test
    public void testGenerateIntForHeight() {
        int height = generator.generateInt("height");
        assertTrue(height >= 140 && height <= 239);
    }

    @Test
    public void testGenerateIntForDefaultField() {
        int defaultValue = generator.generateInt("randomFieldName");
        assertTrue(defaultValue >= 0 && defaultValue <= 999);
    }

    @Test
    public void testGenerateLongForId() {
        long id = generator.generateLong("id");
        assertTrue(id > 0); // Should be positive
    }

    @Test
    public void testGenerateLongForTimestamp() {
        long timestamp = generator.generateLong("timestamp");
        assertTrue(timestamp > 0);
        assertTrue(timestamp <= System.currentTimeMillis());
    }

    @Test
    public void testGenerateLongForTime() {
        long time = generator.generateLong("time");
        assertTrue(time > 0);
        assertTrue(time <= System.currentTimeMillis());
    }

    @Test
    public void testGenerateLongForSize() {
        long size = generator.generateLong("size");
        assertTrue(size >= 0);
    }

    @Test
    public void testGenerateLongForLength() {
        long length = generator.generateLong("length");
        assertTrue(length >= 0);
    }

    @Test
    public void testGenerateLongForBytes() {
        long bytes = generator.generateLong("bytes");
        assertTrue(bytes >= 0);
    }

    @Test
    public void testGenerateLongForDefaultField() {
        long defaultValue = generator.generateLong("randomFieldName");
        assertTrue(defaultValue >= 0 && defaultValue <= 999);
    }

    @Test
    public void testGenerateDoubleForPrice() {
        double price = generator.generateDouble("price");
        assertTrue(price >= 1.0 && price <= 1001.0);
        // Check decimal precision (should have at most 2 decimal places)
        assertEquals(price, Math.round(price * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForCost() {
        double cost = generator.generateDouble("cost");
        assertTrue(cost >= 1.0 && cost <= 1001.0);
        assertEquals(cost, Math.round(cost * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForAmount() {
        double amount = generator.generateDouble("amount");
        assertTrue(amount >= 1.0 && amount <= 1001.0);
        assertEquals(amount, Math.round(amount * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForPercentage() {
        double percentage = generator.generateDouble("percentage");
        assertTrue(percentage >= 0.0 && percentage <= 100.0);
        assertEquals(percentage, Math.round(percentage * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForPercent() {
        double percent = generator.generateDouble("percent");
        assertTrue(percent >= 0.0 && percent <= 100.0);
        assertEquals(percent, Math.round(percent * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForRating() {
        double rating = generator.generateDouble("rating");
        assertTrue(rating >= 0.0 && rating <= 5.0);
        assertEquals(rating, Math.round(rating * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForScore() {
        double score = generator.generateDouble("score");
        assertTrue(score >= 0.0 && score <= 5.0);
        assertEquals(score, Math.round(score * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForLatitude() {
        double latitude = generator.generateDouble("latitude");
        assertTrue(latitude >= -90.0 && latitude <= 90.0);
    }

    @Test
    public void testGenerateDoubleForLat() {
        double lat = generator.generateDouble("lat");
        assertTrue(lat >= -90.0 && lat <= 90.0);
    }

    @Test
    public void testGenerateDoubleForLongitude() {
        double longitude = generator.generateDouble("longitude");
        assertTrue(longitude >= -180.0 && longitude <= 180.0);
    }

    @Test
    public void testGenerateDoubleForLng() {
        double lng = generator.generateDouble("lng");
        assertTrue(lng >= -180.0 && lng <= 180.0);
    }

    @Test
    public void testGenerateDoubleForLon() {
        double lon = generator.generateDouble("lon");
        assertTrue(lon >= -180.0 && lon <= 180.0);
    }

    @Test
    public void testGenerateDoubleForWeight() {
        double weight = generator.generateDouble("weight");
        assertTrue(weight >= 30.0 && weight <= 230.0);
        assertEquals(weight, Math.round(weight * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForHeight() {
        double height = generator.generateDouble("height");
        assertTrue(height >= 140.0 && height <= 240.0);
        assertEquals(height, Math.round(height * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForTemperature() {
        double temperature = generator.generateDouble("temperature");
        assertTrue(temperature >= -10.0 && temperature <= 50.0);
        assertEquals(temperature, Math.round(temperature * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForTemp() {
        double temp = generator.generateDouble("temp");
        assertTrue(temp >= -10.0 && temp <= 50.0);
        assertEquals(temp, Math.round(temp * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateDoubleForDefaultField() {
        double defaultValue = generator.generateDouble("randomFieldName");
        assertTrue(defaultValue >= 0.0 && defaultValue <= 1000.0);
        assertEquals(defaultValue, Math.round(defaultValue * 100.0) / 100.0, 0.001);
    }

    @Test
    public void testGenerateFloatForPrice() {
        float price = generator.generateFloat("price");
        assertTrue(price >= 1.0f && price <= 1001.0f);
    }

    @Test
    public void testGenerateFloatForDefaultField() {
        float defaultValue = generator.generateFloat("randomFieldName");
        assertTrue(defaultValue >= 0.0f && defaultValue <= 1000.0f);
    }

    @Test
    public void testCaseInsensitivity() {
        int age1 = generator.generateInt("AGE");
        int age2 = generator.generateInt("Age");
        int age3 = generator.generateInt("age");
        
        // All should be in age range
        assertTrue(age1 >= 1 && age1 <= 80);
        assertTrue(age2 >= 1 && age2 <= 80);
        assertTrue(age3 >= 1 && age3 <= 80);
    }

    @Test
    public void testSubstringMatching() {
        int personAge = generator.generateInt("personAge");
        int userAge = generator.generateInt("userAge");
        int employeeAge = generator.generateInt("employeeAge");
        
        // All should be in age range since they contain "age"
        assertTrue(personAge >= 1 && personAge <= 80);
        assertTrue(userAge >= 1 && userAge <= 80);
        assertTrue(employeeAge >= 1 && employeeAge <= 80);
    }

}
