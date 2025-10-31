package cane.brothers.markdown.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of InlineProcessor
 */
class DefaultInlineProcessor implements InlineProcessor {

    private final List<InlineMarkdownHandler> inlineHandlers = new ArrayList<>();

    public DefaultInlineProcessor() {
        // Add inline handlers in order of processing
        inlineHandlers.add(new BoldInlineMarkdownHandler());
        inlineHandlers.add(new ItalicInlineMarkdownHandler());
        inlineHandlers.add(new StrikethroughInlineMarkdownHandler());
        inlineHandlers.add(new SpoilerInlineMarkdownHandler());
        inlineHandlers.add(new LinkInlineMarkdownHandler());
        inlineHandlers.add(new ImageInlineMarkdownHandler());
        inlineHandlers.add(new CodeInlineMarkdownHandler());
    }

    @Override
    public ConversionResult<String> process(ConversionResult<String> text) {
        var lineValue = text.getValue();
        if (lineValue == null || lineValue.isEmpty()) {
            return text;
        }

        ConversionResult<String> result = text;
        boolean changed = true;
        int iterations = 0;
        final int MAX_ITERATIONS = 10; // Protection against infinite recursion

        while (changed && iterations < MAX_ITERATIONS) {
            ConversionResult<String> before = result;

            // Apply each inline handler in sequence
            for (InlineMarkdownHandler handler : inlineHandlers) {
                if (handler.canHandle(result)) {
                    var conversionResult = handler.apply(result);
                    if (conversionResult.isConverted().isPresent()) {
                        result = conversionResult;
                    }
                }
            }

            changed = !result.equals(before);
            iterations++;
        }

        // TODO Final escaping
//        return EscapeUtils.escape(result);
        return result;
    }
}
