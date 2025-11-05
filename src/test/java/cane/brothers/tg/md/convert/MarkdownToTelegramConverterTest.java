package cane.brothers.tg.md.convert;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkdownToTelegramConverterTest {

    private final MarkdownToTelegramConverter converter = new MarkdownToTelegramConverter();

    @Nested
    class NullInputTests {
        @Test
        void testNullInput() {
            assertEquals("", converter.convert(null));
        }
    }

    @Nested
    class EmptyInputTests {
        @Test
        void testEmptyInput() {
            assertEquals("", converter.convert(""));
        }
    }

    @Nested
    class SimpleTextTests {
        @Test
        void testSimpleText() {
            assertEquals("Hello world\\.", converter.convert("Hello world."));
        }
    }

    @Nested
    class HeadingTests {
        @Test
        void testHeading() {
            assertEquals("*Heading*", converter.convert("# Heading"));
        }
    }

    @Nested
    class BoldTests {
        @Test
        void testBold() {
            assertEquals("*bold*", converter.convert("**bold**"));
        }
    }

    @Nested
    class ItalicTests {
        @Test
        void testItalic() {
            assertEquals("_italic_", converter.convert("*italic*"));
        }
    }

    @Nested
    class InlineCodeTests {
        @Test
        void testInlineCode() {
            assertEquals("This is `code`\\.", converter.convert("This is `code`."));
        }
    }

    @Nested
    class FencedCodeBlockTests {
        @Test
        void testFencedCodeBlock() {
            String md = """
                ```
                code block
                ```
                """;
            String expected = "```\ncode block\n```";
            assertEquals(expected, converter.convert(md));
        }

        @Test
        void testFencedCodeBlockWithLanguage() {
            String md = """
                ```java
                int x = 1;
                ```
                """;
            String expected = "```java\nint x = 1;\n```";
            assertEquals(expected, converter.convert(md));
        }
    }

    @Nested
    class LinkImageTests {
        @Test
        void testLink() {
            assertEquals("[Telegram](https://telegram.org)", converter.convert("[Telegram](https://telegram.org)"));
        }

        @Test
        void testImage() {
            assertEquals("[alt](https://img.com/img.png)", converter.convert("![alt](https://img.com/img.png)"));
        }
    }

    @Nested
    class ListTests {
        @Test
        void testBulletList() {
            String md = "- item1\n- item2";
            String expected = "\\- item1\n\\- item2";
            assertEquals(expected, converter.convert(md));
        }

        @Test
        void testOrderedList() {
            String md = "1. first\n2. second";
            String expected = "• first\n• second";
            assertEquals(expected, converter.convert(md));
        }
    }

    @Nested
    class BlockQuoteTests {
        @Test
        void testBlockQuote() {
            assertEquals(">quoted", converter.convert("> quoted"));
        }
    }

    @Nested
    class ThematicBreakTests {
        @Test
        void testThematicBreak() {
            assertEquals("\\-\\-\\-", converter.convert("---"));
            assertEquals("\\-\\-\\-", converter.convert("***"));
        }
    }
}
