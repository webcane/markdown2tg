package cane.brothers.markdown.convert;

/**
 * Processor for inline markdown elements (bold, italic, links, etc.)
 */
public interface InlineProcessor {

    /**
     * Process inline elements in the given text
     *
     * @param text the input text
     * @return processed text
     */
    String process(String text);
}
