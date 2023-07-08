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

val objectMapper = createObjectMapper()

fun createObjectMapper(): ObjectMapper {
    val mapper = ObjectMapper()
    mapper.setDefaultPrettyPrinter(PRETTY_PRINTER)
    mapper.enable(SerializationFeature.INDENT_OUTPUT)
    mapper.registerModule(JavaTimeModule())
    mapper.registerModule(Builder().build())
    mapper.registerModule(customModule())
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    return mapper
}

private fun customModule(): SimpleModule {
    val module = SimpleModule()
    module.addSerializer(ByteArray::class.java, ByteArraySerializer())
    return module
}
