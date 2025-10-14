package cane.brothers.markdown.convert;

import cane.brothers.markdown.EscapeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of InlineProcessor
 */
public class DefaultInlineProcessor implements InlineProcessor {

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
    public String process(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        String result = text;
        boolean changed = true;
        int iterations = 0;
        final int MAX_ITERATIONS = 10; // Protection against infinite recursion
        
        while (changed && iterations < MAX_ITERATIONS) {
            String before = result;

            // Apply each inline handler in sequence
            for (InlineMarkdownHandler handler : inlineHandlers) {
                if (handler.canHandle(result)) {
                    var conversionResult = handler.process(result);
                    if (conversionResult.isConverted()) {
                        result = conversionResult.getValue();
                    }
                }
            }
            
            changed = !result.equals(before);
            iterations++;
        }
        
        // Final escaping
        return EscapeUtils.escape(result);
    }
}
