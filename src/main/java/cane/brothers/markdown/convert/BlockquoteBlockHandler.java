package cane.brothers.markdown.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Block handler for blockquotes
 */
class BlockquoteBlockHandler implements BlockHandler {

    // Pattern to match blockquotes (e.g., "> Quote")
    private static final Pattern BLOCKQUOTE_PATTERN = Pattern.compile("^\\s*>(.*)$");

    private final InlineProcessor inlineProcessor;

    public BlockquoteBlockHandler(InlineProcessor inlineProcessor) {
        this.inlineProcessor = inlineProcessor;
    }

    @Override
    public boolean canHandle(String line) {
        return BLOCKQUOTE_PATTERN.matcher(line).matches();
    }
    
    @Override
    public ConversionResult<String> process(String line) {
        Matcher matcher = BLOCKQUOTE_PATTERN.matcher(line);
        if (matcher.matches()) {
            String text = matcher.group(1).trim();
            String processedText = inlineProcessor.process(text);
            return ConversionResult.success("> " + processedText);
        }
        return ConversionResult.failure();
    }
    
    @Override
    public int getPriority() {
        return 90; // High priority
    }
    
    @Override
    public String getName() {
        return "blockquote";
    }
}
