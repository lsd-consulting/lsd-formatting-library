package lsd.format;

import lombok.Value;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readString;
import static lsd.format.PrettyPrinter.prettyPrint;
import static lsd.format.PrettyPrinter.prettyPrintJson;
import static org.approvaltests.Approvals.verify;
import static org.approvaltests.Approvals.verifyXml;

class PrettyPrinterShould {

    private final Options options = new Options().forFile().withExtension(".json");

    @Test
    void formatJson() throws IOException, URISyntaxException {
        verify(prettyPrint(readDocument("/source/source.json")), options);
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
    void convertsByteArrayFieldToString() {
        var jsonBytes = "{\"name\":\"Bond\"}".getBytes();
        var nonJsonBytes = "some regular text".getBytes();
        var nonStringBytes = new byte[]{1, 2, 3};

        verify(prettyPrintJson(new ExampleObjectWithBytes(jsonBytes, nonJsonBytes, nonStringBytes)), options);
    }

    private String readDocument(String fileName) throws IOException, URISyntaxException {
        return readString(Paths.get(getClass().getResource(fileName).toURI()), UTF_8);
    }

    @Value
    static class ExampleObject {
        int value;
    }

    @Value
    static class ExampleObjectWithBytes {
        byte[] bytesValue1;
        byte[] bytesValue2;
        byte[] bytesValue3;
    }
}
