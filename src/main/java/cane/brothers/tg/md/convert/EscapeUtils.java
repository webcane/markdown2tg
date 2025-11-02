package cane.brothers.tg.md.convert;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.LookupTranslator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for escaping special characters in Telegram MarkdownV2.
 * <a href="https://core.telegram.org/bots/api#markdownv2-style">markdownv2-style</a>
 */
final class EscapeUtils {

    private static final Map<CharSequence, CharSequence> TG_MD_V2_FULL_ESCAPE_MAP = new HashMap<>() {
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
    static final AggregateTranslator FULL_ESCAPE = new AggregateTranslator(
            new LookupTranslator(Collections.unmodifiableMap(TG_MD_V2_FULL_ESCAPE_MAP))
    );

    private static final Map<CharSequence, CharSequence> TG_MD_V2_CODE_ESCAPE_MAP = new HashMap<>() {
        {
            put("\\", "\\\\");
            put("`", "\\`");
        }
    };
    static final AggregateTranslator CODE_ESCAPE = new AggregateTranslator(
            new LookupTranslator(Collections.unmodifiableMap(TG_MD_V2_CODE_ESCAPE_MAP))
    );

    private static final Map<CharSequence, CharSequence> TG_MD_V2_LINK_ESCAPE_MAP = new HashMap<>() {
        {
            put("\\", "\\\\");
            put(")", "\\)");
        }
    };
    static final AggregateTranslator LINK_ESCAPE = new AggregateTranslator(
            new LookupTranslator(Collections.unmodifiableMap(TG_MD_V2_LINK_ESCAPE_MAP))
    );

    private EscapeUtils() {
    }

    /**
     * Escape special characters using the provided translator
     *
     * @param input      the input string to escape
     * @param translator the translator defining escape rules
     * @return the escaped string
     */
    public static String escape(String input, AggregateTranslator translator) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return StringEscapeUtils.builder(translator).escape(input).toString();
    }

    /**
     * Escape special characters for Telegram MarkdownV2
     *
     * @param input the input string to escape
     * @return the escaped string
     */
    public static String escape(String input) {
        return escape(input, FULL_ESCAPE);
    }
}
