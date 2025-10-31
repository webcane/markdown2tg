package cane.brothers.markdown.convert;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkdownToTelegramConverterTest {

    private final MarkdownToTelegramConverter converter = new MarkdownToTelegramConverter();

    @Test
    void testNullInput() {
        assertEquals("", converter.convert(null));
    }

    @Test
    void testEmptyInput() {
        assertEquals("", converter.convert(""));
    }

    @Test
    void testSimpleText() {
        assertEquals("Hello world\\.", converter.convert("Hello world."));
    }

    @Test
    void testHeading() {
        assertEquals("*Heading*", converter.convert("# Heading"));
    }

    @Test
    void testBold() {
        assertEquals("*bold*", converter.convert("**bold**"));
    }

    @Test
    void testItalic() {
        assertEquals("_italic_", converter.convert("*italic*"));
    }

    @Test
    void testInlineCode() {
        assertEquals("This is `code`\\.", converter.convert("This is `code`."));
    }

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

    @Test
    void testLink() {
        assertEquals("[Telegram](https://telegram.org)", converter.convert("[Telegram](https://telegram.org)"));
    }

    @Test
    void testImage() {
        assertEquals("[alt](https://img.com/img.png)", converter.convert("![alt](https://img.com/img.png)"));
    }

    @Test
    void testBulletList() {
        String md = "- item1\n- item2";
        String expected = "• item1\n• item2";
        assertEquals(expected, converter.convert(md));
    }

    @Test
    void testOrderedList() {
        String md = "1. first\n2. second";
        String expected = "• first\n• second";
        assertEquals(expected, converter.convert(md));
    }

    @Test
    void testBlockQuote() {
        assertEquals(">quoted_", converter.convert("> quoted"));
    }

    @Test
    void testThematicBreak() {
        assertEquals("---", converter.convert("---"));
    }


    void testHorizontalRule() {
        assertEquals("———", converter.convert("---"));
        assertEquals("———", converter.convert("***"));
        assertEquals("———", converter.convert("___"));
    }

    @Test
    void testMain() {
        String md = """
                
                """;
//        assertEquals(expected, converter.convert(md));
        String result = converter.convert(md);
        System.out.println("=== Результат конвертации ===");
        System.out.println(result);

    }
}

