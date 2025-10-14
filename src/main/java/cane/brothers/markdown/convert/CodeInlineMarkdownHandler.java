package cane.brothers.markdown.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Inline handler for inline code
 */
class CodeInlineMarkdownHandler implements InlineMarkdownHandler {

    // Pattern to match inline code (e.g., `code`)
    private static final Pattern INLINE_CODE_PATTERN = Pattern.compile("`([^`]+)`");

    @Override
    public boolean canHandle(String line) {
        return INLINE_CODE_PATTERN.matcher(line).find();
    }

    @Override
    public ConversionResult<String> process(String line) {
        Matcher matcher = INLINE_CODE_PATTERN.matcher(line);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String code = matcher.group(1);
            String safeCode = code.replace("`", "\\`");
            String replacement = "`" + safeCode + "`";
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);

        return ConversionResult.success(sb.toString());
    }

    @Override
    public String getName() {
        return "code-inline";
    }
}
