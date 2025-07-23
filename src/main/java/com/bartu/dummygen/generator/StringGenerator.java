package com.bartu.dummygen.generator;

import com.bartu.dummygen.config.DummyConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generator for String values with meaningful content support.
 * 
 * This class provides context-aware string generation based on field names and configuration.
 * It supports multiple languages (currently English and Turkish) and can generate either
 * meaningful content (real names, words, addresses) or gibberish for testing purposes.
 * 
 * The generator uses resource files to load language-specific data:
 * - names_en.txt / names_tr.txt - First names
 * - surnames_en.txt / surnames_tr.txt - Last names
 * - words.txt / words_tr.txt - Common words for sentence generation
 * 
 * @author Bartu Can Palamut
 * @since 1.0.0
 */
public class StringGenerator {

    private final Random random;
    private final List<String> firstNamesEn;
    private final List<String> firstNamesTr;
    private final List<String> lastNamesEn;
    private final List<String> lastNamesTr;
    private final List<String> words;
    private final List<String> wordsTr;
    private final List<String> domains;

    public StringGenerator() {
        this.random = new Random();
        this.firstNamesEn = loadDataFromResource("/data/names_en.txt");
        this.firstNamesTr = loadDataFromResource("/data/names_tr.txt");
        this.lastNamesEn = loadDataFromResource("/data/surnames_en.txt");
        this.lastNamesTr = loadDataFromResource("/data/surnames_tr.txt");
        this.words = loadDataFromResource("/data/words.txt");
        this.wordsTr = loadDataFromResource("/data/words_tr.txt");
        this.domains = initializeDomains();
    }

    /**
     * Generates a realistic full name based on the configuration.
     * 
     * Uses language-appropriate first and last names from resource files.
     * For Turkish configuration, uses Turkish names; for English (or default),
     * uses English names. In gibberish mode, generates random character sequences.
     * 
     * @param config the generation configuration
     * @return a full name like "John Smith" or "Ahmet Yılmaz"
     */
    public String generateName(DummyConfig config) {
        if (!config.isMeaningfulContent()) {
            return generateGibberish(5, 12);
        }

        List<String> firstNames = "tr".equals(config.getLanguage()) ? firstNamesTr : firstNamesEn;
        List<String> lastNames = "tr".equals(config.getLanguage()) ? lastNamesTr : lastNamesEn;

        String firstName = getRandomFromList(firstNames, "John");
        String lastName = getRandomFromList(lastNames, "Doe");

        return firstName + " " + lastName;
    }

    /**
     * Generates a realistic email address based on names and common domains.
     * 
     * Creates email addresses using the pattern: firstname.lastname@domain.com
     * The names are selected based on the configured language, and domains
     * are chosen from common email providers.
     * 
     * @param config the generation configuration
     * @return an email address like "john.smith@gmail.com"
     */
    public String generateEmail(DummyConfig config) {
        if (!config.isMeaningfulContent()) {
            return generateGibberish(5, 10) + "@" + generateGibberish(5, 8) + ".com";
        }

        List<String> firstNames = "tr".equals(config.getLanguage()) ? firstNamesTr : firstNamesEn;
        List<String> lastNames = "tr".equals(config.getLanguage()) ? lastNamesTr : lastNamesEn;

        String firstName = getRandomFromList(firstNames, "john").toLowerCase();
        String lastName = getRandomFromList(lastNames, "doe").toLowerCase();
        String domain = getRandomFromList(domains, "example.com");

        return firstName + "." + lastName + "@" + domain;
    }

    /**
     * Generates a username based on first names, optionally with numbers.
     * 
     * Creates usernames using lowercase first names, sometimes appended
     * with random numbers. Language configuration affects the name selection.
     * 
     * @param config the generation configuration
     * @return a username like "john" or "ahmet123"
     */
    public String generateUsername(DummyConfig config) {
        if (!config.isMeaningfulContent()) {
            return generateGibberish(6, 12);
        }

        List<String> firstNames = "tr".equals(config.getLanguage()) ? firstNamesTr : firstNamesEn;

        String firstName = getRandomFromList(firstNames, "user").toLowerCase();
        int number = random.nextInt(1000);

        return firstName + (random.nextBoolean() ? String.valueOf(number) : "");
    }

