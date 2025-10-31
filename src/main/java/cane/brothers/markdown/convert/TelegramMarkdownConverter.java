package cane.brothers.markdown.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter from Markdown to Telegram MarkdownV2
 */
public class TelegramMarkdownConverter {

    private final InlineProcessor inlineProcessor;
    private final BlockProcessor blockProcessor;

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

        // Process each line
        for (String line : lines) {
            var result = new ConversionResult<String>(line);
            // Block-level processing using block processor
            result = blockProcessor.process(result);
            if (result.isConverted().isPresent()) {
                outLines.add(result.getValue());
                continue;
            }
            if (line.isEmpty()) {
                outLines.add("");
                continue;
            }
            // Inline-level processing for lines not handled by block processor
            result = inlineProcessor.process(result);
            outLines.add(result.getValue());
        }

        return String.join("\n", outLines);
    }

}
