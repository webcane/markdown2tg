package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Inline handler for strikethrough text
 */
class StrikethroughInlineMarkdownHandler implements InlineMarkdownHandler {

    // Pattern to match ~~text~~
    private static final Pattern STRIKETHROUGH_PATTERN = Pattern.compile("~~(.+?)~~");
    
    @Override
    public boolean canHandle(String line) {
        return STRIKETHROUGH_PATTERN.matcher(line).find();
    }
    
    @Override
    public ConversionResult<String> process(String line) {
        String result = STRIKETHROUGH_PATTERN.matcher(line).replaceAll(matchResult -> {
            String content = matchResult.group(1);
            return "~" + content + "~";
        });
        
        return ConversionResult.success(result);
    }
    
    @Override
    public String getName() {
        return "strikethrough-inline";
    }
}
