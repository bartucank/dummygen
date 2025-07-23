package com.bartu.dummygen.generator;

import java.util.Random;

/**
 * Generator for numeric values with context-aware ranges.
 * 
 * This class generates realistic numeric values by analyzing field names
 * and applying appropriate ranges. For example:
 * - Age fields: 1-80 years
 * - Salary fields: 30,000-130,000
 * - Percentage fields: 0-100
 * - Price fields: 1-1000 with decimal precision
 * 
 * @author Bartu Can Palamut
 * @since 1.0.0
 */
public class NumberGenerator {

    private final Random random;

    public NumberGenerator() {
        this.random = new Random();
    }

    /**
     * Generates an integer value based on field name context.
     * 
     * Analyzes the field name to determine appropriate range:
     * - "age" → 1-80
     * - "year" → 1990-2019
     * - "salary" → 30,000-130,000
     * - "price", "cost", "amount" → 1-1000
     * - "percentage", "score" → 0-100
     * - "id" → 1-10,000
     * - Default → 0-999
     * 
     * @param fieldName the name of the field (used for context analysis)
     * @return an integer value appropriate for the field context
     */
    public int generateInt(String fieldName) {
        String lowerFieldName = fieldName.toLowerCase();
        
        if (lowerFieldName.contains("age")) {
            return random.nextInt(80) + 1; // 1-80
        } else if (lowerFieldName.contains("year")) {
            return random.nextInt(30) + 1990; // 1990-2019
        } else if (lowerFieldName.contains("month")) {
            return random.nextInt(12) + 1; // 1-12
        } else if (lowerFieldName.contains("day")) {
            return random.nextInt(28) + 1; // 1-28
        } else if (lowerFieldName.contains("hour")) {
            return random.nextInt(24); // 0-23
        } else if (lowerFieldName.contains("minute") || lowerFieldName.contains("second")) {
            return random.nextInt(60); // 0-59
        } else if (lowerFieldName.contains("price") || lowerFieldName.contains("cost") || lowerFieldName.contains("amount")) {
            return random.nextInt(1000) + 1; // 1-1000
        } else if (lowerFieldName.contains("salary")) {
            return random.nextInt(100000) + 30000; // 30000-130000
        } else if (lowerFieldName.contains("count") || lowerFieldName.contains("quantity") || lowerFieldName.contains("size")) {
            return random.nextInt(100) + 1; // 1-100
        } else if (lowerFieldName.contains("id")) {
            return random.nextInt(10000) + 1; // 1-10000
        } else if (lowerFieldName.contains("percentage") || lowerFieldName.contains("percent")) {
            return random.nextInt(101); // 0-100
        } else if (lowerFieldName.contains("score")) {
            return random.nextInt(101); // 0-100
        } else if (lowerFieldName.contains("level")) {
            return random.nextInt(10) + 1; // 1-10
        } else if (lowerFieldName.contains("weight")) {
            return random.nextInt(200) + 30; // 30-229 kg
        } else if (lowerFieldName.contains("height")) {
            return random.nextInt(100) + 140; // 140-239 cm
        } else {
            return random.nextInt(1000); // Default: 0-999
        }
    }

    /**
     * Generates a long value based on field name context.
     * 
     * For ID fields, generates positive long values.
     * For timestamp fields, generates recent timestamps.
     * For size/length fields, generates positive values.
     * Otherwise, delegates to integer generation logic.
     * 
     * @param fieldName the name of the field (used for context analysis)
     * @return a long value appropriate for the field context
     */
    public long generateLong(String fieldName) {
        String lowerFieldName = fieldName.toLowerCase();
        
        if (lowerFieldName.contains("id")) {
            return random.nextLong() & Long.MAX_VALUE; // Positive long
        } else if (lowerFieldName.contains("timestamp") || lowerFieldName.contains("time")) {
            return System.currentTimeMillis() - random.nextInt(365 * 24 * 60 * 60 * 1000); // Last year
        } else if (lowerFieldName.contains("size") || lowerFieldName.contains("length") || lowerFieldName.contains("bytes")) {
            return random.nextLong() & 0x7FFFFFFFFFFFFFFFL; // Positive long for sizes
        } else {
            return (long) generateInt(fieldName); // Use int logic for other cases
        }
    }

    /**
     * Generates a double value based on field name context.
     * 
     * Provides decimal precision for appropriate fields:
     * - "price", "cost" → 1.00-1001.00 (2 decimal places)
     * - "percentage" → 0.00-100.00
     * - "rating" → 0.00-5.00
     * - "latitude" → -90.0 to 90.0
     * - "longitude" → -180.0 to 180.0
     * - "temperature" → -10.0 to 50.0
     * 
     * @param fieldName the name of the field (used for context analysis)
     * @return a double value appropriate for the field context
     */
    public double generateDouble(String fieldName) {
        String lowerFieldName = fieldName.toLowerCase();
        
        if (lowerFieldName.contains("price") || lowerFieldName.contains("cost") || lowerFieldName.contains("amount")) {
            return Math.round((random.nextDouble() * 1000 + 1) * 100.0) / 100.0; // 1.00-1001.00
        } else if (lowerFieldName.contains("percentage") || lowerFieldName.contains("percent")) {
            return Math.round(random.nextDouble() * 100 * 100.0) / 100.0; // 0.00-100.00
        } else if (lowerFieldName.contains("rating") || lowerFieldName.contains("score")) {
            return Math.round((random.nextDouble() * 5) * 100.0) / 100.0; // 0.00-5.00
        } else if (lowerFieldName.contains("latitude") || lowerFieldName.contains("lat")) {
            return (random.nextDouble() * 180) - 90; // -90 to 90
        } else if (lowerFieldName.contains("longitude") || lowerFieldName.contains("lng") || lowerFieldName.contains("lon")) {
            return (random.nextDouble() * 360) - 180; // -180 to 180
        } else if (lowerFieldName.contains("weight")) {
            return Math.round((random.nextDouble() * 200 + 30) * 100.0) / 100.0; // 30.00-230.00 kg
        } else if (lowerFieldName.contains("height")) {
            return Math.round((random.nextDouble() * 100 + 140) * 100.0) / 100.0; // 140.00-240.00 cm
        } else if (lowerFieldName.contains("temperature") || lowerFieldName.contains("temp")) {
            return Math.round((random.nextDouble() * 60 - 10) * 100.0) / 100.0; // -10.00 to 50.00°C
        } else {
            return Math.round(random.nextDouble() * 1000 * 100.0) / 100.0; // Default: 0.00-1000.00
        }
    }

    public float generateFloat(String fieldName) {
        return (float) generateDouble(fieldName);
    }

}
