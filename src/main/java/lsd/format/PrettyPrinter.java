package lsd.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lsd.format.json.ObjectMapperCreator;

import static lombok.AccessLevel.PRIVATE;
import static lsd.format.json.JsonPrettyPrinter.indentJson;
import static lsd.format.xml.XmlPrettyPrinter.indentXml;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class PrettyPrinter {
    private static final ObjectMapper objectMapper = ObjectMapperCreator.create();

    public static String prettyPrint(final String document) {
        return document == null ? "" : indentJson(document).orElseGet(() -> indentXml(document).orElse(document));
    }

    @SneakyThrows
    public static String prettyPrintJson(final Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof byte[] && ((byte[])object).length == 0) {
            return "";
        }
        try {
            return prettyPrint(getDocument(object));
        } catch (Exception e) {
            log.error("Problem serialising intercepted object for LSD: {}", e.getMessage());
            return null;
        }
    }

    private static String getDocument(Object object) throws JsonProcessingException {
        return object instanceof String ? (String) object : objectMapper.writeValueAsString(object);
    }
}
