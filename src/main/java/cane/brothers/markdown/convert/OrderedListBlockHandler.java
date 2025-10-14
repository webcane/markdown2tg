package cane.brothers.markdown.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Block handler for ordered lists
 */
class OrderedListBlockHandler implements BlockHandler {

    // Pattern to match ordered list items (e.g., "1. Item", "2. Item", etc.)
    private static final Pattern ORDERED_ITEM_PATTERN = Pattern.compile("^\\s*(\\d+)\\.\\s+(.*)$");

    private final InlineProcessor inlineProcessor;

    public OrderedListBlockHandler(InlineProcessor inlineProcessor) {
        this.inlineProcessor = inlineProcessor;
    }

    @Override
    public boolean canHandle(String line) {
        return ORDERED_ITEM_PATTERN.matcher(line).matches();
    }
    
    @Override
    public ConversionResult<String> process(String line) {
        Matcher matcher = ORDERED_ITEM_PATTERN.matcher(line);
        if (matcher.matches()) {
            String number = matcher.group(1);
            String text = matcher.group(2);
            String processedText = inlineProcessor.process(text);
            return ConversionResult.success(number + ". " + processedText);
        }
        return ConversionResult.failure();
    }
    
    @Override
    public int getPriority() {
        return 80; // Medium-high priority
    }
    
    @Override
    public String getName() {
        return "ordered-list";
    }
}
