package cane.brothers.markdown.convert;

/**
 * Interface for block-level markdown handlers (headings, lists, blockquotes, etc.)
 */
public interface BlockHandler extends MarkdownHandler {

    /**
     * Get the priority of this handler (higher number = higher priority)
     * @return priority value
     */
    int getPriority();
}
