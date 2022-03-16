package lsd.format.json;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonPrettyPrinterShould {

    @Test
    public void indentJson() {
        final Optional<String> result = JsonPrettyPrinter.indentJson("{\"key\":\"value\"}");
        assertTrue(result.isPresent());
        assertThat(result.get(), is("{\n  \"key\": \"value\"\n}"));
    }

    @Test
    public void inlineArrayElements() {
        final Optional<String> result = JsonPrettyPrinter.indentJson("{\"array\":[\"a1\",\"a2\",\"a3\"]}");
        assertTrue(result.isPresent());
        assertThat(result.get(), is("{\n  \"array\": [ \"a1\", \"a2\", \"a3\" ]\n}"));
    }

    @Test
    public void handleEmptyString() {
        final Optional<String> result = JsonPrettyPrinter.indentJson(null);
        assertThat(result, is(empty()));
    }

    @Test
    public void handleNonJson() {
        final Optional<String> result = JsonPrettyPrinter.indentJson("< >");
        assertThat(result, is(empty()));
    }

    @Test
    public void handleTopLevelArray() {
        final Optional<String> result = JsonPrettyPrinter.indentJson("[{\"key1\":\"value1\", \"key2\":\"value2\"}]\n");
        assertTrue(result.isPresent());
        assertThat(result.get(), equalTo("[ {\n  \"key1\": \"value1\",\n  \"key2\": \"value2\"\n} ]"));
    }

    @Test
    public void handleBrokenTopLevelArray() {
        final Optional<String> result = JsonPrettyPrinter.indentJson("[{\"key1\":\"value1\", \"key2\":\"value2\"}[][]]\n");
        assertThat(result, is(empty()));
    }
}