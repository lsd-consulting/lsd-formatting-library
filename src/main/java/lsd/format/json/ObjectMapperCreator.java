package lsd.format.json;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.jetbrains.annotations.NotNull;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class ObjectMapperCreator {
    public static final DefaultPrettyPrinter PRETTY_PRINTER = new DefaultPrettyPrinter() {
        @NotNull
        @Override
        public DefaultPrettyPrinter createInstance() {
            super._objectFieldValueSeparatorWithSpaces = ": ";
            return new DefaultPrettyPrinter(this);
        }
    };

    public static ObjectMapper create() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDefaultPrettyPrinter(PRETTY_PRINTER);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new KotlinModule.Builder().build());
        objectMapper.registerModule(customModule());
        objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    private static SimpleModule customModule() {
        var module = new SimpleModule();
        module.addSerializer(byte[].class, new ByteArraySerializer());
        return module;
    }
}
