package cane.brothers.markdown.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Block handler for headings
 */
class HeadingBlockHandler extends AbstractBlockHandler {

    // Pattern to match markdown headings (e.g., "# Heading 1", "## Heading 2", etc.)
    private static final Pattern HEADER_PATTERN = Pattern.compile("^(#{1,6})\\s+(.*)$");
    private final HeadingStrategy headingStrategy;
//    private final InlineProcessor inlineProcessor;

    public HeadingBlockHandler(HeadingStrategy headingStrategy) {
//        this.inlineProcessor = inlineProcessor;
        this.headingStrategy = headingStrategy;
    }

    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return HEADER_PATTERN.matcher(lineValue).matches();
    }

    @Override
    protected ConversionResult<String> handle(ConversionResult<String> line) {
        Matcher matcher = HEADER_PATTERN.matcher(line.getValue());
        if (matcher.matches()) {
            String text = matcher.group(2).trim();
//            String processedText = inlineProcessor.process(text);
            return ConversionResult.success(headingStrategy.format(text));
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
