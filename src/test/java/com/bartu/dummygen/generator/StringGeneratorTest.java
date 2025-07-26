package com.bartu.dummygen.generator;

import com.bartu.dummygen.config.DummyConfig;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Unit tests for StringGenerator class.
 * Tests string generation with different contexts and configurations.
 */
public class StringGeneratorTest {

    private StringGenerator generator;

    @Before
    public void setUp() {
        generator = new StringGenerator();
    }

    @Test
    public void testGenerateNameWithEnglishConfig() {
        DummyConfig config = DummyConfig.builder().language("en").meaningfulContent(true).build();
        String name = generator.generateName(config);
        
        assertNotNull(name);
        assertFalse(name.trim().isEmpty());
        assertTrue(name.contains(" ")); // Should contain space between first and last name
        assertTrue(name.length() > 2);
    }

    @Test
    public void testGenerateNameWithTurkishConfig() {
        DummyConfig config = DummyConfig.builder().language("tr").meaningfulContent(true).build();
        String name = generator.generateName(config);
        
        assertNotNull(name);
        assertFalse(name.trim().isEmpty());
        assertTrue(name.contains(" ")); // Should contain space between first and last name
        assertTrue(name.length() > 2);
    }

    @Test
    public void testGenerateNameWithGibberishConfig() {
        DummyConfig config = DummyConfig.builder().meaningfulContent(false).build();
        String name = generator.generateName(config);
        
        assertNotNull(name);
        assertFalse(name.trim().isEmpty());
        assertTrue(name.length() >= 5);
        assertTrue(name.length() <= 12);
        assertTrue(name.matches("[a-z]+"));
    }

    @Test
    public void testGenerateEmailWithMeaningfulContent() {
        DummyConfig config = DummyConfig.builder().language("en").meaningfulContent(true).build();
        String email = generator.generateEmail(config);
        
        assertNotNull(email);
        assertTrue(email.contains("@"));
        assertTrue(email.contains("."));
        assertTrue(email.matches("[a-z]+\\.[a-z]+@[a-z]+\\.[a-z]+"));
    }

    @Test
    public void testGenerateEmailWithGibberish() {
        DummyConfig config = DummyConfig.builder().meaningfulContent(false).build();
        String email = generator.generateEmail(config);
        
        assertNotNull(email);
        assertTrue(email.contains("@"));
        assertTrue(email.endsWith(".com"));
        assertTrue(email.matches("[a-z]+@[a-z]+\\.com"));
    }

    @Test
    public void testGenerateUsernameWithMeaningfulContent() {
        DummyConfig config = DummyConfig.builder().language("en").meaningfulContent(true).build();
        String username = generator.generateUsername(config);
        
        assertNotNull(username);
        assertFalse(username.trim().isEmpty());
        assertTrue(username.matches("[a-z]+[0-9]*"));
    }

    @Test
    public void testGenerateUsernameWithGibberish() {
        DummyConfig config = DummyConfig.builder().meaningfulContent(false).build();
        String username = generator.generateUsername(config);
        
        assertNotNull(username);
        assertTrue(username.length() >= 6);
        assertTrue(username.length() <= 12);
        assertTrue(username.matches("[a-z]+"));
    }

    @Test
    public void testGenerateCityWithEnglishConfig() {
        DummyConfig config = DummyConfig.builder().language("en").meaningfulContent(true).build();
        String city = generator.generateCity(config);
        
        assertNotNull(city);
        assertFalse(city.trim().isEmpty());
        assertTrue(city.length() > 2);
    }

    @Test
    public void testGenerateCityWithTurkishConfig() {
        DummyConfig config = DummyConfig.builder().language("tr").meaningfulContent(true).build();
        String city = generator.generateCity(config);
        
        assertNotNull(city);
        assertFalse(city.trim().isEmpty());
        assertTrue(city.length() > 2);
    }

    @Test
    public void testGenerateCityWithGibberish() {
        DummyConfig config = DummyConfig.builder().meaningfulContent(false).build();
        String city = generator.generateCity(config);
        
        assertNotNull(city);
        assertTrue(city.length() >= 4);
        assertTrue(city.length() <= 10);
        assertTrue(city.matches("[a-z]+"));
    }

    @Test
    public void testGenerateAddressWithMeaningfulContent() {
        DummyConfig config = DummyConfig.builder().language("en").meaningfulContent(true).build();
        String address = generator.generateAddress(config);
        
        assertNotNull(address);
        assertTrue(address.contains("Street"));
        assertTrue(address.contains(","));
        assertTrue(address.matches("\\d+ .* Street, .*"));
    }

