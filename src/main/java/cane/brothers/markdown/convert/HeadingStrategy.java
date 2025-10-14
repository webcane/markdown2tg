package cane.brothers.markdown.convert;

/**
 * Strategy interface for heading formatting
 */
public interface HeadingStrategy {
    /**
     * Format the heading text
     * @param text the heading text
     * @return formatted text
     */
    String format(String text);
}

/**
 * Bold heading strategy - converts to *text*
 */
class BoldHeadingStrategy implements HeadingStrategy {
    @Override
    public String format(String text) {
        return "*" + text + "*";
    }
}

/**
 * Plain heading strategy - no formatting
 */
class PlainHeadingStrategy implements HeadingStrategy {
    @Override
    public String format(String text) {
        return text;
    }
}

/**
 * Underline heading strategy - converts to _text_
 */
class UnderlineHeadingStrategy implements HeadingStrategy {
    @Override
    public String format(String text) {
        return "_" + text + "_";
    }
}
