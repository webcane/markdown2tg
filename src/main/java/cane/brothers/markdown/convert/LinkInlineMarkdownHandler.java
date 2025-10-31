package cane.brothers.markdown.convert;

import cane.brothers.markdown.EscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Inline handler for links
 */
class LinkInlineMarkdownHandler implements InlineMarkdownHandler {

    // Pattern to match links [label](url)
    private static final Pattern LINK_PATTERN = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");
    
    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return LINK_PATTERN.matcher(lineValue).find();
    }
    
    @Override
    public ConversionResult<String> apply(ConversionResult<String> line) {
        var lineValue = line.getValue();
        Matcher matcher = LINK_PATTERN.matcher(lineValue);
        StringBuilder sb = new StringBuilder();
        
        while (matcher.find()) {
            String label = matcher.group(1);
            String url = matcher.group(2);
            String safeLabel = EscapeUtils.escape(label);
            String safeUrl = EscapeUtils.escapeUrlForTelegram(url);
            String replacement = "[" + safeLabel + "](" + safeUrl + ")";
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        
        return ConversionResult.success(sb.toString());
    }
    
    @Override
    public String getName() {
        return "link-inline";
    }
}
