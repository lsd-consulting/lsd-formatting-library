package lsd.format;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import static lsd.format.json.JsonPrettyPrinter.indentJson;
import static lsd.format.xml.XmlPrettyPrinter.indentXml;

public class Formatter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String prettyPrint(final String document) {
        return indentJson(document).orElseGet(() -> indentXml(document).orElse(document));
    }

    @SneakyThrows
    public static String prettyPrintJson(final Object object) {
        return indentJson(objectMapper.writeValueAsString(object)).orElseThrow();
    }
}
