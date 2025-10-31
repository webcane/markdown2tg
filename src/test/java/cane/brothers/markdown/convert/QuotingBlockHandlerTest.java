package cane.brothers.markdown.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuotingBlockHandlerTest {
    private QuotingBlockHandler handler;

    @BeforeEach
    void setUp() {
        handler = new QuotingBlockHandler();
    }

    @Test
    void testSimpleFenceBlock() {
        List<String> out = new ArrayList<>();
        String[] lines = {"```", "code line 1", "code line 2", "```"};
        for (String line : lines) {
            var result = new ConversionResult<String>(line);
            if (handler.canHandle(result)) {
                result = handler.apply(result);
                assertTrue(result.isConverted().orElse(false));
                out.add(result.getValue());
            }
        }
        assertEquals(List.of("```", "code line 1", "code line 2", "```"), out);
    }

    @Test
    void testFenceBlockWithLanguage() {
        List<String> out = new ArrayList<>();
        String[] lines = {"```java", "int x = 1;", "```"};
        for (String line : lines) {
            var result = new ConversionResult<String>(line);
            if (handler.canHandle(result)) {
                result = handler.apply(result);
                assertTrue(result.isConverted().orElse(false));
                out.add(result.getValue());
            }
        }
        assertEquals(List.of("```java", "int x = 1;", "```"), out);
    }

    @Test
    void testMultipleFenceBlocks() {
        List<String> out = new ArrayList<>();
        String[] lines = {"```", "block1", "```", "```", "block2", "```"};
        for (String line : lines) {
            var result = new ConversionResult<String>(line);
            if (handler.canHandle(result)) {
                result = handler.apply(result);
                assertTrue(result.isConverted().orElse(false));
                out.add(result.getValue());
            }
        }
        assertEquals(List.of("```", "block1", "```", "```", "block2", "```"), out);
    }

    @Test
    void testNonFenceLine() {
        String line = "not a fence";
        var result = new ConversionResult<String>(line);
        assertFalse(handler.canHandle(result));
        result = handler.apply(result);
        assertFalse(result.isConverted().orElse(false));
    }

    @Test
    void testEmptyFenceBlock() {
        List<String> out = new ArrayList<>();
        String[] lines = {"```", "```"};
        for (String line : lines) {
            var result = new ConversionResult<String>(line);
            if (handler.canHandle(result)) {
                result = handler.apply(result);
                assertTrue(result.isConverted().orElse(false));
                out.add(result.getValue());
            }
        }
        assertEquals(List.of("```", "```"), out);
    }
}