    @Test
    public void testGenerateAddressWithGibberish() {
        DummyConfig config = DummyConfig.builder().meaningfulContent(false).build();
        String address = generator.generateAddress(config);
        
        assertNotNull(address);
        assertTrue(address.length() >= 10);
        assertTrue(address.length() <= 20);
        assertTrue(address.matches("[a-z]+"));
    }

    @Test
    public void testGeneratePhoneNumberWithEnglishConfig() {
        DummyConfig config = DummyConfig.builder().language("en").meaningfulContent(true).build();
        String phone = generator.generatePhoneNumber(config);
        
        assertNotNull(phone);
        assertTrue(phone.startsWith("+1 "));
        assertTrue(phone.matches("\\+1 \\d{3} \\d{3} \\d{4}"));
    }

    @Test
    public void testGeneratePhoneNumberWithTurkishConfig() {
        DummyConfig config = DummyConfig.builder().language("tr").meaningfulContent(true).build();
        String phone = generator.generatePhoneNumber(config);
        
        assertNotNull(phone);
        assertTrue(phone.startsWith("+90 "));
    }

    @Test
    public void testGenerateUrlWithMeaningfulContent() {
        DummyConfig config = DummyConfig.builder().meaningfulContent(true).build();
        String url = generator.generateUrl(config);
        
        assertNotNull(url);
        assertTrue(url.startsWith("https://www."));
        assertTrue(url.contains("."));
    }

    @Test
    public void testGenerateUrlWithGibberish() {
        DummyConfig config = DummyConfig.builder().meaningfulContent(false).build();
        String url = generator.generateUrl(config);
        
        assertNotNull(url);
        assertTrue(url.startsWith("https://"));
        assertTrue(url.endsWith(".com"));
        assertTrue(url.matches("https://[a-z]+\\.com"));
    }

    @Test
    public void testGenerateSentenceWithMeaningfulContent() {
        DummyConfig config = DummyConfig.builder().language("en").meaningfulContent(true).build();
        String sentence = generator.generateSentence(config);
        
        assertNotNull(sentence);
        assertTrue(sentence.endsWith("."));
        assertTrue(Character.isUpperCase(sentence.charAt(0)));
        assertTrue(sentence.split(" ").length >= 5);
        assertTrue(sentence.split(" ").length <= 12);
    }

    @Test
    public void testGenerateSentenceWithGibberish() {
        DummyConfig config = DummyConfig.builder().meaningfulContent(false).build();
        String sentence = generator.generateSentence(config);
        
        assertNotNull(sentence);
        assertTrue(sentence.length() >= 20);
        assertTrue(sentence.length() <= 50);
        assertTrue(sentence.matches("[a-z]+"));
    }

    @Test
    public void testGenerateRandomStringWithMeaningfulContent() {
        DummyConfig config = DummyConfig.builder().language("en").meaningfulContent(true).build();
        String randomString = generator.generateRandomString(config);
        
        assertNotNull(randomString);
        assertFalse(randomString.trim().isEmpty());
        assertFalse(randomString.isEmpty());
    }

    @Test
    public void testGenerateRandomStringWithGibberish() {
        DummyConfig config = DummyConfig.builder().meaningfulContent(false).build();
        String randomString = generator.generateRandomString(config);
        
        assertNotNull(randomString);
        assertTrue(randomString.length() >= 5);
        assertTrue(randomString.length() <= 15);
        assertTrue(randomString.matches("[a-z]+"));
    }

    @Test
    public void testConsistencyWithSameConfig() {
        DummyConfig config = DummyConfig.builder().language("en").meaningfulContent(true).build();
        
        // Generate multiple values and ensure they are all valid
        for (int i = 0; i < 10; i++) {
            String name = generator.generateName(config);
            assertNotNull(name);
            assertTrue(name.contains(" "));
            
            String email = generator.generateEmail(config);
            assertNotNull(email);
            assertTrue(email.contains("@"));
            
            String city = generator.generateCity(config);
            assertNotNull(city);
            assertFalse(city.trim().isEmpty());
        }
    }

    @Test
    public void testLanguageDifference() {
        DummyConfig enConfig = DummyConfig.builder().language("en").meaningfulContent(true).build();
        DummyConfig trConfig = DummyConfig.builder().language("tr").meaningfulContent(true).build();
        
        // Generate names with both configs and check they're different pools
        String enName = generator.generateName(enConfig);
        String trName = generator.generateName(trConfig);
        
        assertNotNull(enName);
        assertNotNull(trName);
        // Both should be valid names but potentially from different language pools
        assertTrue(enName.contains(" "));
        assertTrue(trName.contains(" "));
    }
}
