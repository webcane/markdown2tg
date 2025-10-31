package cane.brothers.markdown.convert;


import org.commonmark.node.*;
import org.commonmark.parser.Parser;


/**
 * Converter from Markdown to Telegram MarkdownV2 using commonmark-java
 */
public class MarkdownToTelegramConverter {

    // AST parser
    private final Parser parser;

    public MarkdownToTelegramConverter() {
        this.parser = Parser.builder().build();
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
        TelegramMarkdownVisitor visitor = new TelegramMarkdownVisitor();
        document.accept(visitor);

        return visitor.getResult();
    }

    /**
     * Построчная обработка (для больших файлов)
     */
//    public String convertLineByLine(String markdown) {
//        // С commonmark лучше обрабатывать весь документ сразу,
//        // так как он строит AST и учитывает контекст
//        // Но можно разбить на параграфы
//        String[] blocks = markdown.split("\n\n+");
//        StringBuilder result = new StringBuilder();
//
//        for (String block : blocks) {
//            if (!block.trim().isEmpty()) {
//                result.append(convert(block.trim()));
//                result.append("\n\n");
//            }
//        }
//
//        return result.toString().trim();
//    }
}
