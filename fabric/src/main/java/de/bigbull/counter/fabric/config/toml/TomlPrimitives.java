package de.bigbull.counter.fabric.config.toml;

class TomlString extends TomlElement {
    private final String value;

    public TomlString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toTomlString() {
        return "\"" + value.replace("\"", "\\\"") + "\"";
    }
}

class TomlBoolean extends TomlElement {
    private final boolean value;

    public TomlBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toTomlString() {
        return String.valueOf(value);
    }
}

class TomlInteger extends TomlElement {
    private final int value;

    public TomlInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toTomlString() {
        return String.valueOf(value);
    }
}

class TomlDouble extends TomlElement {
    private final double value;

    public TomlDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toTomlString() {
        return String.valueOf(value);
    }
}