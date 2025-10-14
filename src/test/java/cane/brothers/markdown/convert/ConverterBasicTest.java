package cane.brothers.markdown.convert;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ConverterBasicTest {

    @Test
    void inlineCode() {
        TelegramMarkdownConverter c = new TelegramMarkdownConverter();
        String out = c.convert("Use `a` and `b``");
        assertEquals("Use \\`a\\` and \\`b\\`\\`", out);
    }

    @Nested
    class PizzaTests {
        
        @Test
        void testConvertPizzaMdFromResources() throws Exception {
            String input = loadResourceAsString("/pizza.md");
            String expected = loadResourceAsString("/pizza.txt");
   
            assertNotNull(input, "pizza.md should exist in resources");
            assertNotNull(expected, "pizza.txt should exist in resources");

            TelegramMarkdownConverter converter = new TelegramMarkdownConverter();
            String actual = converter.convert(input);

            assertNotNull(actual, "Converted output should not be null");
            assertEquals(expected, actual);
        }

        private String loadResourceAsString(String resourcePath) throws Exception {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(getClass().getResourceAsStream(resourcePath))))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        }

       
    }


//    @Nested
//    class HeadingTests {
//        @Test
//        void testDefaultHeadingToBold() {
//            // default behavior is to convert headings to bold
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("# Title");
//            assertEquals("*Title*", out);
//        }
//
//        @Test
//        void testConvertHeadingToBold() {
//            // explicitly set heading handling to BOLD_LINE
//            ConversionOptions options = ConversionOptions.builder()
//                    .headingHandling(HeadingHandling.BOLD_LINE)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertHeadingLine("# Header 1");
//            assertEquals("*Header 1*", result.getValue(), "Heading should be converted to bold");
//        }
//
//        @Test
//        void testNoneConvertHeadingLine() {
//            // explicitly set heading handling to NONE
//            ConversionOptions options = ConversionOptions.builder()
//                    .headingHandling(HeadingHandling.NONE)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertHeadingLine("# Header 1");
//            assertFalse(result.isConverted(), "Heading should not be converted");
//            assertNull(result.getValue(), "Heading should not be converted");
//        }
//    }

//    @Test
//    void linkEscaping() {
//        TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//        String out = c.convert("[l(i)n]k](https://example.com/a b)");
//        assertEquals("[l\\(i\\)n\\]k](https://example.com/a%20b)", out);
//    }

//    @Nested
//    class OrderedListTests {
//
//        @Test
//        void testDefaultOrderedListKeepNumbers() {
//            // default behavior is to keep numbers
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("1. First\n2. Second");
//            assertEquals("1. First\n2. Second", out);
//        }
//
//        @Test
//        void testConvertOrderedListLineKeepNumbers() {
//            // explicitly set ordered list handling to KEEP_NUMBERED
//            ConversionOptions options = ConversionOptions.builder()
//                    .orderedListHandling(OrderedListHandling.KEEP_NUMBERED)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertOrderedListLine("1. First item");
//            assertEquals("1. First item", result.getValue(), "Ordered list numbers should be kept");
//        }
//
//        @Test
//        void testNoneConvertOrderedListRestartAtOne() {
//            // explicitly set ordered list handling to NONE
//            ConversionOptions options = ConversionOptions.builder()
//                    .orderedListHandling(OrderedListHandling.NONE)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertOrderedListLine("1. First item\n2. Second item");
//            assertFalse(result.isConverted(), "Ordered list should not be converted");
//            assertNull(result.getValue(), "Ordered list numbers should be kept");
//        }
//
//        @Test
//        void testOrderedListPatternMatches() {
//            String line = "1. First item";
//            // Example pattern for ordered list: starts with digits, dot, space
//            String pattern = "^\\d+\\.\\s+.*$";
//            assertTrue(line.matches(pattern), "Pattern should match '1. First item'");
//        }
//    }

//    @Nested
//    class UnorderedListTests {
//        @Test
//        void testUnorderedListReplaceStarWithDash() {
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("* one\n+ two\n- three");
//            assertEquals("- one\n- two\n- three", out);
//        }
//
//
//        @Test
//        void testConvertUnorderedListLineKeepNumbers() {
//            // explicitly set unordered list handling to REPLACE_BULLET_WITH_DASH
//            ConversionOptions options = ConversionOptions.builder()
//                    .listHandling(ListHandling.REPLACE_BULLET_WITH_DASH)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertListLine("* one");
//            assertEquals("- one", result.getValue(), "Unordered list numbers should be kept");
//        }
//
//        @Test
//        void testNoneConvertUnorderedListRestartAtOne() {
//            // explicitly set ordered list handling to NONE
//            ConversionOptions options = ConversionOptions.builder()
//                    .listHandling(ListHandling.NONE)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertListLine("- First item");
//            assertFalse(result.isConverted(), "Ordered list should not be converted");
//            assertNull(result.getValue(), "Ordered list numbers should be kept");
//        }
//    }

