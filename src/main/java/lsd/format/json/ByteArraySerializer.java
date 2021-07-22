package lsd.format.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import static lsd.format.json.JsonPrettyPrinter.indentJson;
import static lsd.format.xml.XmlPrettyPrinter.indentXml;

/**
 * Replaces the default byte[] serializer so that we can pretty print json strings that may be contained inside the
 * bytes[] when converted to a String object.
 */
public class ByteArraySerializer extends StdSerializer<byte[]> {
    public ByteArraySerializer() {
        this(null);
    }

    public ByteArraySerializer(Class<byte[]> t) {
        super(t);
    }

    @Override
    public void serialize(byte[] bytes, JsonGenerator generator, SerializerProvider provider) throws IOException {
        var content = new String(bytes).strip();
        if (content.startsWith("{")) {
            var indentJson = indentJson(content);
            if (indentJson.isPresent()) {
                generator.writeRawValue(indentJson.get());
                return;
            }
        }
        if (content.startsWith("<")) {
            var indentXml = indentXml(content);
            if (indentXml.isPresent()) {
                generator.writeString(indentXml.get());
                return;
            }
        }
        generator.writeString(content);
    }
}
