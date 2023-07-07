package lsd.format.json

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule.Builder

val PRETTY_PRINTER: DefaultPrettyPrinter = object : DefaultPrettyPrinter() {
    override fun createInstance(): DefaultPrettyPrinter {
        super._objectFieldValueSeparatorWithSpaces = ": "
        return DefaultPrettyPrinter(this)
    }
}

fun createObjectMapper(): ObjectMapper {
    val objectMapper = ObjectMapper()
    objectMapper.setDefaultPrettyPrinter(PRETTY_PRINTER)
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    objectMapper.registerModule(JavaTimeModule())
    objectMapper.registerModule(Builder().build())
    objectMapper.registerModule(customModule())
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    return objectMapper
}

private fun customModule(): SimpleModule {
    val module = SimpleModule()
    module.addSerializer(ByteArray::class.java, ByteArraySerializer())
    return module
}
