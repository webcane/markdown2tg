package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Inline handler for spoiler text
 */
class SpoilerInlineMarkdownHandler implements InlineMarkdownHandler {

    // Pattern to match ||text||
    private static final Pattern SPOILER_PATTERN = Pattern.compile("\\|\\|(.+?)\\|\\|");
    
    @Override
    public boolean canHandle(String line) {
        return SPOILER_PATTERN.matcher(line).find();
    }
    
    @Override
    public ConversionResult<String> process(String line) {
        // Spoiler format remains unchanged in Telegram MarkdownV2
        return ConversionResult.success(line);
    }
    
    @Override
    public String getName() {
        return "spoiler-inline";
    }
}
