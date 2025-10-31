package cane.brothers.markdown.convert;

import java.util.function.Function;

/**
 * Base interface for all markdown handlers
 */
public interface MarkdownHandler extends Function<ConversionResult<String>, ConversionResult<String>> {

    /**
     * Check if this handler can process the given line
     *
     * @param line the input line
     * @return true if this handler can process the line
     */
    boolean canHandle(ConversionResult<String> line);

    /**
     * Get the name of this handler
     *
     * @return handler name
     */
    String getName();

}
