package cane.brothers.markdown;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Конвертер из стандартного Markdown (CommonMark) в формат Telegram MarkdownV2.
 *
 * Основная задача - правильно экранировать символы, которые
 * Telegram требует экранировать в MarkdownV2.
 *
 * Этот класс использует библиотеку commonmark-java для парсинга
 * и обхода AST.
 */
public class MarkdownV2Converter {

//    // 1. Создаем парсер
//    private static final Parser parser = Parser.builder().build();
//
//    // 2. Создаем наш кастомный "отрисовщик" (Renderer)
//    private static class TelegramV2Renderer extends AbstractVisitor implements NodeRenderer {
//
//        private final StringBuilder out;
//
//        // Символы, которые *всегда* нужно экранировать в MarkdownV2
//        // ВАЖНО: Мы НЕ экранируем _, *, [, ], (, ), ~, `, #, +, -, =, |, {, }, .
//        // потому что мы *сами* их генерируем для разметки.
//        // Мы экранируем только то, что *должно* быть текстом.
//        // Но Telegram требует экранировать *всегда*, кроме как внутри code/pre
//        private static final Set<Character> ESCAPE_CHARS = Set.of(
//                '_', '*', '[', ']', '(', ')', '~', '`', '>',
//                '#', '+', '-', '=', '|', '{', '}', '.', '!'
//        );
//
//        // Паттерн для экранирования (более эффективный, чем replaceAll)
//        // Ищет любой символ из нашего набора ESCAPE_CHARS
//        private static final Pattern ESCAPE_PATTERN = Pattern.compile(
//                "([\\Q" + buildEscapeCharsString() + "\\E])"
//        );
//
//        // Паттерн для экранирования внутри `...`
//        private static final Pattern INLINE_CODE_ESCAPE_PATTERN = Pattern.compile("([`\\\\])");
//
//        // Паттерн для экранирования внутри ```...```
//        private static final Pattern PRE_CODE_ESCAPE_PATTERN = Pattern.compile("([`\\\\])");
//
//
//        TelegramV2Renderer(StringBuilder out) {
//            this.out = out;
//        }
//
//        @Override
//        public Set<Class<? extends Node>> getNodeTypes() {
//            return Set.of();
//        }
//
//        @Override
//        public void render(Node node) {
//            node.accept(this);
//        }
//
//        // --- Главная логика экранирования ---
//
//        /**
//         * Экранирует обычный текст.
//         */
//        private String escape(String text) {
//            // Заменяем каждый найденный спец. символ на \ + этот символ
//            return ESCAPE_PATTERN.matcher(text).replaceAll("\\\\$1");
//        }
//
//        /**
//         * Экранирует текст внутри `inline code`.
//         * В Telegram V2 тут нужно экранировать только ` и \
//         */
//        private String escapeInlineCode(String text) {
//            return INLINE_CODE_ESCAPE_PATTERN.matcher(text).replaceAll("\\\\$1");
//        }
//
//        /**
//         * Экранирует текст внутри ```pre formatted```.
//         * В Telegram V2 тут нужно экранировать только ` и \
//         */
//        private String escapePreCode(String text) {
//            return PRE_CODE_ESCAPE_PATTERN.matcher(text).replaceAll("\\\\$1");
//        }
//
//        private static String buildEscapeCharsString() {
//            // Собирает строку "_*[]()...", которую поймет regex
//            StringBuilder sb = new StringBuilder();
//            for (Character c : ESCAPE_CHARS) {
//                sb.append(c);
//            }
//            return sb.toString();
//        }
//
//        // --- Переопределенные методы для узлов AST ---
//
//        @Override
//        public void visit(Text text) {
//            // Это самый важный узел. Экранируем *только* обычный текст.
//            out.append(escape(text.getLiteral()));
//        }
//
//        @Override
//        public void visit(FormatContext.Bold bold) {
//            // **text** -> *text*
//            out.append('*');
//            visitChildren(bold);
//            out.append('*');
//        }
//
//        @Override
//        public void visit(Italic italic) {
//            // *text* -> _text_
//            out.append('_');
//            visitChildren(italic);
//            out.append('_');
//        }
//
//        @Override
//        public void visit(BlockQuote blockQuote) {
//            // > quote -> \> quote
//            // Telegram не поддерживает > как форматирование, поэтому экранируем
//            out.append(escape("> "));
//            visitChildren(blockQuote);
//        }
//
//        @Override
//        public void visit(Heading heading) {
//            // # Heading -> *Heading* (превращаем в bold)
//            out.append('*');
//            visitChildren(heading);
//            out.append("*\n");
//        }
//
//        @Override
//        public void visit(HardLineBreak hardLineBreak) {
//            out.append('\n');
//        }
//
//        @Override
//        public void visit(SoftLineBreak softLineBreak) {
//            out.append('\n');
//        }
//
//        @Override
//        public void visit(Paragraph paragraph) {
//            visitChildren(paragraph);
//            // Добавляем две новые строки после параграфа, если это не последний параграф
//            // (Логику можно усложнить, если нужно)
//            out.append("\n\n");
//        }
//
//        @Override
//        public void visit(Link link) {
//            // [text](url) -> [text](url)
//            // Формат совпадает, но `text` должен быть обработан,
//            // а `url` должен быть экранирован.
//            out.append('[');
//            visitChildren(link); // Обработает `text`, применив visit(Text) и др.
//            out.append("](");
//            // URL-ы в Telegram V2 требуют экранирования ) и \
//            String escapedUrl = link.getDestination().replace("\\", "\\\\").replace(")", "\\)");
//            out.append(escapedUrl);
//            out.append(')');
//        }
//
//        @Override
//        public void visit(Code code) {
//            // `inline code` -> `inline code`
//            // Формат тот же, но *внутреннее* экранирование другое!
//            out.append('`');
//            out.append(escapeInlineCode(code.getLiteral()));
//            out.append('`');
//        }
//
//        @Override
//        public void visit(FencedCodeBlock fencedCodeBlock) {
//            // ```lang\ncode\n``` -> ```lang\ncode\n```
//            // Формат тот же, но *внутреннее* экранирование другое!
//            out.append("```");
//            if (fencedCodeBlock.getInfo() != null && !fencedCodeBlock.getInfo().isEmpty()) {
//                out.append(fencedCodeBlock.getInfo()); // язык
//            }
//            out.append('\n');
//            out.append(escapePreCode(fencedCodeBlock.getLiteral()));
//            out.append("```\n");
//        }
//
//        @Override
//        public void visit(ListItem listItem) {
//            // * item 1 -> \* item 1
//            // 1. item 2 -> 1\. item 2
//            // Telegram V2 не поддерживает списки, превращаем в текст и экранируем
//            if (listItem.getParent() instanceof OrderedList) {
//                // Это элемент нумерованного списка.
//                // В реальном решении тут нужно было бы получить номер
//                out.append(escape("1. ")); // Упрощение
//            } else {
//                // Это элемент маркированного списка
//                out.append(escape("* ")); // Экранируем маркер
//            }
//            visitChildren(listItem);
//        }
//
//        // --- Посещаем, но ничего не делаем (просто обходим дочерние узлы) ---
//
//        @Override
//        public void visit(Document document) {
//            visitChildren(document);
//        }
//
//        @Override
//        public void visit(BulletList bulletList) {
//            visitChildren(bulletList);
//        }
//
//        @Override
//        public void visit(OrderedList orderedList) {
//            visitChildren(orderedList);
//        }
//
//        // Другие узлы (Image, HtmlBlock, etc.) можно добавить по необходимости
//
//        @Override
//        protected void visitChildren(Node parent) {
//            Node node = parent.getFirstChild();
//            while (node != null) {
//                Node next = node.getNext();
//                node.accept(this);
//                node = next;
//            }
//        }
//    }
//
//    /**
//     * Главный метод конвертации.
//     *
//     * @param standardMarkdown Исходный Markdown (CommonMark).
//     * @return Текст, отформатированный для Telegram MarkdownV2.
//     */
//    public String convertToV2(String standardMarkdown) {
//        if (standardMarkdown == null || standardMarkdown.isEmpty()) {
//            return "";
//        }
//
//        // Шаг 1: Парсим в AST
//        Node document = parser.parse(standardMarkdown);
//
//        // Шаг 2: Рендерим с помощью нашего кастомного V2 рендерера
//        StringBuilder sb = new StringBuilder();
//        TelegramV2Renderer renderer = new TelegramV2Renderer(sb);
//        renderer.render(document);
//
//        // Шаг 3: Возвращаем результат
//        return sb.toString().trim(); // Убираем лишние пробелы в конце
//    }
//
//    // --- Пример использования ---
//    public static void main(String[] args) {
//        String input = """
//                ## Привет, Мир!
//
//                Это **жирный** текст с *вложенным курсивом*.
//                А это просто *курсив*.
//
//                > Это цитата. Она будет экранирована.
//
//                Список покупок:
//                * Молоко (важно!)
//                * Яйца
//
//                1. Первый пункт.
//                2. Второй (с `кодом`).
//
//                Ссылка на [Google](https://google.com).
//
//                И немного кода (java):
//                ```java
//                public class Test {
//                    // Комментарий с `символом`
//                    String s = "Hello!";
//                }
//                ```
//                Конец.
//                """;
//
//        MarkdownV2Converter converter = new MarkdownV2Converter();
//        String output = converter.convertToV2(input);
//
//        System.out.println("--- ИСХОДНЫЙ MARKDOWN ---");
//        System.out.println(input);
//        System.out.println("--- TELEGRAM MARKDOWNV2 ---");
//        System.out.println(output);
//    }
}
