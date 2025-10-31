package cane.brothers.markdown.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Block handler for ordered lists
 */
class OrderedListBlockHandler extends AbstractBlockHandler {

    // Pattern to match ordered list items (e.g., "1. Item", "2. Item", etc.)
    private static final Pattern ORDERED_ITEM_PATTERN = Pattern.compile("^\\s*(\\d+)\\.\\s+(.*)$");

    private final InlineProcessor inlineProcessor;

    public OrderedListBlockHandler(InlineProcessor inlineProcessor) {
        this.inlineProcessor = inlineProcessor;
    }

    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return ORDERED_ITEM_PATTERN.matcher(lineValue).matches();
    }
    
    @Override
    protected ConversionResult<String> handle(ConversionResult<String> line) {
        Matcher matcher = ORDERED_ITEM_PATTERN.matcher(line.getValue());
        if (matcher.matches()) {
            String number = matcher.group(1);
            String text = matcher.group(2);
//            String processedText = inlineProcessor.process(text);
            return ConversionResult.success(number + ". " + text);
        }
        return ConversionResult.failure();
    }
    
    @Override
    public int getPriority() {
        return 85; // Medium-high priority
    }
    
    @Override
    public String getName() {
        return "ordered-list";
    }
}
