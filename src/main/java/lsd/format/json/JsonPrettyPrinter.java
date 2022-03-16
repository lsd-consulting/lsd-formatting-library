package lsd.format.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class JsonPrettyPrinter {

    private final static ObjectMapper objectMapper = ObjectMapperCreator.create();

    public static Optional<String> indentJson(final String document) {
        if (isBlank(document)) {
            return empty();
        }

        try {
            return indentJsonMap(document);
        } catch (MismatchedInputException e) {
            if (e.getMessage().contains("JsonToken.START_ARRAY")) {
                try {
                    return indentJsonList(document);
                } catch (final JsonProcessingException ex) {
                    log.trace(ex.getMessage());
                    return empty();
                }
            }
            log.trace(e.getMessage());
            return empty();
        } catch (final JsonProcessingException e) {
            log.trace(e.getMessage());
            return empty();
        }
    }

    @NotNull
    private static Optional<String> indentJsonMap(String document) throws JsonProcessingException {
        var map = objectMapper.readValue(document, Map.class);
        return Optional.of(objectMapper.writeValueAsString(map));
    }

    @NotNull
    private static Optional<String> indentJsonList(String document) throws JsonProcessingException {
        var list = objectMapper.readValue(document, List.class);
        return Optional.of(objectMapper.writeValueAsString(list));
    }
}
