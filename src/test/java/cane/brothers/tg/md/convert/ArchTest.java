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

public class ArchTest {


    static Stream<Arguments> provideArchTestData() {
        return Stream.of(Arguments.of("src/test/resources/arch.md", "src/test/resources/arch.txt"));
    }

    @ParameterizedTest
    @MethodSource("provideArchTestData")
    void testConvertArchMdFromResources(String mdFile, String txtFile) throws IOException {
        String input = Files.readString(Paths.get(mdFile));
        String expected = Files.readString(Paths.get(txtFile));

        assertNotNull(input, "arch.md should exist in resources");
        assertNotNull(expected, "arch.txt should exist in resources");

        MarkdownToTelegramConverter converter = new MarkdownToTelegramConverter();
        String actual = converter.convert(input);

        assertNotNull(actual, "Converted output should not be null");
        assertEquals(expected, actual);
    }
}
