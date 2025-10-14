package cane.brothers.markdown;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ConverterBasicTest {
    @Nested
    class HeadingTests {
        @Test
        void testDefaultHeadingToBold() {
            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
            String out = c.convert("# Title");
            assertEquals("*Title*", out);
        }

        @Test
        void testConvertHeadingToBold() {
            // explicitly set heading handling to BOLD_LINE
            ConversionOptions options = ConversionOptions.builder()
                    .headingHandling(ConversionOptions.HeadingHandling.BOLD_LINE)
                    .build();
            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
            String result = converter.convertHeadingLine("# Header 1");
            assertEquals("*Header 1*", result, "Heading should be converted to bold");
        }

        @Test
        void testNoneConvertHeadingLine() {
            // explicitly set heading handling to NONE
            ConversionOptions options = ConversionOptions.builder()
                    .headingHandling(ConversionOptions.HeadingHandling.NONE)
                    .build();
            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
            String result = converter.convertHeadingLine("# Header 1");
            assertEquals("# Header 1", result, "Heading should not be converted");
        }
    }

    @Test
    void orderedListKeepNumbers() {
        TelegramMarkdownConverter c = new TelegramMarkdownConverter();
        String out = c.convert("1. First\n2. Second");
        assertEquals("1. First\n2. Second", out);
    }

    @Test
    void unorderedListReplaceStarWithDash() {
        TelegramMarkdownConverter c = new TelegramMarkdownConverter();
        String out = c.convert("* one\n+ two\n- three");
        assertEquals("- one\n- two\n- three", out);
    }

    @Test
    void linkEscaping() {
        TelegramMarkdownConverter c = new TelegramMarkdownConverter();
        String out = c.convert("[l(i)n]k](https://example.com/a b)");
        assertEquals("[l\\(i\\)n\\]k](https://example.com/a%20b)", out);
    }

    @Test
    void inlineCode() {
        TelegramMarkdownConverter c = new TelegramMarkdownConverter();
        String out = c.convert("Use `a` and `b``");
        assertEquals("Use \\`a\\` and \\`b\\`\\`", out);
    }
}
