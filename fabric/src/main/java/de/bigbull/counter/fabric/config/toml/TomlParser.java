package de.bigbull.counter.fabric.config.toml;

import java.io.*;

public class TomlParser {
    public static TomlTable parseFile(File file) throws IOException {
        if (!file.exists()) {
            return new TomlTable();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return parse(reader);
        }
    }

    public static TomlTable parse(BufferedReader reader) throws IOException {
        TomlTable rootTable = new TomlTable();
        TomlTable currentTable = rootTable;
        String currentTableName = "";
        String line;
        StringBuilder multilineString = null;
        String multilineKey = null;
        String comment = null;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("#")) {
                comment = line.substring(1).trim();
                continue;
            }

            if (multilineString != null) {
                if (line.endsWith("\"\"\"")) {
                    multilineString.append(line.substring(0, line.length() - 3));
                    currentTable.putString(multilineKey, multilineString.toString());
                    if (comment != null) {
                        currentTable.setComment(multilineKey, comment);
                        comment = null;
                    }
                    multilineString = null;
                    multilineKey = null;
                } else {
                    multilineString.append(line).append("\n");
                }
                continue;
            }

            if (line.startsWith("[") && line.endsWith("]")) {
                String tableName = line.substring(1, line.length() - 1).trim();
                currentTableName = tableName;

                String[] parts = tableName.split("\\.");
                currentTable = rootTable;

                for (String part : parts) {
                    if (!currentTable.contains(part)) {
                        TomlTable newTable = new TomlTable();
                        currentTable.put(part, newTable);
                    } else if (!currentTable.get(part).isTable()) {
                        throw new IOException("TOML error: " + part + " is already defined as a value, not as a table");
                    }
                    currentTable = currentTable.get(part).asTable();
                }

                if (comment != null) {
                    String lastPart = parts[parts.length - 1];
                    TomlTable parentTable = rootTable;
                    for (int i = 0; i < parts.length - 1; i++) {
                        parentTable = parentTable.get(parts[i]).asTable();
                    }
                    parentTable.setComment(lastPart, comment);
                    comment = null;
                }

                continue;
            }

            int equalPos = line.indexOf('=');
            if (equalPos > 0) {
                String key = line.substring(0, equalPos).trim();
                String value = line.substring(equalPos + 1).trim();

                if (value.startsWith("\"\"\"")) {
                    if (value.endsWith("\"\"\"") && value.length() > 6) {
                        String stringValue = value.substring(3, value.length() - 3);
                        currentTable.putString(key, stringValue);
                    } else {
                        multilineString = new StringBuilder(value.substring(3));
                        multilineKey = key;
                        continue;
                    }
                }
                else if (value.startsWith("\"") && value.endsWith("\"")) {
                    String stringValue = value.substring(1, value.length() - 1);
                    currentTable.putString(key, stringValue);
                }
                else if (value.equals("true") || value.equals("false")) {
                    currentTable.putBoolean(key, Boolean.parseBoolean(value));
                }
                else if (value.matches("-?\\d+")) {
                    int intValue = Integer.parseInt(value);
                    currentTable.putInteger(key, intValue);
                }
                else if (value.matches("-?\\d+\\.\\d+")) {
                    double doubleValue = Double.parseDouble(value);
                    currentTable.putDouble(key, doubleValue);
                }

                if (comment != null) {
                    currentTable.setComment(key, comment);
                    comment = null;
                }
            }
        }

        return rootTable;
    }

    public static void writeToFile(TomlTable table, File file) throws IOException {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(table.toTomlString());
        }
    }
}