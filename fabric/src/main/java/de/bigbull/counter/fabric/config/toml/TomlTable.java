package de.bigbull.counter.fabric.config.toml;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TomlTable extends TomlElement {
    private final Map<String, TomlElement> entries = new LinkedHashMap<>();
    private final Map<String, String> comments = new HashMap<>();

    public void put(String key, TomlElement element) {
        entries.put(key, element);
    }

    public void putString(String key, String value) {
        put(key, new TomlString(value));
    }

    public void putBoolean(String key, boolean value) {
        put(key, new TomlBoolean(value));
    }

    public void putInteger(String key, int value) {
        put(key, new TomlInteger(value));
    }

    public void putDouble(String key, double value) {
        put(key, new TomlDouble(value));
    }

    public TomlElement get(String key) {
        return entries.get(key);
    }

    public boolean contains(String key) {
        return entries.containsKey(key);
    }

    public void setComment(String key, String comment) {
        comments.put(key, comment);
    }

    @Override
    public String toTomlString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, TomlElement> entry : entries.entrySet()) {
            String key = entry.getKey();
            TomlElement value = entry.getValue();

            String comment = comments.get(key);
            if (comment != null && !comment.isEmpty()) {
                builder.append("# ").append(comment).append("\n");
            }

            if (value.isTable()) {
                builder.append("[").append(key).append("]\n");
                builder.append(value.toTomlString());
            } else {
                builder.append(key).append(" = ").append(value.toTomlString()).append("\n");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}