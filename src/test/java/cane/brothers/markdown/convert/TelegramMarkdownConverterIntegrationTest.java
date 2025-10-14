package cane.brothers.markdown.convert;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelegramMarkdownConverterIntegrationTest {
    
    @Test
    void testHorizontalRuleIntegration() {
        TelegramMarkdownConverter converter = new TelegramMarkdownConverter();
        
        // Test horizontal rule with dashes
        String result1 = converter.convert("---");
        assertEquals("———", result1);
        
        // Test horizontal rule with underscores
        String result2 = converter.convert("___");
        assertEquals("———", result2);
        
        // Test horizontal rule with asterisks
        String result3 = converter.convert("***");
        assertEquals("———", result3);
        
        // Test horizontal rule with spaces
        String result4 = converter.convert("---   ");
        assertEquals("———", result4);
        
        // Test horizontal rule with more than three characters
        String result5 = converter.convert("------");
        assertEquals("———", result5);
    }
    
    @Test
    void testHorizontalRuleInContext() {
        TelegramMarkdownConverter converter = new TelegramMarkdownConverter();
        
        String input = "First paragraph\n\n---\n\nSecond paragraph";
        String expected = "First paragraph\n\n———\n\nSecond paragraph";
        String result = converter.convert(input);
        assertEquals(expected, result);
    }
    
    @Test
    void testNotHorizontalRule() {
        TelegramMarkdownConverter converter = new TelegramMarkdownConverter();
        
        // Two dashes should not be converted to horizontal rule
        String result1 = converter.convert("--");
        assertNotEquals("———", result1); // Should not be horizontal rule
        
        // Horizontal rule in text should not be converted
        String result2 = converter.convert("Some text --- more text");
        assertNotEquals("Some text ——— more text", result2); // Should not be horizontal rule
    }
}
