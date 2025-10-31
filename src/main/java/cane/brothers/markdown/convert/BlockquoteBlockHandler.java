package cane.brothers.markdown.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Block handler for blockquotes
 */
class BlockquoteBlockHandler extends AbstractBlockHandler {

    // Pattern to match blockquotes (e.g., "> Quote")
    private static final Pattern BLOCKQUOTE_PATTERN = Pattern.compile("^\\s*>(.*)$");

    private final InlineProcessor inlineProcessor;

    public BlockquoteBlockHandler(InlineProcessor inlineProcessor) {
        this.inlineProcessor = inlineProcessor;
    }

    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return BLOCKQUOTE_PATTERN.matcher(lineValue).matches();
    }
    
    @Override
    protected ConversionResult<String> handle(ConversionResult<String> line) {
        Matcher matcher = BLOCKQUOTE_PATTERN.matcher(line.getValue());
        if (matcher.matches()) {
            String text = matcher.group(1).trim();
//            String processedText = inlineProcessor.process(text);
            return ConversionResult.success("> " + text);
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
