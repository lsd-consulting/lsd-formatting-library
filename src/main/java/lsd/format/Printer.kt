package lsd.format

import lsd.format.config.log
import lsd.format.json.isValidJson
import lsd.format.json.objectMapper
import lsd.format.xml.flattenXml
import lsd.format.xml.isValidXml

fun printFlat(obj: Any?): String =
    obj?.let {
        try {
            when (obj) {
                is ByteArray -> {
                    if (obj.isEmpty()) "" else {
                        val string = String(obj)
                        if (isValidXml(string)) flattenXml(string) else if (isValidJson(string)) string.flattenJson() else string.trim()
                    }
                }

                is String -> {
                    if (obj.isEmpty()) "" else if (isValidXml(obj)) flattenXml(obj) else if (isValidJson(obj)) obj.flattenJson() else obj.trim()
                }

                else -> objectMapper.writeValueAsString(obj).flattenJson()
            }
        } catch (e: Exception) {
            log().error("Problem serialising intercepted object for LSD (this will not affect message processing): {} - {}", e.message, obj)
            ""
        }
    } ?: ""

fun String.flattenJson() = this
    .lines()
    .joinToString("") { it.trim() }
    .replace(Regex("""(\r\n)|\n"""), "")
    .replace(System.lineSeparator(), "")
