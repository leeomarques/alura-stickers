package org.example.alura;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JsonParser {

    // This Regex Capture a sequence of Character that are in a bracket
    private static final Pattern REGEX_ITEMS = Pattern.compile(".*\\[(.+)\\].*");

    // This Regex transforms a sequence of String in JSON
    private static final Pattern REGEX_CHARACTER_JSON = Pattern.compile("\"(.+?)\":\"(.*?)\"");

    public List<Map<String, String>> parse(String json) {

        Matcher matcher = REGEX_ITEMS.matcher(json); // Create Matcher

        // Verify if list is null
        if (!matcher.find()) {
            throw new IllegalArgumentException("Not Find Itens");
        }

        String[] items = matcher.group(1).split("\\},\\{");


        // Create a Stream of the map of the ApiReader body to create a new list of titles, images, and Rating this ApiReader

        List<Map<String, String>> parseBody = Arrays.stream(items)
                .map(item -> {
                    Map<String, String> characterItem = new HashMap<>();
                    Matcher matcherCharacterJson = REGEX_CHARACTER_JSON.matcher(item);
                    while (matcherCharacterJson.find()) {
                        String character = matcherCharacterJson.group(1);
                        String value = matcherCharacterJson.group(2);
                        characterItem.put(character, value);
                    }
                    return characterItem;
                })
                .collect(Collectors.toList());


        return parseBody;
    }
}
