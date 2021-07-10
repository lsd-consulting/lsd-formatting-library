package lsd.format;

import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readString;
import static lsd.format.Formatter.indent;
import static org.approvaltests.Approvals.verify;
import static org.approvaltests.Approvals.verifyXml;

class FormatterShould {

    @Test
    void formatJson() throws IOException, URISyntaxException {
        verify(indent(readDocument("/source/source.json")), new Options().forFile().withExtension(".json"));
    }

    @Test
    void formatXml() throws IOException, URISyntaxException {
        verifyXml(indent(readDocument("/source/source.xml")));
    }

    @Test
    void returnOriginalIfNeitherJsonNorXml() throws IOException, URISyntaxException {
        verify(indent(readDocument("/source/source.txt")));
    }

    private String readDocument(String fileName) throws IOException, URISyntaxException {
        return readString(Paths.get(getClass().getResource(fileName).toURI()), UTF_8);
    }
}
