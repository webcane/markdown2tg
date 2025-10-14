package cane.brothers.markdown.convert;

import java.util.Objects;

/**
 * Options for converting Markdown to Telegram MarkdownV2
 */
public final class ConversionOptions {


    private final HeadingStrategyType headingStrategyType;

    private final boolean preserveLineBreaks;

    private ConversionOptions(Builder builder) {
        this.headingStrategyType = builder.headingStrategyType;
        this.preserveLineBreaks = builder.preserveLineBreaks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public HeadingStrategyType getHeadingStrategyType() {
        return headingStrategyType;
    }

    public boolean isPreserveLineBreaks() {
        return preserveLineBreaks;
    }


    public static final class Builder {

        // default options
        private HeadingStrategyType headingStrategyType = HeadingStrategyType.BOLD_LINE;
        private boolean preserveLineBreaks = true;

        public Builder headingStrategyType(HeadingStrategyType value) {
            this.headingStrategyType = Objects.requireNonNull(value);
            return this;
        }

        public Builder preserveLineBreaks(boolean value) {
            this.preserveLineBreaks = value;
            return this;
        }

        public ConversionOptions build() {
            return new ConversionOptions(this);
        }
    }
}
