package cane.brothers.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converter from Markdown to Telegram MarkdownV2
 *
 * @param options
 */
public record TelegramMarkdownConverter(ConversionOptions options) {

    private static final Pattern ORDERED_ITEM = Pattern.compile("^\\s*(\\d+)\\.\\s+(.*)$");
    private static final Pattern UNORDERED_ITEM = Pattern.compile("^\\s*([*+-])\\s+(.*)$");
    private static final Pattern HR = Pattern.compile("^(-{3,}|_{3,}|\\*{3,})\\s*$");
    private static final Pattern FENCE_START = Pattern.compile("^```(.*)$");
    private static final Pattern FENCE_END = Pattern.compile("^```\\s*$");
    private static final Pattern BLOCKQUOTE = Pattern.compile("^>\\s?(.*)$");
    private static final Pattern IMAGE = Pattern.compile("!\\[(.*?)\\]\\((.*?)\\)");
    private static final Pattern LINK = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");
    private static final Pattern INLINE_CODE = Pattern.compile("`([^`]+)`");

    public TelegramMarkdownConverter() {
        this(ConversionOptions.builder().build());
    }

    /**
     * Convert Markdown to Telegram MarkdownV2
     *
     * @param markdown the input markdown text
     * @return the converted text in Telegram MarkdownV2 format
     */
    public String convert(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return "";
        }
        String normalized = markdown.replace("\r\n", "\n");

        List<String> outLines = new ArrayList<>();
        String[] lines = normalized.split("\n", -1);

        boolean inFence = false;
        List<String> fenceBuffer = new ArrayList<>();

        for (String line : lines) {
            Matcher startFence = FENCE_START.matcher(line);
            if (!inFence && startFence.matches()) {
                inFence = true;
                fenceBuffer.clear();
                continue;
            }
            if (inFence) {
                Matcher endFence = FENCE_END.matcher(line);
                if (endFence.matches()) {
                    outLines.add("```");
                    for (String codeLine : fenceBuffer) {
                        outLines.add(codeLine);
                    }
                    outLines.add("```");
                    inFence = false;
                    fenceBuffer.clear();
                } else {
                    fenceBuffer.add(line);
                }
                continue;
            }

            Matcher hr = HR.matcher(line);
            if (hr.matches()) {
                outLines.add("\u2014\u2014\u2014");
                continue;
            }

//            if (convertHeadingLine(line, outLines))
//                continue;
//            outLines.add(options.getHeadingHandling().apply(line));
            if (options.getHeadingHandling().isHandle()) {
                outLines.add(convertHeadingLine(line));
                continue;
            }

            Matcher bq = BLOCKQUOTE.matcher(line);
            if (bq.matches()) {
                String inner = processInline(bq.group(1));
                outLines.add("> " + inner);
                continue;
            }

            Matcher ord = ORDERED_ITEM.matcher(line);
            if (ord.matches()) {
                String num = ord.group(1);
                String text = ord.group(2);
                String inline = processInline(text);
                outLines.add(num + ". " + inline);
                continue;
            }

            Matcher unord = UNORDERED_ITEM.matcher(line);
            if (unord.matches()) {
                String text = unord.group(2);
                String inline = processInline(text);
                outLines.add("- " + inline);
                continue;
            }

            if (line.isEmpty()) {
                outLines.add("");
                continue;
            }

            outLines.add(processInline(line));
        }

        return String.join("\n", outLines);
    }

//    boolean convertHeadingLine(String line, List<String> outLines) {
//        return outLines.add(options.getHeadingHandling().apply(line));
//    }
    String convertHeadingLine(String line) {
        return options.getHeadingHandling().apply(line);
    }

    private String processInline(String text) {
        if (text == null || text.isEmpty()) return text;

        String t = text;

        t = replaceImages(t);
        t = replaceLinks(t);

        t = replaceEmphasis(t);

        t = replaceInlineCode(t);

        t = EscapeUtils.escape(t);
        return t;
    }

    private String replaceImages(String input) {
        Matcher m = IMAGE.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String alt = m.group(1);
            String url = m.group(2);
            String safeAlt = EscapeUtils.escape(alt);
            String safeUrl = EscapeUtils.escapeUrlForTelegram(url);
            String repl = "[" + safeAlt + "](" + safeUrl + ")";
            m.appendReplacement(sb, Matcher.quoteReplacement(repl));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String replaceLinks(String input) {
        Matcher m = LINK.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String label = m.group(1);
            String url = m.group(2);
            String safeLabel = EscapeUtils.escape(label);
            String safeUrl = EscapeUtils.escapeUrlForTelegram(url);
            String repl = "[" + safeLabel + "](" + safeUrl + ")";
            m.appendReplacement(sb, Matcher.quoteReplacement(repl));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String replaceInlineCode(String input) {
        Matcher m = INLINE_CODE.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String code = m.group(1);
            String safe = code.replace("`", "\\`");
            String repl = "`" + safe + "`";
            m.appendReplacement(sb, Matcher.quoteReplacement(repl));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String replaceEmphasis(String input) {
        String t = input;
        t = t.replaceAll("\\*\\*(.+?)\\*\\*", "*$1*");
        t = t.replaceAll("__(.+?)__", "*$1*");
        t = t.replaceAll("(?<!_)_(.+?)_(?!_)", "_$1_");
        t = t.replaceAll("\\*(.+?)\\*", "_$1_");
        t = t.replaceAll("~~(.+?)~~", "~$1~");
        t = t.replaceAll("\\|\\|(.+?)\\|\\|", "||$1||");
        return t;
    }
}
