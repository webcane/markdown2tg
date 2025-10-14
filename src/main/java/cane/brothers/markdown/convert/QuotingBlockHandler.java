package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Block handler for quoting/fenced code blocks (one-line, stateless)
 */
class QuotingBlockHandler implements BlockHandler {

    // Pattern to match quoting/fence delimiters (e.g., "```")
    private static final Pattern FENCE_DELIMITER = Pattern.compile("^```.*$");
    private boolean inFence = false;

    @Override
    public boolean canHandle(String line) {
        // handle lines that are fence delimiters or inside a fence block
        return FENCE_DELIMITER.matcher(line).matches() || inFence;
    }

    /**
     * Processes a line for fence blocks. Returns ConversionResult if handled, otherwise failure.
     * If handled, further processing should be skipped (no escaping).
     */
    @Override
    public ConversionResult<String> process(String line) {
        if (FENCE_DELIMITER.matcher(line).matches()) {
            inFence = !inFence;
            return ConversionResult.success(line);
        }
        if (inFence) {
            return ConversionResult.success(line);
        }
        return ConversionResult.failure();
    }

    @Override
    public int getPriority() {
        // Highest priority
        return 110;
    }

    @Override
    public String getName() {
        return "quoting";
    }
}
