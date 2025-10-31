package cane.brothers.markdown.convert;

import java.util.regex.Pattern;

/**
 * Block handler for quoting/fenced code blocks (one-line, stateless)
 */
class QuotingBlockHandler extends AbstractBlockHandler {

    // Pattern to match quoting/fence delimiters (e.g., "```" or "```python")
    private static final Pattern FENCE_DELIMITER = Pattern.compile("^```(?:\\w+)?$", Pattern.UNICODE_CASE);
    private boolean inFence = false;

    @Override
    public boolean canHandle(ConversionResult<String> line) {
        var lineValue = line.getValue();
        // handle lines that are fence delimiters or inside a fence block
        return FENCE_DELIMITER.matcher(lineValue).matches() || inFence;
    }

    /**
     * Processes a line for fence blocks. Returns ConversionResult if handled, otherwise failure.
     * If handled, further processing should be skipped (no escaping).
     */
    @Override
    protected ConversionResult<String> handle(ConversionResult<String> line) {
        if (FENCE_DELIMITER.matcher(line.getValue()).matches()) {
            inFence = !inFence;
            return ConversionResult.success(line.value());
        }
        if (inFence) {
            return ConversionResult.success(line.value());
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
