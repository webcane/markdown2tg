package cane.brothers.tg.md.convert;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

/**
 * Converter from standard Markdown (CommonMark) to Telegram MarkdownV2
 * The main task is to properly escape characters that telegram requires to escape in MarkdownV2.
 * Using commonmark-java library for parsing and AST traversal.
 */
public class MarkdownToTelegramConverter {

    // AST parser
    private final Parser parser;

    private final TelegramMarkdownRenderer renderer;

    public MarkdownToTelegramConverter() {
        this.parser = Parser.builder().build();
        this.renderer = TelegramMarkdownRenderer.builder().build();
    }

    /**
     * Convert Markdown to Telegram MarkdownV2
     *
     * @param markdown the input markdown text
     * @return the converted text in Telegram MarkdownV2 format
     */
    public String convert(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return "";
        }

        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}
