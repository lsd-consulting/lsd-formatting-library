package lsd.format.xml;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Optional;

import static java.util.Optional.empty;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.dom4j.DocumentHelper.parseText;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class XmlPrettyPrinter {
    private static final OutputFormat format = OutputFormat.createPrettyPrint();

    public static Optional<String> indentXml(final String document) {
        if (isBlank(document)) {
            return empty();
        }

        final StringWriter sw = new StringWriter();
        try {
            new XMLWriter(sw, format).write(parseText(document));
            return Optional.of(sw.toString());
        } catch (final IOException | DocumentException e) {
            log.trace(e.getMessage());
            return empty();
        }
    }
}
