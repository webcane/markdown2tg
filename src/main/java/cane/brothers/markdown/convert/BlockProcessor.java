package cane.brothers.markdown.convert;

/**
 * Processor for block-level markdown elements (headings, lists, code blocks, etc.)
 * Has priority to determine processing order.
 */
public interface BlockProcessor {

    /**
     * Process a line of text to convert block-level markdown elements
     *
     * @param line the input line of text
     * @return the conversion result, containing the converted value if successful
     */
    ConversionResult<String> process(ConversionResult<String> line);
}
