package cane.brothers.markdown.convert;

import org.commonmark.node.Node;
import org.commonmark.renderer.Renderer;

class TelegramMarkdownRenderer implements Renderer {

    private TelegramMarkdownRenderer() {
    }

    static Builder builder() {
        return new Builder();
    }

    @Override
    public void render(Node node, Appendable output) {
        TelegramMarkdownNodeRenderer nodeRenderer = new TelegramMarkdownNodeRenderer();
        node.accept(nodeRenderer);
        if (output != null) {
            try {
                output.append(nodeRenderer.getResult());
            } catch (Exception e) {
                throw new RuntimeException("Error while rendering markdown", e);
            }
        }
    }

    @Override
    public String render(Node node) {
        StringBuilder sb = new StringBuilder();
        render(node, sb);
        return sb.toString();
    }

    static class Builder {

        public TelegramMarkdownRenderer build() {
            return new TelegramMarkdownRenderer();
        }
    }
}
