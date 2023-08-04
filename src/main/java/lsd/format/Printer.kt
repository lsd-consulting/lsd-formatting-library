package lsd.format

import lsd.format.config.log
import lsd.format.json.isValidJson
import lsd.format.json.objectMapper
import lsd.format.xml.isValidXml

fun printFlat(obj: Any?): String =
    obj?.let {
        try {
            when (obj) {
                is ByteArray -> {
                    if (obj.isEmpty()) "" else {
                        val string = String(obj)
                        if (string.isValidJsonOrXml()) string.flatten() else string.trim()
                    }
                }

                is String -> {
                    if (obj.isEmpty()) "" else if (obj.isValidJsonOrXml()) obj.flatten() else obj.trim()
                }

                else -> objectMapper.writeValueAsString(obj).flatten()
            }
        } catch (e: Exception) {
            log().error("Problem serialising intercepted object for LSD (this will not affect message processing): {} - {}", e.message, obj)
            ""
        }
    } ?: ""

fun String.flatten() = this
    .lines()
    .joinToString("") { it.trim() }
    .replace(Regex("""(\r\n)|\n"""), "")
    .replace(System.lineSeparator(), "")

fun String.isValidJsonOrXml(): Boolean = isValidJson(this) || isValidXml(this)
