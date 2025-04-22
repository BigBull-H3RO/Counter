package de.bigbull.counter.fabric.config.toml;

public abstract class TomlElement {

    public boolean isTable() {
        return this instanceof TomlTable;
    }

    public boolean isString() {
        return this instanceof TomlString;
    }

    public boolean isBoolean() {
        return this instanceof TomlBoolean;
    }

    public boolean isInteger() {
        return this instanceof TomlInteger;
    }

    public boolean isDouble() {
        return this instanceof TomlDouble;
    }

    public TomlTable asTable() {
        if (!isTable()) {
            throw new ClassCastException("Element is not a table");
        }
        return (TomlTable) this;
    }

    public String asString() {
        if (!isString()) {
            throw new ClassCastException("Element is not a string");
        }
        return ((TomlString) this).getValue();
    }

    public boolean asBoolean() {
        if (!isBoolean()) {
            throw new ClassCastException("Element is not a boolean");
        }
        return ((TomlBoolean) this).getValue();
    }

    public int asInteger() {
        if (!isInteger()) {
            throw new ClassCastException("Element is not an integer");
        }
        return ((TomlInteger) this).getValue();
    }

    public double asDouble() {
        if (!isDouble()) {
            throw new ClassCastException("Element is not a double");
        }
        return ((TomlDouble) this).getValue();
    }

    public abstract String toTomlString();
}