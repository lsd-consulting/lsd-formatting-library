package lsd.format.json

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import lsd.format.log.log
import org.apache.commons.lang3.StringUtils
import java.util.*

fun indentJson(document: String?): String? {
    return if (StringUtils.isBlank(document)) {
        null
    } else try {
        indentJsonMap(document)
    } catch (e: MismatchedInputException) {
        if (e.message!!.contains("JsonToken.START_ARRAY")) {
            return try {
                indentJsonList(document)
            } catch (ex: JsonProcessingException) {
                log().trace(ex.message)
                null
            }
        }
        log().error(e.message)
        null
    } catch (e: JsonProcessingException) {
        log().trace(e.message)
        null
    }
}

@Throws(JsonProcessingException::class)
private fun indentJsonMap(document: String?): String? {
    val map: Map<*, *> = objectMapper.readValue(document, MutableMap::class.java)
    return objectMapper.writeValueAsString(map)
}

@Throws(JsonProcessingException::class)
private fun indentJsonList(document: String?): String? {
    val list: List<*> = objectMapper.readValue(document, MutableList::class.java)
    return objectMapper.writeValueAsString(list)
}
