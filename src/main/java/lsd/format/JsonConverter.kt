package lsd.format

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import lsd.format.json.createObjectMapper

private val objectMapper = createObjectMapper()

fun parse(document: String?): Map<String, Any> =
    document?.let {
        try {
            objectMapper.readValue<Map<String, Any>?>(it, object : TypeReference<Map<String, Any>>() {})
        } catch (e: JsonProcessingException) {
            log().trace(e.message)
            HashMap()
        }
    } ?: HashMap()
