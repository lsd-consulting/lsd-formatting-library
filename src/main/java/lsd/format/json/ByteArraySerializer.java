package lsd.format.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import static lsd.format.PrettyPrinter.prettyPrint;

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
    public void serialize(byte[] bytes, JsonGenerator gen, SerializerProvider provider) throws IOException {
        var document = new String(bytes).strip();
        if (document.startsWith("{")) {
            gen.writeRawValue(prettyPrint(document));
        } else {
            gen.writeString(document);
        }
    }
}
