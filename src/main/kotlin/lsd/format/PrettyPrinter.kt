package lsd.format

import lsd.format.json.indentJson
import lsd.format.json.objectMapper
import lsd.format.xml.indentXml
import lsd.logging.log

fun prettyPrint(obj: Any?): String =
    obj?.let {
        try {
            when (obj) {
                is ByteArray -> {
                    if (obj.isEmpty()) "" else indent(String((obj)).trim())
                }

                is String -> {
                    if (obj.isEmpty()) "" else indent(obj.trim())
                }

                else -> indent(objectMapper.writeValueAsString(obj))
            }
        } catch (e: Exception) {
            log().error("Problem serialising intercepted object for LSD (this will not affect message processing): {} - {}", e.message, obj)
            ""
        }
    } ?: ""

private fun indent(doc: String): String = indentJson(doc) ?: indentXml(doc) ?: doc
