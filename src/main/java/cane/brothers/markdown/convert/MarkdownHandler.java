package cane.brothers.markdown.convert;

/**
 * Base interface for all markdown handlers
 */
public interface MarkdownHandler {

    /**
     * Check if this handler can process the given line
     *
     * @param line the input line
     * @return true if this handler can process the line
     */
    boolean canHandle(String line);

    /**
     * Process the line and return the result
     *
     * @param line the input line
     * @return conversion result
     */
    ConversionResult<String> process(String line);

    /**
     * Get the name of this handler
     *
     * @return handler name
     */
    String getName();
}
