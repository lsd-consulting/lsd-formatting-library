package lsd.format

import lsd.format.config.log
import lsd.format.json.indentJson
import lsd.format.json.objectMapper
import lsd.format.xml.indentXml

fun prettyPrintJson(obj: Any?): String =
    obj?.let {
        try {
            when (obj) {
                is ByteArray -> {
                    if (obj.isEmpty()) "" else indent(String((obj)))
                }

                is String -> {
                    if (obj.isEmpty()) "" else indent(obj)
                }

                else -> indent(objectMapper.writeValueAsString(obj))
            }
        } catch (e: Exception) {
            log().error("Problem serialising intercepted object for LSD: {}", e.message)
            ""
        }
    } ?: ""

private fun indent(doc: String): String = indentJson(doc) ?: indentXml(doc) ?: doc
