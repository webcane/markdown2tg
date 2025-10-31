package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Block handler for horizontal rules
 */
class HorizontalRuleBlockHandler extends AbstractBlockHandler {
    private static final Pattern HR_PATTERN = Pattern.compile("^(-{3,}|_{3,}|\\*{3,})\\s*$");


    public HorizontalRuleBlockHandler() {
    }

    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return HR_PATTERN.matcher(lineValue).matches();
    }
    
    @Override
    protected ConversionResult<String> handle(ConversionResult<String> line) {
        // Horizontal rules are converted to em dash sequence
        return ConversionResult.success("———");
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
