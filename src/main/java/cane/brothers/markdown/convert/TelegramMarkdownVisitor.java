package cane.brothers.markdown.convert;

import org.commonmark.node.*;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Visit AST nodes to convert Markdown to Telegram MarkdownV2
 */
class TelegramMarkdownVisitor extends AbstractVisitor {

    private final StringBuilder result = new StringBuilder();
    private final Deque<FormatContext> formatStack = new ArrayDeque<>();
    private boolean inCodeBlock = false;
    private boolean inCode = false;

    // Символы, требующие экранирования в MarkdownV2 (вне форматирования)
//    private static final String ESCAPE_CHARS = "_*[]()~`>#+=|{}.!-";



    @Override
    public void visit(Heading heading) {
        // Telegram MarkdownV2 does not support headings, using bold instead
        formatStack.push(new FormatContext(FormatType.BOLD));
        result.append("*");
        visitChildren(heading);
        result.append("*");
        formatStack.pop();
        result.append("\n\n");
    }

    @Override
    public void visit(Paragraph paragraph) {
        visitChildren(paragraph);
        result.append("\n\n");
    }

    @Override
    public void visit(Text text) {
        String content = text.getLiteral();
        // escape only if not inside code/code block
        if (!inCode && !inCodeBlock) {
            content = EscapeUtils.escape(content);
        }

        result.append(content);
    }

    @Override
    public void visit(Emphasis emphasis) {
        // Italic: * -> _
        formatStack.push(new FormatContext(FormatType.ITALIC));
        result.append("_");
        visitChildren(emphasis);
        result.append("_");
        formatStack.pop();
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        // Bold: ** -> *
        formatStack.push(new FormatContext(FormatType.BOLD));
        result.append("*");
        visitChildren(strongEmphasis);
        result.append("*");
        formatStack.pop();
    }

    @Override
    public void visit(Code code) {
        // Inline code
        inCode = true;
        result.append("`");
        result.append(code.getLiteral());
        result.append("`");
        inCode = false;
    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        // Fenced code block
        inCodeBlock = true;
        result.append("```");

        String info = fencedCodeBlock.getInfo();
        if (info != null && !info.isEmpty()) {
            result.append(info);
        }
        result.append("\n");

        result.append(fencedCodeBlock.getLiteral());

        result.append("```\n\n");
        inCodeBlock = false;
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        // Code block with indentation
        inCodeBlock = true;
        result.append("```\n");
        result.append(indentedCodeBlock.getLiteral());
        result.append("```\n\n");
        inCodeBlock = false;
    }

    @Override
    public void visit(Link link) {
        // Telegram Markdown V2 links format: [text](url)
        result.append("[");

        // link text might contain formatting
        visitChildren(link);

        result.append("](");
        result.append(link.getDestination());
        result.append(")");
    }

    @Override
    public void visit(Image image) {
        // Telegram MarkdownV2 does not support images directly
        // Using link
        result.append("[");

        String title = image.getTitle();
        if (title != null && !title.isEmpty()) {
            result.append(EscapeUtils.escape(title));
        } else {
            visitChildren(image);
        }

        result.append("](");
        result.append(image.getDestination());
        result.append(")");
    }

    @Override
    public void visit(BulletList bulletList) {
        super.visit(bulletList);
        result.append("\n");
    }

    @Override
    public void visit(OrderedList orderedList) {
        super.visit(orderedList);
        result.append("\n");
    }

    @Override
    public void visit(ListItem listItem) {
        // Telegram MarkdownV2 does not support lists directly
        // Using bullet points
        result.append("• ");
        visitChildren(listItem);

        // remove trailing newlines before adding newline
        while (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
            result.setLength(result.length() - 1);
        }
        result.append("\n");
    }

    @Override
    public void visit(BlockQuote blockQuote) {
        result.append(">");
        visitChildren(blockQuote);
        // remove trailing newlines before adding >
        while (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
            result.setLength(result.length() - 1);
        }
        result.append("_\n\n");
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        result.append("\n");
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        result.append(" ");
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        result.append("---").append("\n\n");
    }

    /**
     * Get convertion result
     *
     * @return the converted Telegram MarkdownV2 text
     */
    public String getResult() {
        String text = result.toString();
        // remove trailing newlines at the end
        return text.replaceAll("\n+$", "");
    }

    private enum FormatType {
        BOLD, ITALIC, STRIKETHROUGH, CODE
    }


    /**
     * Format context to track nesting
     */
    private static class FormatContext {
        final FormatType type;

        FormatContext(FormatType type) {
            this.type = type;
        }
    }
}