//    @Nested
//    class BoldTests {
//        @Test
//        void testDefaultBoldConvertToTelegram() {
//            // default behavior is to convert bold text to Telegram format
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("This is **bold** text");
//            assertEquals("This is *bold* text", out);
//        }
//
//        @Test
//        void testConvertBoldLineToTelegram() {
//            // explicitly set bold handling to CONVERT_TO_TELEGRAM
//            ConversionOptions options = ConversionOptions.builder()
//                    .boldHandling(BoldHandling.BOLD)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertBoldLine("This is **bold** text");
//            assertEquals("This is *bold* text", result.getValue(), "Bold text should be converted to Telegram format");
//        }
//
//        @Test
//        void testConvertUnderscoreBoldToTelegram() {
//            // test __text__ format
//            ConversionOptions options = ConversionOptions.builder()
//                    .boldHandling(BoldHandling.BOLD)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertBoldLine("This is __bold__ text");
//            assertEquals("This is *bold* text", result.getValue(), "Underscore bold text should be converted to Telegram format");
//        }
//
//        @Test
//        void testNoneConvertBoldLine() {
//            // explicitly set bold handling to NONE
//            ConversionOptions options = ConversionOptions.builder()
//                    .boldHandling(BoldHandling.NONE)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertBoldLine("This is **bold** text");
//            assertFalse(result.isConverted(), "Bold text should not be converted");
//            assertNull(result.getValue(), "Bold text should not be converted");
//        }
//
//        @Test
//        void testBoldPatternMatches() {
//            String line = "This is **bold** text";
//            // Example pattern for bold text: **text**
//            String pattern = "\\*\\*([^*]+)\\*\\*";
//            assertTrue(line.matches(".*" + pattern + ".*"), "Pattern should match '**bold**'");
//        }
//
//        @Test
//        void testMixedBoldFormats() {
//            // test mixed **text** and __text__ formats
//            ConversionOptions options = ConversionOptions.builder()
//                    .boldHandling(BoldHandling.BOLD)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertBoldLine("**First** and __second__ bold");
//            assertEquals("*First* and *second* bold", result.getValue(), "Mixed bold formats should be converted");
//        }
//    }

//    @Nested
//    class ItalicTests {
//        @Test
//        void testDefaultItalicConvertToTelegram() {
//            // default behavior is to convert italic text to Telegram format
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("This is *italic* text");
//            assertEquals("This is _italic_ text", out);
//        }
//
//        @Test
//        void testConvertItalicLineToTelegram() {
//            // explicitly set italic handling to ITALIC
//            ConversionOptions options = ConversionOptions.builder()
//                    .italicHandling(ItalicHandling.ITALIC)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertItalicLine("This is *italic* text");
//            assertEquals("This is _italic_ text", result.getValue(), "Italic text should be converted to Telegram format");
//        }
//
//        @Test
//        void testConvertUnderscoreItalicToTelegram() {
//            // test _text_ format (should remain unchanged)
//            ConversionOptions options = ConversionOptions.builder()
//                    .italicHandling(ItalicHandling.ITALIC)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertItalicLine("This is _italic_ text");
//            assertEquals("This is _italic_ text", result.getValue(), "Underscore italic text should remain unchanged");
//        }
//
//        @Test
//        void testNoneConvertItalicLine() {
//            // explicitly set italic handling to NONE
//            ConversionOptions options = ConversionOptions.builder()
//                    .italicHandling(ItalicHandling.NONE)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertItalicLine("This is *italic* text");
//            assertFalse(result.isConverted(), "Italic text should not be converted");
//            assertNull(result.getValue(), "Italic text should not be converted");
//        }
//
//        @Test
//        void testItalicPatternMatches() {
//            String line = "This is *italic* text";
//            // Example pattern for italic text: *text*
//            String pattern = "\\*([^*]+?)\\*";
//            assertTrue(line.matches(".*" + pattern + ".*"), "Pattern should match '*italic*'");
//        }
//
//        @Test
//        void testMixedItalicFormats() {
//            // test mixed *text* and _text_ formats
//            ConversionOptions options = ConversionOptions.builder()
//                    .italicHandling(ItalicHandling.ITALIC)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertItalicLine("*First* and _second_ italic");
//            assertEquals("_First_ and _second_ italic", result.getValue(), "Mixed italic formats should be converted");
//        }
//
//        @Test
//        void testItalicDoesNotMatchBold() {
//            // test that **text** is not matched as italic
//            ConversionOptions options = ConversionOptions.builder()
//                    .italicHandling(ItalicHandling.ITALIC)
//                    .build();
//            TelegramMarkdownConverter converter = new TelegramMarkdownConverter(options);
//            var result = converter.convertItalicLine("This is **bold** text");
//            assertFalse(result.isConverted(), "Bold text should not be matched as italic");
//        }
//    }

