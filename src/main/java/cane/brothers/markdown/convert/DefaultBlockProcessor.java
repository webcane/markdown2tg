package cane.brothers.markdown.convert;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Default implementation of BlockProcessor that processes markdown blocks
 * using a chain of responsibility pattern with various BlockHandlers.
 */
class DefaultBlockProcessor implements BlockProcessor {

    private final HandlerVisitor handlerVisitor;

    DefaultBlockProcessor(ConversionOptions options, InlineProcessor inlineProcessor) {
        // Add QuotingBlockHandler first for highest priority (fenced blocks)
        Queue<AbstractBlockHandler> blockHandlers = new LinkedList<>();
        blockHandlers.add(new QuotingBlockHandler());
        blockHandlers.add(new HorizontalRuleBlockHandler());
        blockHandlers.add(new HeadingBlockHandler(createHeadingStrategy(options)));
        blockHandlers.add(new BlockquoteBlockHandler(inlineProcessor));
        blockHandlers.add(new OrderedListBlockHandler(inlineProcessor));
        blockHandlers.add(new UnorderedListBlockHandler(inlineProcessor));
        handlerVisitor = new BlockHandlerVisitor(blockHandlers);
    }

    private HeadingStrategy createHeadingStrategy(ConversionOptions options) {
        return switch (options.getHeadingStrategyType()) {
            case BOLD_LINE -> new BoldHeadingStrategy();
            case UNDERLINE -> new UnderlineHeadingStrategy();
            case NONE -> new PlainHeadingStrategy();
        };
    }

    @Override
    public ConversionResult<String> process(ConversionResult<String> line) {
        ConversionResult<String> result = ConversionResult.failure();

        for (AbstractBlockHandler handler : handlerVisitor.getHandlers()) {
            result = handler.apply(line);
        }

        return result;
    }
}
