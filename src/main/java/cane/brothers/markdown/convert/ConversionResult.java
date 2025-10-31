package cane.brothers.markdown.convert;

import java.util.Optional;

/**
 * Represents the result of a convert attempt.
 * Analogous to <a href="https://github.com/jbock-java/either">Either</a>. If converted is true, value is present,
 *
 * @param <T> the type of the value being converted
 */
public record ConversionResult<T>(T value, Boolean converted) {

    public ConversionResult(T value) {
        this(value, null);
    }

    public static <T> ConversionResult<T> success(T convertedValue) {
        return new ConversionResult<>(convertedValue, true);
    }

    public static <T> ConversionResult<T> failure() {
        return new ConversionResult<>(null, false);
    }

    public Optional<Boolean> isConverted() {
        return Optional.ofNullable(converted);
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Converted value \"%s\"".formatted(value);
    }
}

