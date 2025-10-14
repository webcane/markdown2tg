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
    public boolean canHandle(String line) {
        return LINK_PATTERN.matcher(line).find();
    }
    
    @Override
    public ConversionResult<String> process(String line) {
        Matcher matcher = LINK_PATTERN.matcher(line);
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
