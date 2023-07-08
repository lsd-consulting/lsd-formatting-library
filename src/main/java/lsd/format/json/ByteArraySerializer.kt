package lsd.format.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import lsd.format.log.log
import lsd.format.xml.indentXml
import java.io.IOException

/**
 * Replaces the default byte[] serializer so that we can pretty print json strings that may be contained inside the
 * bytes[] when converted to a String object.
 */
class ByteArraySerializer @JvmOverloads constructor(t: Class<ByteArray?>? = null) : StdSerializer<ByteArray>(t) {

    @Throws(IOException::class)
    override fun serialize(bytes: ByteArray, generator: JsonGenerator, provider: SerializerProvider) {
        log().trace("Serialising:{}", bytes)
        val content = String(bytes).trim()
        if (content.startsWith("{")) {
            val indentJson = indentJson(content)
            if (indentJson != null) {
                generator.writeRawValue(indentJson)
                return
            }
        }
        if (content.startsWith("<")) {
            val indentXml = indentXml(content)
            if (indentXml != null) {
                generator.writeString(indentXml)
                return
            }
        }
        generator.writeString(content)
    }
}
