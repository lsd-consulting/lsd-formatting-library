package lsd.format

import com.fasterxml.jackson.core.JsonProcessingException
import lsd.format.json.createObjectMapper
import lsd.format.json.indentJson
import lsd.format.log.log
import lsd.format.xml.indentXml

private val objectMapper = createObjectMapper()

fun prettyPrint(document: Any?): String {
    if (document == null) return ""
    if (document is ByteArray) {
        return intend(String((document as ByteArray?)!!))
    }
    return if (document !is String) {
        objectMapper.writeValueAsString(document)
    } else intend(document)
}

private fun intend(doc: String): String = indentJson(doc) ?: indentXml(doc) ?: doc

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
