package cane.brothers.markdown.convert;

import java.util.function.Consumer;

/**
 * Abstract base class for block-level markdown handlers. (headings, lists, blockquotes, etc.)
 * Chains block handlers together.
 */
abstract class AbstractBlockHandler implements MarkdownHandler, Consumer<HandlerVisitor> {

    protected AbstractBlockHandler next;

    public void setNext(AbstractBlockHandler next) {
        this.next = next;
    }

    /**
     * Get the priority of this handler (higher number = higher priority)
     * @return priority value
     */
    abstract int getPriority();


    abstract ConversionResult<String> handle(ConversionResult<String> line);


    @Override
    public ConversionResult<String> apply(ConversionResult<String> line) {
        if (canHandle(line)) {
            return handle(line);
        } else if (next != null) {
            return next.apply(line);
        } else {
            return line; // No handler could process the line
        }
    }

    @Override
    public void accept(HandlerVisitor visitor) {
        visitor.visit(this);
    }

}
