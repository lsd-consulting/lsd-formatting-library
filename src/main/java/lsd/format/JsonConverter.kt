package lsd.format

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import lsd.format.config.log
import lsd.format.json.objectMapper

fun convertJsonStringToMap(document: String?): Map<String, Any> =
    document?.let {
        try {
            objectMapper.readValue(it, object : TypeReference<Map<String, Any>>() {})
        } catch (e: JsonProcessingException) {
            log().trace(e.message)
            HashMap()
        }
    } ?: HashMap()
