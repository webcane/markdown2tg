package cane.brothers.tg.md.convert;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelegramMarkdownRendererTest {

    private String convert(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        TelegramMarkdownNodeRenderer renderer = new TelegramMarkdownNodeRenderer();
        renderer.render(document);
        return renderer.getResult();
    }

    @Nested
    class HeadingTests {
        @Test
        void testHeading() {
            String md = "# Heading";
            String expected = "*Heading*";
            assertEquals(expected, convert(md));
        }
    }

    @Nested
    class ParagraphTests {
        @Test
        void testParagraph() {
            String md = "Hello world.";
            String expected = "Hello world\\.";
            assertEquals(expected, convert(md));
        }
    }

    @Nested
    class EmphasisTests {
        @Test
        void testBoldAndItalic() {
            String md = "**bold** and *italic*";
            String expected = "*bold* and _italic_";
            assertEquals(expected, convert(md));
        }
    }

    @Nested
    class CodeTests {
        @Test
        void testInlineCode() {
            String md = "This is `code`.";
            String expected = "This is `code`\\.";
            assertEquals(expected, convert(md));
        }

        @Test
        void testFencedCodeBlock() {
            String md = """
                ```
                code block
                ```
                """;
            String expected = "```\ncode block\n```";
            assertEquals(expected, convert(md));
        }

        @Test
        void testFencedCodeBlockWithLanguage() {
            String md = """
                ```java
                int x = 1;
                ```
                """;
            String expected = "```java\nint x = 1;\n```";
            assertEquals(expected, convert(md));
        }
    }

    @Nested
    class LinkImageTests {
        @Test
        void testLink() {
            String md = "[Telegram](https://telegram.org)";
            String expected = "[Telegram](https://telegram.org)";
            assertEquals(expected, convert(md));
        }

        @Test
        void testImage() {
            String md = "![alt](https://img.com/img.png)";
            String expected = "[alt](https://img.com/img.png)";
            assertEquals(expected, convert(md));
        }
    }

    @Nested
    class ListTests {
        @Test
        void testBulletList() {
            String md = "- item1\n- item2";
            String expected = "\\- item1\n\\- item2";
            assertEquals(expected, convert(md));
        }

        @Test
        void testOrderedList() {
            String md = "1. first\n2. second";
            String expected = "• first\n• second";
            assertEquals(expected, convert(md));
        }
    }

    @Nested
    class BlockQuoteTests {
        @Test
        void testBlockQuote() {
            String md = "> quoted";
            String expected = ">quoted";
            assertEquals(expected, convert(md));
        }
    }

    @Nested
    class ThematicBreakTests {
        @Test
        void testThematicBreak() {
            String md = "---";
            String expected = "\\-\\-\\-";
            assertEquals(expected, convert(md));
        }
    }
}
