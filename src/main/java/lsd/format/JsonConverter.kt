package lsd.format

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import lsd.format.json.objectMapper
import lsd.format.log.log

fun convertJsonStringToMap(document: String?): Map<String, Any> =
    document?.let {
        try {
            objectMapper.readValue<Map<String, Any>?>(it, object : TypeReference<Map<String, Any>>() {})
        } catch (e: JsonProcessingException) {
            log().trace(e.message)
            HashMap()
        }
    } ?: HashMap()
