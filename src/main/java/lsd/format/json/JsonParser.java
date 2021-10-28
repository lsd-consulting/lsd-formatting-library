package lsd.format.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class JsonParser {

    private final static ObjectMapper objectMapper = ObjectMapperCreator.create();

    public static Optional<Map<String, Object>> indentJson(final String document) {
        if (isBlank(document)) {
            return empty();
        }

        try {
            return Optional.of(objectMapper.readValue(document, new TypeReference<>() {}));
        } catch (final JsonProcessingException e) {
            log.trace(e.getMessage());
            return empty();
        }
    }
}
