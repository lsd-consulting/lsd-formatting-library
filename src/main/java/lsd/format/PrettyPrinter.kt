package lsd.format

import com.fasterxml.jackson.core.JsonProcessingException
import lsd.format.config.log
import lsd.format.json.indentJson
import lsd.format.json.objectMapper
import lsd.format.xml.indentXml

fun prettyPrint(document: Any?): String {
    if (document == null) return ""
    if (document is ByteArray) {
        return indent(String((document as ByteArray?)!!))
    }
    return if (document !is String) {
        objectMapper.writeValueAsString(document)
    } else indent(document)
}

private fun indent(doc: String): String = indentJson(doc) ?: indentXml(doc) ?: doc

fun prettyPrintJson(obj: Any?): String? =
    if (obj == null) {
        ""
    } else if (obj is ByteArray && obj.size == 0) {
        ""
    } else try {
        prettyPrint(getDocument(obj))
    } catch (e: Exception) {
        log().error("Problem serialising intercepted object for LSD: {}", e.message)
        null
    }


@Throws(JsonProcessingException::class)
private fun getDocument(obj: Any): String = if (obj is String) obj else objectMapper.writeValueAsString(obj)
