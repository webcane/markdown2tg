package cane.brothers.markdown.convert;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TelegramMarkdownConverterFenceIntegrationTest {
    @Test
    void testSimpleFenceBlockIntegration() {
        TelegramMarkdownConverter converter = new TelegramMarkdownConverter();
        String input = "Some text before\n```\ncode line 1\ncode line 2\n```\nSome text after";
        String expected = "Some text before\n```\ncode line 1\ncode line 2\n```\nSome text after";
        assertEquals(expected, converter.convert(input));
    }

    @Test
    void testFenceBlockWithLanguageIntegration() {
        TelegramMarkdownConverter converter = new TelegramMarkdownConverter();
        String input = "Text before\n```java\nint x = 1;\n```\nText after";
        String expected = "Text before\n```\nint x = 1;\n```\nText after";
        assertEquals(expected, converter.convert(input));
    }

    @Test
    void testMultipleFenceBlocksIntegration() {
        TelegramMarkdownConverter converter = new TelegramMarkdownConverter();
        String input = "A\n```\nblock1\n```\nB\n```\nblock2\n```\nC";
        String expected = "A\n```\nblock1\n```\nB\n```\nblock2\n```\nC";
        assertEquals(expected, converter.convert(input));
    }

    @Test
    void testEmptyFenceBlockIntegration() {
        TelegramMarkdownConverter converter = new TelegramMarkdownConverter();
        String input = "before\n```\n```\nafter";
        String expected = "before\n```\n```\nafter";
        assertEquals(expected, converter.convert(input));
    }

    @Test
    void testNoInlineOrBlockProcessingInsideFence() {
        TelegramMarkdownConverter converter = new TelegramMarkdownConverter();
        String input = "```\n**bold**\n---\n```";
        String expected = "```\n**bold**\n---\n```";
        assertEquals(expected, converter.convert(input));
    }
}

