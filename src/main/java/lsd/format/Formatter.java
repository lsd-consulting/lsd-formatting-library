package lsd.format;

import static lsd.format.json.JsonPrettyPrinter.indentJson;
import static lsd.format.xml.XmlPrettyPrinter.indentXml;

public class Formatter {
    public static String indent(final String document) {
        return indentJson(document).orElseGet(() -> indentXml(document).orElse(document));
    }
}
