package com.bartu.dummygen.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generator for Date and time-related values with context-aware ranges.
 * 
 * This class generates realistic dates and times by analyzing field names
 * and applying appropriate date ranges. Examples:
 * - Birth dates: 1950-2005
 * - Registration/creation dates: last 5 years
 * - Expiration dates: future dates up to 2 years ahead
 * - Login dates: last 30 days
 * 
 * Supports Java 8+ time API (LocalDate, LocalDateTime) as well as
 * legacy Date class for backward compatibility.
 * 
 * @author Bartu Can Palamut
 * @since 1.0.0
 */
public class DateGenerator {

    private final Random random;

    public DateGenerator() {
        this.random = new Random();
    }

    /**
     * Generates a Date object based on field name context.
     * 
     * Converts the generated LocalDateTime to a Date object for
     * compatibility with legacy code that uses java.util.Date.
     * 
     * @param fieldName the name of the field (used for context analysis)
     * @return a Date object with contextually appropriate value
     */
    public Date generateDate(String fieldName) {
        LocalDateTime localDateTime = generateLocalDateTime(fieldName);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Generates a LocalDate based on field name context.
     * 
     * Returns only the date portion (no time) of the generated timestamp.
     * Uses the same context analysis as generateLocalDateTime().
     * 
     * @param fieldName the name of the field (used for context analysis)
     * @return a LocalDate with contextually appropriate value
     */
    public LocalDate generateLocalDate(String fieldName) {
        return generateLocalDateTime(fieldName).toLocalDate();
    }

    /**
     * Generates a LocalDateTime based on field name context.
     * 
     * Analyzes the field name to determine appropriate date range:
     * - "birth", "born" → 1950-2005
     * - "created", "registration" → last 5 years
     * - "modified", "updated" → last year to now
     * - "expir", "due", "deadline" → now to 2 years ahead
     * - "login", "access" → last 30 days
     * - "order", "purchase" → last 6 months
     * - "scheduled", "appointment" → next 3 months
     * - Default → last year to now
     * 
     * @param fieldName the name of the field (used for context analysis)
     * @return a LocalDateTime with contextually appropriate value
     */
    public LocalDateTime generateLocalDateTime(String fieldName) {
        String lowerFieldName = fieldName.toLowerCase();
        
        if (lowerFieldName.contains("birth") || lowerFieldName.contains("born")) {
            // Birth dates: 1950 - 2005
            return generateRandomDateTimeBetween(
                LocalDateTime.of(1950, 1, 1, 0, 0),
                LocalDateTime.of(2005, 12, 31, 23, 59)
            );
        } else if (lowerFieldName.contains("created") || lowerFieldName.contains("registration") || lowerFieldName.contains("signup")) {
            // Registration/creation dates: last 5 years
            return generateRandomDateTimeBetween(
                LocalDateTime.now().minusYears(5),
                LocalDateTime.now()
            );
        } else if (lowerFieldName.contains("modified") || lowerFieldName.contains("updated") || lowerFieldName.contains("changed")) {
            // Modified dates: last year to now
            return generateRandomDateTimeBetween(
                LocalDateTime.now().minusYears(1),
                LocalDateTime.now()
            );
        } else if (lowerFieldName.contains("expir") || lowerFieldName.contains("due") || lowerFieldName.contains("deadline")) {
            // Future dates: now to 2 years ahead
            return generateRandomDateTimeBetween(
                LocalDateTime.now(),
                LocalDateTime.now().plusYears(2)
            );
        } else if (lowerFieldName.contains("start")) {
            // Start dates: last 2 years to 1 year ahead
            return generateRandomDateTimeBetween(
                LocalDateTime.now().minusYears(2),
                LocalDateTime.now().plusYears(1)
            );
        } else if (lowerFieldName.contains("end") || lowerFieldName.contains("finish")) {
            // End dates: last year to 2 years ahead
            return generateRandomDateTimeBetween(
                LocalDateTime.now().minusYears(1),
                LocalDateTime.now().plusYears(2)
            );
        } else if (lowerFieldName.contains("login") || lowerFieldName.contains("access") || lowerFieldName.contains("visit")) {
            // Login/access dates: last 30 days
            return generateRandomDateTimeBetween(
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now()
            );
        } else if (lowerFieldName.contains("order") || lowerFieldName.contains("purchase") || lowerFieldName.contains("payment")) {
            // Order/purchase dates: last 6 months
            return generateRandomDateTimeBetween(
                LocalDateTime.now().minusMonths(6),
                LocalDateTime.now()
            );
        } else if (lowerFieldName.contains("scheduled") || lowerFieldName.contains("appointment") || lowerFieldName.contains("meeting")) {
            // Future scheduled dates: next 3 months
            return generateRandomDateTimeBetween(
                LocalDateTime.now(),
                LocalDateTime.now().plusMonths(3)
            );
        } else {
            // Default: last year to now
            return generateRandomDateTimeBetween(
                LocalDateTime.now().minusYears(1),
                LocalDateTime.now()
            );
        }
    }

    private LocalDateTime generateRandomDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        long startEpochDay = start.toLocalDate().toEpochDay();
        long endEpochDay = end.toLocalDate().toEpochDay();
        
        // Generate random date
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        
        // Generate random time
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        
        return LocalDateTime.of(randomDate, java.time.LocalTime.of(hour, minute, second));
    }

}
