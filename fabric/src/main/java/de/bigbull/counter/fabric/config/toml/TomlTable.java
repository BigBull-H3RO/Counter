package de.bigbull.counter.fabric.config.toml;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Repräsentiert eine TOML-Tabelle
 */
public class TomlTable extends TomlElement {
    private final Map<String, TomlElement> entries = new LinkedHashMap<>();
    private final Map<String, String> comments = new HashMap<>();

    /**
     * Fügt ein Element zur Tabelle hinzu
     */
    public void put(String key, TomlElement element) {
        entries.put(key, element);
    }

    /**
     * Fügt einen String-Wert zur Tabelle hinzu
     */
    public void putString(String key, String value) {
        put(key, new TomlString(value));
    }

    /**
     * Fügt einen Boolean-Wert zur Tabelle hinzu
     */
    public void putBoolean(String key, boolean value) {
        put(key, new TomlBoolean(value));
    }

    /**
     * Fügt einen Integer-Wert zur Tabelle hinzu
     */
    public void putInteger(String key, int value) {
        put(key, new TomlInteger(value));
    }

    /**
     * Fügt einen Double-Wert zur Tabelle hinzu
     */
    public void putDouble(String key, double value) {
        put(key, new TomlDouble(value));
    }

    /**
     * Fügt einen Long-Wert zur Tabelle hinzu
     */
    public void putLong(String key, long value) {
        put(key, new TomlLong(value));
    }

    /**
     * Holt ein Element aus der Tabelle
     */
    public TomlElement get(String key) {
        return entries.get(key);
    }

    /**
     * Prüft, ob ein Schlüssel existiert
     */
    public boolean contains(String key) {
        return entries.containsKey(key);
    }

    /**
     * Fügt einen Kommentar für einen Schlüssel hinzu
     */
    public void setComment(String key, String comment) {
        comments.put(key, comment);
    }

    /**
     * Gibt den Kommentar für einen Schlüssel zurück
     */
    public String getComment(String key) {
        return comments.get(key);
    }

    /**
     * Gibt alle Einträge zurück
     */
    public Set<Map.Entry<String, TomlElement>> entrySet() {
        return entries.entrySet();
    }

    /**
     * Gibt alle Schlüssel zurück
     */
    public Set<String> keySet() {
        return entries.keySet();
    }

    @Override
    public String toTomlString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, TomlElement> entry : entries.entrySet()) {
            String key = entry.getKey();
            TomlElement value = entry.getValue();

            // Kommentar hinzufügen, wenn vorhanden
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