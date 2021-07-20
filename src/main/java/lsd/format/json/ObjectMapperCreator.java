package lsd.format.json;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class ObjectMapperCreator {
    public static final DefaultPrettyPrinter PRETTY_PRINTER = new DefaultPrettyPrinter() {
        @Override
        public DefaultPrettyPrinter createInstance() {
            super._objectFieldValueSeparatorWithSpaces = ": ";
            return new DefaultPrettyPrinter(this);
        }
    };

    public static ObjectMapper create() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
