package cane.brothers.markdown.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Block handler for unordered lists
 */
class UnorderedListBlockHandler extends AbstractBlockHandler {

    // Pattern to match unordered list items (e.g., "* Item", "- Item", "+ Item")
    private static final Pattern UNORDERED_ITEM_PATTERN = Pattern.compile("^\\s*([*+-])\\s+(.*)$");

    private final InlineProcessor inlineProcessor;

    public UnorderedListBlockHandler(InlineProcessor inlineProcessor) {
        this.inlineProcessor = inlineProcessor;
    }

    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return UNORDERED_ITEM_PATTERN.matcher(lineValue).matches();
    }
    
    @Override
    protected ConversionResult<String> handle(ConversionResult<String> line) {
        Matcher matcher = UNORDERED_ITEM_PATTERN.matcher(line.getValue());
        if (matcher.matches()) {
            String text = matcher.group(2);
//            String processedText = inlineProcessor.process(text);
            return ConversionResult.success("- " + text);
        }
        return ConversionResult.failure();
    }
    
    @Override
    public int getPriority() {
        return 80; // Medium-high priority
    }
    
    @Override
    public String getName() {
        return "unordered-list";
    }
}
