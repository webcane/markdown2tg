package cane.brothers.markdown.convert;

import cane.brothers.markdown.EscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Inline handler for images
 */
class ImageInlineMarkdownHandler implements InlineMarkdownHandler {

    // Pattern to match images ![alt](url)
    private static final Pattern IMAGE_PATTERN = Pattern.compile("!\\[(.*?)\\]\\((.*?)\\)");
    
    @Override
    public boolean canHandle(String line) {
        return IMAGE_PATTERN.matcher(line).find();
    }
    
    @Override
    public ConversionResult<String> process(String line) {
        Matcher matcher = IMAGE_PATTERN.matcher(line);
        StringBuilder sb = new StringBuilder();
        
        while (matcher.find()) {
            String alt = matcher.group(1);
            String url = matcher.group(2);
            String safeAlt = EscapeUtils.escape(alt);
            String safeUrl = EscapeUtils.escapeUrlForTelegram(url);
            String replacement = "[" + safeAlt + "](" + safeUrl + ")";
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        
        return ConversionResult.success(sb.toString());
    }
    
    @Override
    public String getName() {
        return "image-inline";
    }
}
