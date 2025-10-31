package cane.brothers.markdown.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class HorizontalRuleBlockHandlerTest {

    private HorizontalRuleBlockHandler handler;

    @BeforeEach
    void setUp() {
        handler = new HorizontalRuleBlockHandler();
    }

    @ParameterizedTest
    @ValueSource(strings = {"---", "----", "---   "})
    void testCanHandleWithDashesTrue(String line) {
        var result = new ConversionResult<String>(line);
        assertTrue(handler.canHandle(result));
    }

    @ParameterizedTest
    @ValueSource(strings = {"--", "Some text --- more text"})
    void testCanHandleWithDashesFalse(String line) {
        var result = new ConversionResult<String>(line);
        assertFalse(handler.canHandle(result));
    }

    @ParameterizedTest
    @ValueSource(strings = {"___", "____", "___   "})
    void testCanHandleWithUnderscoresTrue(String line) {
        var result = new ConversionResult<String>(line);
        assertTrue(handler.canHandle(result));
    }

    @ParameterizedTest
    @ValueSource(strings = {"__"})
    void testCanHandleWithUnderscoresFalse(String line) {
        var result = new ConversionResult<String>(line);
        assertFalse(handler.canHandle(result));
    }

    @ParameterizedTest
    @ValueSource(strings = {"***", "****", "***   "})
    void testCanHandleWithAsterisksTrue(String line) {
        var result = new ConversionResult<String>(line);
        assertTrue(handler.canHandle(result));
    }

    @ParameterizedTest
    @ValueSource(strings = {"**"})
    void testCanHandleWithAsterisksFalse(String line) {
        var result = new ConversionResult<String>(line);
        assertFalse(handler.canHandle(result));
    }


    @ParameterizedTest
    @ValueSource(strings = {"---", "___", "***", "---   "})
    void testProcessReturnsEmDash(String line) {
        var result = new ConversionResult<String>(line);
        result = handler.apply(result);
        assertTrue(result.isConverted().orElse(false));
        assertEquals("———", result.getValue());
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
    //            assertEquals("———", out);
    //        }
    //
    //        @Test
    //        void testHorizontalRuleWithUnderscores() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("___");
    //            assertEquals("———", out);
    //        }
    //
    //        @Test
    //        void testHorizontalRuleWithAsterisks() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("***");
    //            assertEquals("———", out);
    //        }
    //
    //        @Test
    //        void testHorizontalRuleWithSpaces() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("---   ");
    //            assertEquals("———", out);
    //        }
    //
    //        @Test
    //        void testHorizontalRuleWithMoreThanThree() {
    //            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
    //            String out = c.convert("------");
    //            assertEquals("———", out);
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
