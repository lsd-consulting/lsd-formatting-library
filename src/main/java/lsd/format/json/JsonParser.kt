package lsd.format.json

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import lsd.format.log

private val objectMapper = createObjectMapper()

fun convert(document: String?): Map<String, Any>? {
    return document?.let {
        try {
            objectMapper.readValue(it, object : TypeReference<Map<String, Any>>() {})
        } catch (e: JsonProcessingException) {
            log().trace(e.message)
            null
        }
    }
}
