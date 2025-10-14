package cane.brothers.markdown.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Block handler for unordered lists
 */
class UnorderedListBlockHandler implements BlockHandler {

    // Pattern to match unordered list items (e.g., "* Item", "- Item", "+ Item")
    private static final Pattern UNORDERED_ITEM_PATTERN = Pattern.compile("^\\s*([*+-])\\s+(.*)$");

    private final InlineProcessor inlineProcessor;

    public UnorderedListBlockHandler(InlineProcessor inlineProcessor) {
        this.inlineProcessor = inlineProcessor;
    }

    @Override
    public boolean canHandle(String line) {
        return UNORDERED_ITEM_PATTERN.matcher(line).matches();
    }
    
    @Override
    public ConversionResult<String> process(String line) {
        Matcher matcher = UNORDERED_ITEM_PATTERN.matcher(line);
        if (matcher.matches()) {
            String text = matcher.group(2);
            String processedText = inlineProcessor.process(text);
            return ConversionResult.success("- " + processedText);
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
