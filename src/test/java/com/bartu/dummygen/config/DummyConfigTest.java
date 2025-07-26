package com.bartu.dummygen.config;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

/**
 * Unit tests for DummyConfig class.
 * Tests configuration options and builder pattern.
 */
public class DummyConfigTest {

    @Test
    public void testDefaultConstructor() {
        DummyConfig config = new DummyConfig();
        
        assertEquals("en", config.getLanguage());
        assertTrue(config.isMeaningfulContent());
        assertEquals(3, config.getMaxListSize());
        assertFalse(config.isAllowNullFields());
    }

    @Test
    public void testParameterizedConstructor() {
        DummyConfig config = new DummyConfig("tr", false, 5, true);
        
        assertEquals("tr", config.getLanguage());
        assertFalse(config.isMeaningfulContent());
        assertEquals(5, config.getMaxListSize());
        assertTrue(config.isAllowNullFields());
    }

    @Test
    public void testDefaultConfig() {
        DummyConfig config = DummyConfig.defaultConfig();
        
        assertEquals("en", config.getLanguage());
        assertTrue(config.isMeaningfulContent());
        assertEquals(3, config.getMaxListSize());
        assertFalse(config.isAllowNullFields());
    }

    @Test
    public void testBuilderPattern() {
        DummyConfig config = DummyConfig.builder()
                .language("tr")
                .meaningfulContent(false)
                .maxListSize(10)
                .allowNullFields(true)
                .build();
        
        assertEquals("tr", config.getLanguage());
        assertFalse(config.isMeaningfulContent());
        assertEquals(10, config.getMaxListSize());
        assertTrue(config.isAllowNullFields());
    }

    @Test
    public void testBuilderWithDefaults() {
        DummyConfig config = DummyConfig.builder().build();
        
        assertEquals("en", config.getLanguage());
        assertTrue(config.isMeaningfulContent());
        assertEquals(3, config.getMaxListSize());
        assertFalse(config.isAllowNullFields());
    }

    @Test
    public void testBuilderChaining() {
        DummyConfig config = DummyConfig.builder()
                .language("tr")
                .meaningfulContent(true)
                .maxListSize(7)
                .allowNullFields(false)
                .build();
        
        assertEquals("tr", config.getLanguage());
        assertTrue(config.isMeaningfulContent());
        assertEquals(7, config.getMaxListSize());
        assertFalse(config.isAllowNullFields());
    }

    @Test
    public void testSettersAndGetters() {
        DummyConfig config = new DummyConfig();
        
        config.setLanguage("tr");
        assertEquals("tr", config.getLanguage());
        
        config.setMeaningfulContent(false);
        assertFalse(config.isMeaningfulContent());
        
        config.setMaxListSize(8);
        assertEquals(8, config.getMaxListSize());
        
        config.setAllowNullFields(true);
        assertTrue(config.isAllowNullFields());
    }

    @Test
    public void testBuilderLanguageConfiguration() {
        DummyConfig configEn = DummyConfig.builder().language("en").build();
        DummyConfig configTr = DummyConfig.builder().language("tr").build();
        
        assertEquals("en", configEn.getLanguage());
        assertEquals("tr", configTr.getLanguage());
    }

    @Test
    public void testBuilderMeaningfulContentConfiguration() {
        DummyConfig meaningfulConfig = DummyConfig.builder().meaningfulContent(true).build();
        DummyConfig gibberishConfig = DummyConfig.builder().meaningfulContent(false).build();
        
        assertTrue(meaningfulConfig.isMeaningfulContent());
        assertFalse(gibberishConfig.isMeaningfulContent());
    }

    @Test
    public void testBuilderMaxListSizeConfiguration() {
        DummyConfig config1 = DummyConfig.builder().maxListSize(1).build();
        DummyConfig config10 = DummyConfig.builder().maxListSize(10).build();
        
        assertEquals(1, config1.getMaxListSize());
        assertEquals(10, config10.getMaxListSize());
    }

    @Test
    public void testBuilderAllowNullFieldsConfiguration() {
        DummyConfig allowNullConfig = DummyConfig.builder().allowNullFields(true).build();
        DummyConfig noNullConfig = DummyConfig.builder().allowNullFields(false).build();
        
        assertTrue(allowNullConfig.isAllowNullFields());
        assertFalse(noNullConfig.isAllowNullFields());
    }

    @Test
    public void testMultipleBuilderInstances() {
        DummyConfig.Builder builder1 = DummyConfig.builder();
        DummyConfig.Builder builder2 = DummyConfig.builder();
        
        assertNotSame(builder1, builder2);
        
        DummyConfig config1 = builder1.language("en").build();
        DummyConfig config2 = builder2.language("tr").build();
        
        assertNotSame(config1, config2);
        assertEquals("en", config1.getLanguage());
        assertEquals("tr", config2.getLanguage());
    }
}
