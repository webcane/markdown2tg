package cane.brothers.markdown.convert;

interface HandlerVisitor {

    void visit(AbstractBlockHandler handler);

    Iterable<? extends AbstractBlockHandler> getHandlers();
}