//    @Nested
//    class EmphasisTests {
//        @Test
//        void testBoldAsteriskFormat() {
//            // Test **text** -> *text*
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("This is **bold** text");
//            assertEquals("This is *bold* text", out);
//        }
//
//        @Test
//        void testBoldUnderscoreFormat() {
//            // Test __text__ -> *text*
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("This is __bold__ text");
//            assertEquals("This is *bold* text", out);
//        }
//
//        @Test
//        void testItalicAsteriskFormat() {
//            // Test *text* -> _text_
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("This is *italic* text");
//            assertEquals("This is _italic_ text", out);
//        }
//
//        @Test
//        void testItalicUnderscoreFormat() {
//            // Test _text_ -> _text_ (unchanged)
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("This is _italic_ text");
//            assertEquals("This is _italic_ text", out);
//        }
//
//        @Test
//        void testStrikethroughFormat() {
//            // Test ~~text~~ -> ~text~ (but ~ gets escaped)
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("This is ~~strikethrough~~ text");
//            assertEquals("This is \\~strikethrough\\~ text", out);
//        }
//
//        @Test
//        void testSpoilerFormat() {
//            // Test ||text|| -> ||text|| (unchanged)
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("This is ||spoiler|| text");
//            assertEquals("This is ||spoiler|| text", out);
//        }
//
//        @Test
//        void testMixedEmphasisFormats() {
//            // Test mixed formats
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("**Bold**, *italic*, ~~strikethrough~~, ||spoiler||");
//            assertEquals("*Bold*, _italic_, ~strikethrough~, ||spoiler||", out);
//        }
//
//        @Test
//        void testNestedEmphasis() {
//            // Test nested emphasis (should not work in standard markdown)
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("**Bold with *italic* inside**");
//            // This should convert to *Bold with _italic_ inside*
//            assertEquals("*Bold with _italic_ inside*", out);
//        }
//
//        @Test
//        void testMultipleBoldInLine() {
//            // Test multiple bold instances in one line
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("**First** and **second** bold");
//            assertEquals("*First* and *second* bold", out);
//        }
//
//        @Test
//        void testMultipleItalicInLine() {
//            // Test multiple italic instances in one line
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("*First* and *second* italic");
//            assertEquals("_First_ and _second_ italic", out);
//        }
//
//        @Test
//        void testBoldAndItalicTogether() {
//            // Test bold and italic in the same line
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("**Bold** and *italic* together");
//            assertEquals("*Bold* and _italic_ together", out);
//        }
//
//        @Test
//        void testEmptyEmphasis() {
//            // Test empty emphasis markers
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("Empty ** and * markers");
//            assertEquals("Empty ** and * markers", out);
//        }
//
//        @Test
//        void testEscapedCharacters() {
//            // Test with special characters that need escaping
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("**Bold with (parentheses)** and *italic with [brackets]*");
//            assertEquals("*Bold with \\(parentheses\\)* and _italic with \\[brackets\\]_", out);
//        }
//
//        @Test
//        void testComplexMixedFormat() {
//            // Test complex mixed formatting
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("**Bold** with *italic* and ~~strikethrough~~ and ||spoiler||");
//            assertEquals("*Bold* with _italic_ and ~strikethrough~ and ||spoiler||", out);
//        }
//
//        @Test
//        void testNoEmphasis() {
//            // Test text without any emphasis
//            TelegramMarkdownConverter c = new TelegramMarkdownConverter();
//            String out = c.convert("Plain text without any formatting");
//            assertEquals("Plain text without any formatting", out);
//        }
//    }
}
