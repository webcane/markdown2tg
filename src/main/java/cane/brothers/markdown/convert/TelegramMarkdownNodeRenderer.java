package cane.brothers.markdown.convert;

import org.commonmark.node.*;
import org.commonmark.renderer.NodeRenderer;

import java.util.*;

/**
 * Node renderer converting commonmark AST nodes to Telegram MarkdownV2.
 * <p>
 * What is supported:
 * - Headings (as bold text)
 * - Paragraphs
 * - Emphasis (italic)
 * - Strong Emphasis (bold)
 * - Inline code
 * - Fenced and indented code blocks
 * - Links
 * - Images (as links)
 * - Bullet and ordered lists (as bullet points)
 * - Block quotes
 * - Hard and soft line breaks
 * - Thematic breaks
 * <p>
 * What is not supported:
 * - Tables
 * - Footnotes
 * - Strikethrough
 * - Task lists
 * - __underline__
 * - ~strikethrough~
 * - multiline block quotation
 * - expandable block quotation
 * - spoiler
 * - inline mention of a user
 * - emojis
 */
class TelegramMarkdownNodeRenderer extends AbstractVisitor implements NodeRenderer {

    private final StringBuilder output;

    private final Deque<NodeContext> contextStack = new ArrayDeque<>();

    private boolean inCodeBlock = false;
    private boolean inCode = false;

