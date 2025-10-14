package cane.brothers.markdown.convert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class HorizontalRuleBlockHandlerTest {
    
    private HorizontalRuleBlockHandler handler;
    
    @BeforeEach
    void setUp() {
        handler = new HorizontalRuleBlockHandler(null);
    }
    
    @Test
    void testCanHandleWithDashes() {
        assertTrue(handler.canHandle("---"));
        assertTrue(handler.canHandle("----"));
        assertTrue(handler.canHandle("---   "));
        assertFalse(handler.canHandle("--"));
        assertFalse(handler.canHandle("Some text --- more text"));
    }
    
    @Test
    void testCanHandleWithUnderscores() {
        assertTrue(handler.canHandle("___"));
        assertTrue(handler.canHandle("____"));
        assertTrue(handler.canHandle("___   "));
        assertFalse(handler.canHandle("__"));
    }
    
    @Test
    void testCanHandleWithAsterisks() {
        assertTrue(handler.canHandle("***"));
        assertTrue(handler.canHandle("****"));
        assertTrue(handler.canHandle("***   "));
        assertFalse(handler.canHandle("**"));
    }
    
    @Test
    void testProcessReturnsEmDash() {
        var result = handler.process("---");
        assertTrue(result.isConverted());
        assertEquals("\u2014\u2014\u2014", result.getValue());
    }
    
    @Test
    void testProcessWithUnderscores() {
        var result = handler.process("___");
        assertTrue(result.isConverted());
        assertEquals("\u2014\u2014\u2014", result.getValue());
    }
    
    @Test
    void testProcessWithAsterisks() {
        var result = handler.process("***");
        assertTrue(result.isConverted());
        assertEquals("\u2014\u2014\u2014", result.getValue());
    }
    
    @Test
    void testProcessWithSpaces() {
        var result = handler.process("---   ");
        assertTrue(result.isConverted());
        assertEquals("\u2014\u2014\u2014", result.getValue());
    }
    
    @Test
    void testGetPriority() {
        assertEquals(95, handler.getPriority());
    }
    
    @Test
    void testGetName() {
        assertEquals("horizontal-rule", handler.getName());
    }

    // @Nested
    //    class HorizontalRuleTests {
    //        @Test
    //        void testHorizontalRuleWithDashes() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("---");
    //            assertEquals("\u2014\u2014\u2014", out);
    //        }
    //
    //        @Test
    //        void testHorizontalRuleWithUnderscores() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("___");
    //            assertEquals("\u2014\u2014\u2014", out);
    //        }
    //
    //        @Test
    //        void testHorizontalRuleWithAsterisks() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("***");
    //            assertEquals("\u2014\u2014\u2014", out);
    //        }
    //
    //        @Test
    //        void testHorizontalRuleWithSpaces() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("---   ");
    //            assertEquals("\u2014\u2014\u2014", out);
    //        }
    //
    //        @Test
    //        void testHorizontalRuleWithMoreThanThree() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("------");
    //            assertEquals("\u2014\u2014\u2014", out);
    //        }
    //
    //        @Test
    //        void testNotHorizontalRule() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("--");
    //            assertEquals("--", out);
    //        }
    //
    //        @Test
    //        void testHorizontalRuleInText() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("Some text --- more text");
    //            assertEquals("Some text --- more text", out);
    //        }
    //    }
}
