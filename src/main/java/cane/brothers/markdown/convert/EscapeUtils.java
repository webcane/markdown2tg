package cane.brothers.markdown.convert;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.LookupTranslator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class EscapeUtils {

    static final Map<CharSequence, CharSequence> ESCAPE_MAP = new HashMap<>() {
        {
            put("\\", "\\\\");
            put("_", "\\_");
            put("*", "\\*");
            put("[", "\\[");
            put("]", "\\]");
            put("(", "\\(");
            put(")", "\\)");
            put("~", "\\~");
            put("`", "\\`");
            put(">", "\\>");
            put("<", "\\<");
            put("#", "\\#");
            put("+", "\\+");
            put("-", "\\-");
            put("=", "\\=");
            put("|", "\\|");
            put("{", "\\{");
            put("}", "\\}");
            put(".", "\\.");
            put("!", "\\!");
        }
    };
    static final AggregateTranslator ESCAPE = new AggregateTranslator(
            new LookupTranslator(Collections.unmodifiableMap(ESCAPE_MAP))
    );

    private EscapeUtils() {
    }

    /**
     * Escape special characters for Telegram MarkdownV2
     *
     * @param input the input string to escape
     * @return the escaped string
     */
    public static String escape(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return StringEscapeUtils.builder(ESCAPE).escape(input).toString();
    }

    public static String escapeUrlForTelegram(String url) {
        if (url == null)
            return null;
        return url.replace(" ", "%20").replace("(", "%28").replace(")", "%29");
    }

//    public static String unescapeHtmlToText(String html) {
//        if (html == null) return null;
//        return StringEscapeUtils.unescapeHtml4(html);
//    }
}
