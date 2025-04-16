package de.bigbull.counter.fabric.config.toml;

/**
 * Basisklasse für alle TOML-Elemente
 */
public abstract class TomlElement {

    /**
     * Prüft, ob dieses Element eine Tabelle ist
     */
    public boolean isTable() {
        return this instanceof TomlTable;
    }

    /**
     * Prüft, ob dieses Element ein String ist
     */
    public boolean isString() {
        return this instanceof TomlString;
    }

    /**
     * Prüft, ob dieses Element ein Boolean ist
     */
    public boolean isBoolean() {
        return this instanceof TomlBoolean;
    }

    /**
     * Prüft, ob dieses Element ein Integer ist
     */
    public boolean isInteger() {
        return this instanceof TomlInteger;
    }

    /**
     * Prüft, ob dieses Element ein Double ist
     */
    public boolean isDouble() {
        return this instanceof TomlDouble;
    }

    /**
     * Prüft, ob dieses Element ein Long ist
     */
    public boolean isLong() {
        return this instanceof TomlLong;
    }

    /**
     * Konvertiert dieses Element zu einer Tabelle
     */
    public TomlTable asTable() {
        if (!isTable()) {
            throw new ClassCastException("Element ist keine Tabelle");
        }
        return (TomlTable) this;
    }

    /**
     * Konvertiert dieses Element zu einem String
     */
    public String asString() {
        if (!isString()) {
            throw new ClassCastException("Element ist kein String");
        }
        return ((TomlString) this).getValue();
    }

    /**
     * Konvertiert dieses Element zu einem Boolean
     */
    public boolean asBoolean() {
        if (!isBoolean()) {
            throw new ClassCastException("Element ist kein Boolean");
        }
        return ((TomlBoolean) this).getValue();
    }

    /**
     * Konvertiert dieses Element zu einem Integer
     */
    public int asInteger() {
        if (!isInteger()) {
            throw new ClassCastException("Element ist kein Integer");
        }
        return ((TomlInteger) this).getValue();
    }

    /**
     * Konvertiert dieses Element zu einem Double
     */
    public double asDouble() {
        if (!isDouble()) {
            throw new ClassCastException("Element ist kein Double");
        }
        return ((TomlDouble) this).getValue();
    }

    /**
     * Konvertiert dieses Element zu einem Long
     */
    public long asLong() {
        if (!isLong()) {
            throw new ClassCastException("Element ist kein Long");
        }
        return ((TomlLong) this).getValue();
    }

    /**
     * Erstellt einen formatierten String-Wert für TOML
     */
    public abstract String toTomlString();
}