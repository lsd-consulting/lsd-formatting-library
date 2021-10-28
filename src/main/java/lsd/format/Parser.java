package lsd.format;

import lombok.NoArgsConstructor;
import lsd.format.json.JsonParser;

import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Parser {
    public static Map<String, Object> parse(final String document) {
        return JsonParser.indentJson(document).orElse(new HashMap<>());
    }
}
