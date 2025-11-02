package cane.brothers.tg.md.convert;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PizzaTest {


    static Stream<Arguments> providePizzaTestData() {
        return Stream.of(Arguments.of("src/test/resources/pizza.md", "src/test/resources/pizza.txt"));
    }

    @ParameterizedTest
    @MethodSource("providePizzaTestData")
    void testConvertPizzaMdFromResources(String mdFile, String txtFile) throws IOException {
        String input = Files.readString(Paths.get(mdFile));
        String expected = Files.readString(Paths.get(txtFile));

        assertNotNull(input, "pizza.md should exist in resources");
        assertNotNull(expected, "pizza.txt should exist in resources");

        MarkdownToTelegramConverter converter = new MarkdownToTelegramConverter();
        String actual = converter.convert(input);

        assertNotNull(actual, "Converted output should not be null");
        assertEquals(expected, actual);
    }
}
