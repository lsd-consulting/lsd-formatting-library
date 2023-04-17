package lsd.format;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readString;
import static java.util.Objects.requireNonNull;
import static lsd.format.Parser.parse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ParserShould {

    @Test
    void parseJson() throws IOException, URISyntaxException {
        Map<String, Object> result = parse(readDocument("/source/source.json"));

        assertThat(result, hasEntry("key", List.of("value1", "value2", "value3")));
    }

    @Test
    void returnEmptyMapIfNotJson() throws IOException, URISyntaxException {
        Map<String, Object> result = parse(readDocument("/source/source.txt"));

        assertThat(result, is(anEmptyMap()));
    }

    @Test
    void handleEmptyString() {
        Map<String, Object> result = parse("");

        assertThat(result, is(anEmptyMap()));
    }

    @Test
    void handleMissingValue() {
        Map<String, Object> result = parse(null);

        assertThat(result, is(anEmptyMap()));
    }

    private String readDocument(String fileName) throws IOException, URISyntaxException {
        return readString(Paths.get(requireNonNull(getClass().getResource(fileName)).toURI()), UTF_8);
    }
}