    /**
     * Default constructor
     */
    TelegramMarkdownNodeRenderer() {
        this.output = new StringBuilder();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Arrays.asList(
                Document.class,
                Heading.class,
                Paragraph.class,
                Text.class,
                Emphasis.class,
                StrongEmphasis.class,
                Code.class,
                FencedCodeBlock.class,
                IndentedCodeBlock.class,
                Link.class,
                Image.class,
                BulletList.class,
                OrderedList.class,
                ListItem.class,
                BlockQuote.class,
                HardLineBreak.class,
                SoftLineBreak.class,
                ThematicBreak.class
        ));
    }

    @Override
    public void render(Node node) {
        node.accept(this);
    }

    @Override
    public void visit(Document document) {
        visitChildren(document);
        // final cleanup
        trimTrailingNewlines();
    }

    @Override
    public void visit(Heading heading) {
        // Telegram MarkdownV2 does not support headings,
        // using bold instead
        contextStack.push(new NodeContext(NodeType.HEADING, heading.getLevel()));

        output.append("*");
        visitChildren(heading);
        output.append("*");

        contextStack.pop();
        appendBlockSeparator();
    }

    @Override
    public void visit(Paragraph paragraph) {
        contextStack.push(new NodeContext(NodeType.PARAGRAPH));
        visitChildren(paragraph);
        contextStack.pop();
        appendBlockSeparator();
    }

    @Override
    public void visit(Text text) {
        String content = text.getLiteral();

        // escape only if not inside code/code block
        if (!inCode && !inCodeBlock) {
            content = EscapeUtils.escape(content);
        }

        output.append(content);
    }

    @Override
    public void visit(Emphasis emphasis) {
        // Markdown: *text* or _text_
        // Telegram MarkdownV2: _text_
        contextStack.push(new NodeContext(NodeType.EMPHASIS)); // ITALIC

        output.append("_");
        visitChildren(emphasis);
        output.append("_");

        contextStack.pop();
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        // Markdown: **text** or __text__
        // Telegram MarkdownV2: *text*
        contextStack.push(new NodeContext(NodeType.STRONG)); // BOLD

        output.append("*");
        visitChildren(strongEmphasis);
        output.append("*");

        contextStack.pop();
    }

    @Override
    public void visit(Code code) {
        // Inline code: `text`
        inCode = true;
        contextStack.push(new NodeContext(NodeType.CODE));

        output.append("`");
        // escape code content
        output.append(EscapeUtils.escape(code.getLiteral(), EscapeUtils.CODE_ESCAPE));
        output.append("`");

        contextStack.pop();
        inCode = false;
    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        // Fenced code block
        // Markdown: ```lang\ncode\n````
        // Telegram: ```lang\ncode\n``` or ```\ncode\n```
        inCodeBlock = true;
        contextStack.push(new NodeContext(NodeType.CODE_BLOCK));
        output.append("```");

        String info = fencedCodeBlock.getInfo();
        if (info != null && !info.isEmpty()) {
            // optional language info
            output.append(info);
        }

        output.append("\n");
        String literal = fencedCodeBlock.getLiteral();
        if (literal != null) {
            output.append(literal);
            // add newline if not present at the end
            if (!literal.endsWith("\n")) {
                output.append("\n");
            }
        }

        output.append("```");
        contextStack.pop();
        inCodeBlock = false;
        appendBlockSeparator();
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        // Code block with 4-space indentation will be converted to fenced code block
        inCodeBlock = true;
        contextStack.push(new NodeContext(NodeType.CODE_BLOCK));
        output.append("```\n");

        String literal = indentedCodeBlock.getLiteral();
        if (literal != null) {
            output.append(literal);
        }

        output.append("```");
        contextStack.pop();
        inCodeBlock = false;
        appendBlockSeparator();
    }

    @Override
    public void visit(Link link) {
        // Markdown and Telegram MarkdownV2: [text](url)
        contextStack.push(new NodeContext(NodeType.LINK));

        output.append("[");
        // Link text might contain formatting
        visitChildren(link);
        output.append("](");

        String destination = link.getDestination();
        if (destination != null) {
            // escape inline link
            output.append(EscapeUtils.escape(destination, EscapeUtils.LINK_ESCAPE));
        }

        output.append(")");
        contextStack.pop();
    }

    @Override
    public void visit(Image image) {
        // Telegram MarkdownV2 does not support images directly
        // Using link
        contextStack.push(new NodeContext(NodeType.IMAGE));

        output.append("[");

        // use image alt text as link text or title
        if (image.getFirstChild() != null) {
            visitChildren(image);
        } else if (image.getTitle() != null && !image.getTitle().isEmpty()) {
            output.append(EscapeUtils.escape(image.getTitle()));
        } else {
            output.append("Image");
        }

        output.append("](");

        String destination = image.getDestination();
        if (destination != null) {
            // escape inline link
            output.append(EscapeUtils.escape(destination, EscapeUtils.LINK_ESCAPE));
        }

        output.append(")");
        contextStack.pop();
    }

    @Override
    public void visit(BulletList bulletList) {
        contextStack.push(new NodeContext(NodeType.BULLET_LIST));
        visitChildren(bulletList);
        contextStack.pop();
        appendBlockSeparator();
    }

    @Override
    public void visit(OrderedList orderedList) {
        contextStack.push(new NodeContext(NodeType.ORDERED_LIST));
        visitChildren(orderedList);
        contextStack.pop();
        appendBlockSeparator();
    }

    @Override
    public void visit(ListItem listItem) {
        // Telegram MarkdownV2 does not support lists directly
        // Using bullet points
        contextStack.push(new NodeContext(NodeType.LIST_ITEM));

        // determine type of parent list
        Node parent = listItem.getParent();
        if (parent instanceof OrderedList) {
            // can use numbers, but Telegram supports them badly
            output.append("• ");
        } else {
            output.append("- ");
        }

        visitChildren(listItem);

        // remove trailing newlines before adding newline
        trimTrailingNewlines();
        output.append("\n");

        contextStack.pop();
    }

    @Override
    public void visit(BlockQuote blockQuote) {
        contextStack.push(new NodeContext(NodeType.BLOCK_QUOTE));

        output.append(">");
        visitChildren(blockQuote);

        // remove trailing newlines
        trimTrailingNewlines();

        contextStack.pop();
        appendBlockSeparator();
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        output.append("\n");
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        // Soft line break in Markdown -> whitespace
        output.append(" ");
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        // Horizontal rule: --- or ***
        output.append("---");
        appendBlockSeparator();
    }

    /**
     * Append block separator (double newline)
     */
    private void appendBlockSeparator() {
        // append double newline if last was not block
        output.append("\n\n");
    }

    /**
     * Remove trailing newlines
     */
    private void trimTrailingNewlines() {
        while (!output.isEmpty() && output.charAt(output.length() - 1) == '\n') {
            output.setLength(output.length() - 1);
        }
    }

    /**
     * Get convertion result
     *
     * @return the converted Telegram MarkdownV2 text
     */
    protected String getResult() {
        String result = output.toString();
        return result.replaceAll("\n{3,}", "\n\n");
    }

    /**
     * Types of nodes we handle
     */
    private enum NodeType {
        HEADING, PARAGRAPH,
        EMPHASIS, STRONG, CODE, CODE_BLOCK,
        LINK, IMAGE, BULLET_LIST, ORDERED_LIST, LIST_ITEM,
        BLOCK_QUOTE
    }

    /**
     * Context of the current node for tracking nesting
     *
     * @param level Для heading
     */
    private record NodeContext(NodeType type, int level) {

        NodeContext(NodeType type) {
            this(type, 0);
        }
    }
}
