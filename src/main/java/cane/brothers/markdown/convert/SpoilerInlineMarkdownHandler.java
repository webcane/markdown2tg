package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Inline handler for spoiler text
 */
class SpoilerInlineMarkdownHandler implements InlineMarkdownHandler {

    // Pattern to match ||text||
    private static final Pattern SPOILER_PATTERN = Pattern.compile("\\|\\|(.+?)\\|\\|");
    
    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return SPOILER_PATTERN.matcher(lineValue).find();
    }
    
    @Override
    public ConversionResult<String> apply(ConversionResult<String> line) {
        var lineValue = line.getValue();
        // Spoiler format remains unchanged in Telegram MarkdownV2
        return ConversionResult.success(lineValue);
    }
    
    @Override
    public String getName() {
        return "spoiler-inline";
    }
}