    /**
     * Generates a city name appropriate for the configured language.
     * 
     * For Turkish configuration, returns Turkish cities like İstanbul, Ankara.
     * For English/international configuration, returns international cities
     * like New York, London. Cities are selected from predefined lists.
     * 
     * @param config the generation configuration
     * @return a city name like "Antalya" or "New York"
     */
    public String generateCity(DummyConfig config) {
        if (!config.isMeaningfulContent()) {
            return generateGibberish(4, 10);
        }

        if ("tr".equals(config.getLanguage())) {
            List<String> turkishCities = getTurkishCities();
            return getRandomFromList(turkishCities, "Antalya");
        } else {
            List<String> internationalCities = getInternationalCities();
            return getRandomFromList(internationalCities, "New York");
        }
    }

    /**
     * Generates a realistic street address.
     * 
     * Creates addresses in the format: "[Number] [Street Name], [City]"
     * Street names are generated using random words from the vocabulary,
     * and cities are generated using the configured language preference.
     * 
     * @param config the generation configuration
     * @return an address like "123 Main Street, New York"
     */
    public String generateAddress(DummyConfig config) {
        if (!config.isMeaningfulContent()) {
            return generateGibberish(10, 20);
        }

        String streetNumber = String.valueOf(random.nextInt(9999) + 1);
        String streetName = getRandomFromList(words, "Main") + " Street";
        String city = generateCity(config);

        return streetNumber + " " + streetName + ", " + city;
    }

    /**
     * Generates a phone number appropriate for the configured language/region.
     * 
     * For Turkish configuration, generates numbers with +90 country code and
     * Turkish mobile prefixes (5xx). For English/international configuration,
     * generates numbers with +1 country code. Format: "+CC NNN NNN NNNN"
     * 
     * @param config the generation configuration
     * @return a phone number like "+90 532 123 4567" or "+1 555 123 4567"
     */
    public String generatePhoneNumber(DummyConfig config) {
        StringBuilder phone = new StringBuilder();
        
        if ("tr".equals(config.getLanguage())) {
            phone.append("+90 ");
            phone.append(random.nextInt(600) + 500); // Turkish mobile prefixes
        } else {
            phone.append("+1 ");
            phone.append(random.nextInt(900) + 100);
        }
        
        phone.append(" ");
        phone.append(random.nextInt(900) + 100);
        phone.append(" ");
        phone.append(String.format("%04d", random.nextInt(10000)));

        return phone.toString();
    }

    /**
     * Generates a website URL using common domain names.
     * 
     * Creates URLs in the format "https://www.[domain]" using domains
     * from predefined common domain list (gmail.com, yahoo.com, etc.).
     * 
     * @param config the generation configuration
     * @return a URL like "https://www.gmail.com"
     */
    public String generateUrl(DummyConfig config) {
        if (!config.isMeaningfulContent()) {
            return "https://" + generateGibberish(5, 10) + ".com";
        }

        String domain = getRandomFromList(domains, "example.com");
        return "https://www." + domain;
    }

    /**
     * Generates a sentence using random words from the vocabulary.
     * 
     * Creates sentences with 5-12 words selected from language-appropriate
     * word lists. The sentence is properly capitalized and ends with a period.
     * For Turkish configuration, uses Turkish words; for English, uses English words.
     * 
     * @param config the generation configuration
     * @return a sentence like "The application provides excellent business solution."
     */
    public String generateSentence(DummyConfig config) {
        if (!config.isMeaningfulContent()) {
            return generateGibberish(20, 50);
        }

        List<String> wordList = "tr".equals(config.getLanguage()) ? wordsTr : words;
        StringBuilder sentence = new StringBuilder();
        int wordCount = random.nextInt(8) + 5; // 5-12 words

        for (int i = 0; i < wordCount; i++) {
            if (i > 0) {
                sentence.append(" ");
            }
            sentence.append(getRandomFromList(wordList, "word"));
        }

        // Capitalize first letter and add period
        String result = sentence.toString();
        if (result.length() > 0) {
            result = Character.toUpperCase(result.charAt(0)) + result.substring(1) + ".";
        }

        return result;
    }

