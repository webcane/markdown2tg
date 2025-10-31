package cane.brothers.markdown.convert;

import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;

class BlockHandlerVisitor implements HandlerVisitor {

    private final NavigableSet<AbstractBlockHandler> handlers =
            new TreeSet<>(Comparator.comparingInt(AbstractBlockHandler::getPriority)
                    .reversed());

    public BlockHandlerVisitor(Collection<AbstractBlockHandler> handlers) {
        this.handlers.addAll(handlers);
        // link handlers
        for (AbstractBlockHandler handler : this.handlers) {
            handler.accept(this);
        }
    }

    @Override
    public void visit(AbstractBlockHandler handler) {
        var next = handlers.higher(handler);
        handler.setNext(next);
    }

    @Override
    public Iterable<? extends AbstractBlockHandler> getHandlers() {
        return handlers;
    }
}
