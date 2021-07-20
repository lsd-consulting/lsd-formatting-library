package lsd.format.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static lombok.AccessLevel.PRIVATE;
import static lsd.format.json.ObjectMapperCreator.PRETTY_PRINTER;
import static org.apache.commons.lang3.StringUtils.isBlank;

@NoArgsConstructor(access = PRIVATE)
public class JsonPrettyPrinter {

    private final static ObjectMapper objectMapper = ObjectMapperCreator.create();

    public static Optional<String> indentJson(final String document) {
        if (isBlank(document)) {
            return empty();
        }

        try {
            var map = objectMapper.readValue(document, Map.class);
            return Optional.of(objectMapper.setDefaultPrettyPrinter(PRETTY_PRINTER).writeValueAsString(map));
        } catch (final JsonProcessingException e) {
            return empty();
        }
    }
}
