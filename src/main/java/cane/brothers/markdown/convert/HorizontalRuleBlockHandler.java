package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Block handler for horizontal rules
 */
class HorizontalRuleBlockHandler implements BlockHandler {
    private static final Pattern HR_PATTERN = Pattern.compile("^(-{3,}|_{3,}|\\*{3,})\\s*$");


    public HorizontalRuleBlockHandler(InlineProcessor inlineProcessor) {
    }

    @Override
    public boolean canHandle(String line) {
        return HR_PATTERN.matcher(line).matches();
    }
    
    @Override
    public ConversionResult<String> process(String line) {
        // Horizontal rules are converted to em dash sequence
        return ConversionResult.success("\u2014\u2014\u2014");
    }
    
    @Override
    public int getPriority() {
        return 95; // High priority, but after code fences
    }
    
    @Override
    public String getName() {
        return "horizontal-rule";
    }
}
