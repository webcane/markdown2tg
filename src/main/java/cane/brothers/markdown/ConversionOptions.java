package cane.brothers.markdown;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Options for converting Markdown to Telegram MarkdownV2
 */
public final class ConversionOptions {

    private final ListHandling listHandling;
    private final HeadingHandling headingHandling;
    private final HtmlPolicy htmlPolicy;
    private final ImageHandling imageHandling;
    private final boolean preserveLineBreaks;

    private static final Pattern HEADER = Pattern.compile("^(#{1,6})\\s+(.*)$");

    private ConversionOptions(Builder builder) {
        this.listHandling = builder.listHandling;
        this.headingHandling = builder.headingHandling;
        this.htmlPolicy = builder.htmlPolicy;
        this.imageHandling = builder.imageHandling;
        this.preserveLineBreaks = builder.preserveLineBreaks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ListHandling getListHandling() {
        return listHandling;
    }

    public HeadingHandling getHeadingHandling() {
        return headingHandling;
    }

    public HtmlPolicy getHtmlPolicy() {
        return htmlPolicy;
    }

    public ImageHandling getImageHandling() {
        return imageHandling;
    }

    public boolean isPreserveLineBreaks() {
        return preserveLineBreaks;
    }

    /**
     * List handling options
     */
    public enum ListHandling {
        KEEP_NUMBERED,
        REPLACE_BULLET_WITH_DASH
    }

    /**
     * External interface for supplying handle flag
     */
    public interface HandleSupplier extends Supplier<Boolean> {

        default Boolean isHandle() {
            return get();
        }
    }

    /**
     * Heading handling options
     */
    public enum HeadingHandling implements Function<String, String>, HandleSupplier {
        /**
         * No special handling, leave as is
         */
        NONE(false) {
            @Override
            public String apply(String s) {
                return s;
            }
        },
        /**
         * Convert heading to bold line (e.g. "# Title" -> "*Title*") only if the line is a valid heading
         */
        BOLD_LINE(true) {
            @Override
            public String apply(String s) {
                Matcher h = HEADER.matcher(s);
                if (h.matches()) {
                    String text = h.group(2).trim();
                    String escaped = EscapeUtils.escape(text);
                    return "*" + escaped + "*";
                }
                return s;
            }
        };

        private final boolean handle;

        /**
         * Constructor
         *
         * @param handle flag - whether to handle headings
         */
        HeadingHandling(boolean handle) {
            this.handle = handle;
        }
        @Override
        public Boolean get() {
            return handle;
        }
    };

    /**
     * HTML handling options
     */
    public enum HtmlPolicy {
        STRIP,
        ESCAPE_AS_TEXT
    }

    /**
     * Image handling options
     */
    public enum ImageHandling {
        AS_LINK
    }

    public static final class Builder {
        private ListHandling listHandling = ListHandling.REPLACE_BULLET_WITH_DASH;
        private HeadingHandling headingHandling = HeadingHandling.BOLD_LINE;
        private HtmlPolicy htmlPolicy = HtmlPolicy.STRIP;
        private ImageHandling imageHandling = ImageHandling.AS_LINK;
        private boolean preserveLineBreaks = true;

        public Builder listHandling(ListHandling value) {
            this.listHandling = Objects.requireNonNull(value);
            return this;
        }

        public Builder headingHandling(HeadingHandling value) {
            this.headingHandling = Objects.requireNonNull(value);
            return this;
        }

        public Builder htmlPolicy(HtmlPolicy value) {
            this.htmlPolicy = Objects.requireNonNull(value);
            return this;
        }

        public Builder imageHandling(ImageHandling value) {
            this.imageHandling = Objects.requireNonNull(value);
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
