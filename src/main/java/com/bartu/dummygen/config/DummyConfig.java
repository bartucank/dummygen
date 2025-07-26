package com.bartu.dummygen.config;

import java.util.Objects;

/**
 * Configuration class for DummyGen data generation.
 * 
 * Controls various aspects of dummy data generation including:
 * - Language: Determines which language-specific data to use (en/tr)
 * - Content Type: Whether to generate meaningful content or gibberish
 * - Collection Size: Maximum size for generated lists, sets, and maps
 * - Null Policy: Whether to allow null values in fields
 * 
 * This class supports both direct instantiation and builder pattern for
 * flexible configuration setup:
 * 
 * // Direct instantiation
 * DummyConfig config = new DummyConfig("tr", true, 5, false);
 * 
 * // Builder pattern
 * DummyConfig config = DummyConfig.builder()
 *     .language("tr")
 *     .meaningfulContent(true)
 *     .maxListSize(5)
 *     .build();
 * 
 * @author Bartu Can Palamut
 * @since 1.0.0
 */
public class DummyConfig {
    
    private String language;
    private boolean meaningfulContent;
    private int maxListSize;
    private boolean allowNullFields;

    /**
     * Default constructor with sensible defaults.
     * 
     * Creates configuration with:
     * - Language: English ("en")
     * - Meaningful content: enabled
     * - Max list size: 3
     * - Allow null fields: disabled
     */
    public DummyConfig() {
        this.language = "en";
        this.meaningfulContent = true;
        this.maxListSize = 3;
        this.allowNullFields = false;
    }

    /**
     * Constructor with all parameters.
     * 
     * @param language the language code ("en" or "tr")
     * @param meaningfulContent whether to use meaningful content or gibberish
     * @param maxListSize maximum size for generated lists
     * @param allowNullFields whether to allow null values for fields
     */
    public DummyConfig(String language, boolean meaningfulContent, int maxListSize, boolean allowNullFields) {
        this.language = language;
        this.meaningfulContent = meaningfulContent;
        this.maxListSize = maxListSize;
        this.allowNullFields = allowNullFields;
    }

    /**
     * Creates a default configuration instance.
     * 
     * @return default DummyConfig (English, meaningful content, max list size 3, no nulls)
     */
    public static DummyConfig defaultConfig() {
        return new DummyConfig();
    }

    /**
     * Builder pattern for creating DummyConfig instances.
     * 
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isMeaningfulContent() {
        return meaningfulContent;
    }

    public void setMeaningfulContent(boolean meaningfulContent) {
        this.meaningfulContent = meaningfulContent;
    }

    public int getMaxListSize() {
        return maxListSize;
    }

    public void setMaxListSize(int maxListSize) {
        this.maxListSize = maxListSize;
    }

    public boolean isAllowNullFields() {
        return allowNullFields;
    }

    public void setAllowNullFields(boolean allowNullFields) {
        this.allowNullFields = allowNullFields;
    }

    /**
     * Builder class for DummyConfig.
     */
    public static class Builder {
        private String language = "en";
        private boolean meaningfulContent = true;
        private int maxListSize = 3;
        private boolean allowNullFields = false;

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder meaningfulContent(boolean meaningfulContent) {
            this.meaningfulContent = meaningfulContent;
            return this;
        }

        public Builder maxListSize(int maxListSize) {
            this.maxListSize = Objects.nonNull(maxListSize) && maxListSize > 0 ? maxListSize : 3;
            return this;
        }

        public Builder allowNullFields(boolean allowNullFields) {
            this.allowNullFields = allowNullFields;
            return this;
        }

        public DummyConfig build() {
            return new DummyConfig(language, meaningfulContent, maxListSize, allowNullFields);
        }
    }
}
