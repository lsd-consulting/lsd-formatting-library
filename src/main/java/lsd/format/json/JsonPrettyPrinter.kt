package lsd.format.json

import com.fasterxml.jackson.core.JsonProcessingException
import lsd.format.config.log

fun indentJson(document: String?): String? {
    return if (document.isNullOrBlank()) {
        null
    } else try {
        val map = objectMapper.readTree(document)
        return objectMapper.writeValueAsString(map)
    } catch (e: JsonProcessingException) {
        log().trace("Not JSON: {}", e.message)
        null
    }
}
