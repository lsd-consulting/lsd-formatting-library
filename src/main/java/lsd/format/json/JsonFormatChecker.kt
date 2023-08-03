package lsd.format.json

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

private val mapper: ObjectMapper = ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)

fun isValidJson(doc: String): Boolean {
    try {
        mapper.readTree(doc)
    } catch (e: JacksonException) {
        return false
    }
    return true
}
