package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Inline handler for italic text
 */
public class ItalicInlineMarkdownHandler implements InlineMarkdownHandler {

    // Pattern to match *text* or _text_ but not **text** or __text__
    private static final Pattern SINGLE_ASTERISK_PATTERN = Pattern.compile("(?<!\\*)\\*([^*]+?)\\*(?!\\*)");
    // Negative lookbehind and lookahead to avoid matching **text**
    private static final Pattern SINGLE_UNDERSCORE_PATTERN = Pattern.compile("(?<!_)_([^_]+?)_(?!_)");

    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        return SINGLE_ASTERISK_PATTERN.matcher(lineValue).find() ||
                SINGLE_UNDERSCORE_PATTERN.matcher(lineValue).find();
    }

    @Override
    public ConversionResult<String> apply(ConversionResult<String> line) {
        String result = line.getValue();

        // Convert *text* to _text_
        result = SINGLE_ASTERISK_PATTERN.matcher(result).replaceAll(matchResult -> {
            String content = matchResult.group(1);
            return "_" + content + "_";
        });

        // Keep _text_ as _text_ (single underscores remain unchanged)
        // This is handled by the pattern matching

        return ConversionResult.success(result);
    }

    @Override
    public String getName() {
        return "italic-inline";
    }
}
