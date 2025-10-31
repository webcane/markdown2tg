package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Inline handler for bold text
 */
class BoldInlineMarkdownHandler implements InlineMarkdownHandler {

    // Patterns to match **text** and __text__
    private static final Pattern BOLD_PATTERN = Pattern.compile("\\*\\*([^*]+)\\*\\*");
    // Pattern to match __text__
    private static final Pattern UNDERSCORE_BOLD_PATTERN = Pattern.compile("__([^_]+)__");
    
    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return BOLD_PATTERN.matcher(lineValue).find() ||
               UNDERSCORE_BOLD_PATTERN.matcher(lineValue).find();
    }
    
    @Override
    public ConversionResult<String> apply(ConversionResult<String> line) {
        String result = line.getValue();
        
        // Convert **text** to *text*
        result = BOLD_PATTERN.matcher(result).replaceAll(matchResult -> {
            String content = matchResult.group(1);
            return "*" + content + "*";
        });
        
        // Convert __text__ to *text*
        result = UNDERSCORE_BOLD_PATTERN.matcher(result).replaceAll(matchResult -> {
            String content = matchResult.group(1);
            return "*" + content + "*";
        });
        
        return ConversionResult.success(result);
    }
    
    @Override
    public String getName() {
        return "bold-inline";
    }
}
