package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Inline handler for strikethrough text
 */
class StrikethroughInlineMarkdownHandler implements InlineMarkdownHandler {

    // Pattern to match ~~text~~
    private static final Pattern STRIKETHROUGH_PATTERN = Pattern.compile("~~(.+?)~~");
    
    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return STRIKETHROUGH_PATTERN.matcher(lineValue).find();
    }
    
    @Override
    public ConversionResult<String> apply(ConversionResult<String> line) {
        var lineValue = line.getValue();
        String result = STRIKETHROUGH_PATTERN.matcher(lineValue).replaceAll(matchResult -> {
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
