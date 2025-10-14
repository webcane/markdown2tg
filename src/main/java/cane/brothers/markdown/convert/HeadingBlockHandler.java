package cane.brothers.markdown.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Block handler for headings
 */
class HeadingBlockHandler implements BlockHandler {

    // Pattern to match markdown headings (e.g., "# Heading 1", "## Heading 2", etc.)
    private static final Pattern HEADER_PATTERN = Pattern.compile("^(#{1,6})\\s+(.*)$");
    private final HeadingStrategy headingStrategy;
    private final InlineProcessor inlineProcessor;

    public HeadingBlockHandler(InlineProcessor inlineProcessor, HeadingStrategy headingStrategy) {
        this.inlineProcessor = inlineProcessor;
        this.headingStrategy = headingStrategy;
    }
    
    @Override
    public boolean canHandle(String line) {
        return HEADER_PATTERN.matcher(line).matches();
    }
    
    @Override
    public ConversionResult<String> process(String line) {
        Matcher matcher = HEADER_PATTERN.matcher(line);
        if (matcher.matches()) {
            String text = matcher.group(2).trim();
            String processedText = inlineProcessor.process(text);
            return ConversionResult.success(headingStrategy.format(processedText));
        }
        return ConversionResult.failure();
    }
    
    @Override
    public int getPriority() {
        return 100; // High priority for headings
    }
    
    @Override
    public String getName() {
        return "heading";
    }
}
