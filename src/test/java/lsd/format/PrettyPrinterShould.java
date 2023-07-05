package lsd.format;

import lombok.Value;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readString;
import static java.util.Objects.requireNonNull;
import static lsd.format.PrettyPrinter.prettyPrint;
import static lsd.format.PrettyPrinter.prettyPrintJson;
import static org.approvaltests.Approvals.*;

class PrettyPrinterShould {

    private final Options options = new Options().forFile().withExtension(".json");

    @Test
    void formatJson() throws IOException, URISyntaxException {
        verify(prettyPrint(readDocument("/source/source.json")), options);
    }

    @Test
    void formatJsonFromBytes() throws IOException, URISyntaxException {
        verify(prettyPrint(readDocument("/source/source.json").getBytes(UTF_8)), options);
    }

    @Test
    void formatJsonFromObject() {
        verify(prettyPrint(new ExampleObject(2)), options);
    }

    @Test
    void formatEmptyString() {
        verify(prettyPrint(""), options);
    }

    @Test
    void formatNullValue() {
        verify(prettyPrint(null), options);
    }

    @Test
    void formatJsonInString() throws IOException, URISyntaxException {
        verify(prettyPrintJson(readDocument("/source/source.json")), options);
    }

    @Test
    void formatJsonInEmptyString() {
        verify(prettyPrintJson(""), options);
    }

    @Test
    void formatJsonInNullValue() {
        verify(prettyPrintJson(null), options);
    }

    @Test
    void formatJsonInByteArray() throws IOException, URISyntaxException {
        verify(prettyPrintJson(readDocument("/source/source.json").getBytes(UTF_8)), options);
    }

    @Test
    void formatEmptyJsonByteArray() {
        verify(prettyPrintJson("".getBytes(UTF_8)), options);
    }

    @Test
    void formatArrayOfObjects() {
        verify(prettyPrintJson(new Object[]{new Object()}), options);
    }

    @Test
    void formatEmptyArrayOfObjects() {
        verify(prettyPrintJson(new Object[]{}), options);
    }

    @Test
    void formatTopLevelJsonArray() throws IOException, URISyntaxException {
        verify(prettyPrint(readDocument("/source/topLevelArray.json")), options);
    }

    @Test
    void formatXml() throws IOException, URISyntaxException {
        verifyXml(prettyPrint(readDocument("/source/source.xml")));
    }

    @Test
    void returnOriginalIfNeitherJsonNorXml() throws IOException, URISyntaxException {
        verify(prettyPrint(readDocument("/source/source.txt")));
    }

    @Test
    void serialiseObjectToJson() {
        verify(prettyPrintJson(new ExampleObject(2)), options);
    }

    @Test
    void convertByteArrayFieldToString() {
        var objects = byteArrayExamples()
                .map(ExampleObjectWithBytes::new)
                .map(PrettyPrinter::prettyPrintJson)
                .toArray();

        verifyAll("an object with bytes[] field.", objects);
    }

    private Stream<byte[]> byteArrayExamples() {
        return Stream.of(
                "".getBytes(),
                " ".getBytes(),
                "some regular text".getBytes(),
                "{looks like json}".getBytes(),
                "{\"name\":\"Bond\", \"age\":164, \"hungry\":true}".getBytes(),
                "<xml><x>hello</x><y>goodbye</y></xml>".getBytes(),
                "<looks like xml>".getBytes(),
                null,
                new byte[0],
                new byte[]{1, 2, 3}
        );
    }

    private String readDocument(String fileName) throws IOException, URISyntaxException {
        return readString(Paths.get(requireNonNull(getClass().getResource(fileName)).toURI()), UTF_8);
    }

    @Value
    static class ExampleObject {
        int value;
    }

    @Value
    static class ExampleObjectWithBytes {
        byte[] value;
    }
}
