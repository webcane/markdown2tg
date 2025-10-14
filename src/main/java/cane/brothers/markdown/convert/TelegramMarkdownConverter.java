package cane.brothers.markdown.convert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converter from Markdown to Telegram MarkdownV2
 */
public class TelegramMarkdownConverter {

    private final InlineProcessor inlineProcessor;
    private final BlockProcessor blockProcessor;

    private static final Pattern FENCE_START = Pattern.compile("^```(.*)$");
    private static final Pattern FENCE_END = Pattern.compile("^```\\s*$");


    public TelegramMarkdownConverter() {
        this(ConversionOptions.builder().build());
    }
    
    public TelegramMarkdownConverter(ConversionOptions options) {
        this.inlineProcessor = new DefaultInlineProcessor();
        this.blockProcessor = new DefaultBlockProcessor(options, this.inlineProcessor);
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
                    outLines.addAll(fenceBuffer);
                    outLines.add("```");
                    inFence = false;
                    fenceBuffer.clear();
                } else {
                    fenceBuffer.add(line);
                }
                continue;
            }

            // Block-level processing using block processor
            var result = blockProcessor.process(line);
            if (result.isConverted()) {
                outLines.add(result.getValue());
                continue;
            }

            if (line.isEmpty()) {
                outLines.add("");
                continue;
            }

            // Inline-level processing for lines not handled by block processor
            outLines.add(inlineProcessor.process(line));
        }

        return String.join("\n", outLines);
    }



}