    /**
     * Generates a random string/word from the vocabulary.
     * 
     * Returns a single word selected from the language-appropriate word list.
     * Used for general string fields that don't match specific patterns.
     * 
     * @param config the generation configuration
     * @return a random word like "application" or "uygulama"
     */
    public String generateRandomString(DummyConfig config) {
        if (!config.isMeaningfulContent()) {
            return generateGibberish(5, 15);
        }

        List<String> wordList = "tr".equals(config.getLanguage()) ? wordsTr : words;
        return getRandomFromList(wordList, "random");
    }

    /**
     * Generates a random gibberish string of specified length range.
     * Used when meaningfulContent is disabled.
     * 
     * @param minLength minimum length of the generated string
     * @param maxLength maximum length of the generated string
     * @return a random string of lowercase letters
     */
    private String generateGibberish(int minLength, int maxLength) {
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder gibberish = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < length; i++) {
            gibberish.append(chars.charAt(random.nextInt(chars.length())));
        }

        return gibberish.toString();
    }

    /**
     * Safely retrieves a random element from a list, with fallback.
     * 
     * @param list the list to select from
     * @param fallback the fallback value if list is empty
     * @return a random element from the list, or fallback if list is empty
     */
    private String getRandomFromList(List<String> list, String fallback) {
        if (list.isEmpty()) {
            return fallback;
        }
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Loads string data from a resource file.
     * Each line in the file becomes an element in the returned list.
     * 
     * @param resourcePath the path to the resource file (e.g., "/data/names_en.txt")
     * @return a list of strings from the file, or empty list if file not found
     */
    private List<String> loadDataFromResource(String resourcePath) {
        List<String> data = new ArrayList<>();
        
        try (InputStream is = getClass().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            
            if (is == null) {
                // Resource not found, return empty list
                return data;
            }
            
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    data.add(line);
                }
            }
        } catch (IOException e) {
            // Log error but don't fail, return empty list
            System.err.println("Warning: Could not load resource " + resourcePath + ": " + e.getMessage());
        }
        
        return data;
    }

    private List<String> initializeCities() {
        List<String> cities = new ArrayList<>();
        cities.add("Istanbul");
        cities.add("Ankara");
        cities.add("Izmir");
        cities.add("Bursa");
        cities.add("Antalya");
        cities.add("Adana");
        cities.add("New York");
        cities.add("Los Angeles");
        cities.add("Chicago");
        cities.add("Houston");
        cities.add("Phoenix");
        cities.add("Philadelphia");
        cities.add("London");
        cities.add("Berlin");
        cities.add("Paris");
        cities.add("Madrid");
        cities.add("Rome");
        cities.add("Amsterdam");
        return cities;
    }

    private List<String> initializeDomains() {
        List<String> domains = new ArrayList<>();
        domains.add("gmail.com");
        domains.add("yahoo.com");
        domains.add("hotmail.com");
        domains.add("outlook.com");
        domains.add("example.com");
        domains.add("company.com");
        domains.add("test.org");
        domains.add("sample.net");
        return domains;
    }

    private List<String> getTurkishCities() {
        List<String> cities = new ArrayList<>();
        cities.add("Antalya");
        cities.add("Mersin");
        cities.add("İstanbul");
        cities.add("Ankara");
        cities.add("İzmir");
        cities.add("Bursa");
        cities.add("Adana");
        cities.add("Gaziantep");
        cities.add("Konya");
        cities.add("Mersin");
        cities.add("Diyarbakır");
        cities.add("Kayseri");
        cities.add("Eskişehir");
        cities.add("Samsun");
        cities.add("Denizli");
        cities.add("Trabzon");
        cities.add("Balıkesir");
        cities.add("Malatya");
        return cities;
    }

    private List<String> getInternationalCities() {
        List<String> cities = new ArrayList<>();
        cities.add("New York");
        cities.add("Los Angeles");
        cities.add("Chicago");
        cities.add("Houston");
        cities.add("Phoenix");
        cities.add("Philadelphia");
        cities.add("London");
        cities.add("Berlin");
        cities.add("Paris");
        cities.add("Madrid");
        cities.add("Rome");
        cities.add("Amsterdam");
        cities.add("Tokyo");
        cities.add("Sydney");
        cities.add("Toronto");
        cities.add("Vienna");
        return cities;
    }
}
