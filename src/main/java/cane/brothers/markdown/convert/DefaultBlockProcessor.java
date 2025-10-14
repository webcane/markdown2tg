package cane.brothers.markdown.convert;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Default implementation of BlockProcessor that processes markdown blocks
 * using a chain of responsibility pattern with various BlockHandlers.
 */
class DefaultBlockProcessor implements BlockProcessor {

    private final Queue<BlockHandler> blockHandlers = new LinkedList<>();

    DefaultBlockProcessor(ConversionOptions options, InlineProcessor inlineProcessor) {
        // Add QuotingBlockHandler first for highest priority (fenced blocks)
        blockHandlers.add(new QuotingBlockHandler());
        blockHandlers.add(new HorizontalRuleBlockHandler());
        blockHandlers.add(new HeadingBlockHandler(inlineProcessor, createHeadingStrategy(options)));
        blockHandlers.add(new BlockquoteBlockHandler(inlineProcessor));
        blockHandlers.add(new OrderedListBlockHandler(inlineProcessor));
        blockHandlers.add(new UnorderedListBlockHandler(inlineProcessor));
    }

    private HeadingStrategy createHeadingStrategy(ConversionOptions options) {
        return switch (options.getHeadingStrategyType()) {
            case BOLD_LINE -> new BoldHeadingStrategy();
            case UNDERLINE -> new UnderlineHeadingStrategy();
            case NONE -> new PlainHeadingStrategy();
        };
    }

    public ConversionResult<String> process(String line) {
        for (BlockHandler handler : blockHandlers) {
            if (handler.canHandle(line)) {
                ConversionResult<String> result = handler.process(line);
                if (result.isConverted()) {
                    return result;
                }
            }
        }
        return ConversionResult.failure();
    }
}
