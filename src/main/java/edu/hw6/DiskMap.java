package edu.hw6;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DiskMap implements Map<String, String> {

    private final String directoryPath;

    public DiskMap(String directoryPath) throws IOException {
        Path directory = Path.of(directoryPath);
        if (Files.exists(directory) && !Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Передан существующий файл, а не директория");
        }
        if (!Files.exists(directory)) {
            try {
                Files.createDirectory(directory);
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }
        this.directoryPath = directoryPath;
    }

    private void createFile(String filename) throws IOException {
        Path filePath = Path.of(filename);

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    private Path getPath() {
        return Path.of(directoryPath);
    }

    private Path getPath(String filename) {
        return Path.of(directoryPath, filename);
    }

    @Override
    public int size() {
        try (var stream = Files.list(getPath())) {
            return (int) stream
                .filter(Files::isRegularFile)
                .count();
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof String)) {
            throw new IllegalArgumentException("Ключ должен быть строкой");
        }

        return Files.exists(getPath((String) key));
    }

    @Override
    public boolean containsValue(Object value) {
        if (!(value instanceof String)) {
            throw new IllegalArgumentException("Значение должно быть строкой");
        }

        try (var stream = Files.list(getPath())) {
            return stream
                .filter(Files::isRegularFile)
                .anyMatch(path -> {
                    try {
                        return Objects.equals(Files.readString(path), value);
                    } catch (IOException e) {
                        return false;
                    }
                });
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String get(Object key) {
        try {
            return Files.readString(getPath((String) key));
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        try {
            createFile(key);
            Files.writeString(getPath(key), value);
            return value;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String remove(Object key) {
        String Value = get(key);

        try {
            Files.deleteIfExists(getPath((String) key));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Value;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        for (var entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        try (var stream = Files.list(getPath())) {
            stream.forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException ignored) {
                }
            });
        } catch (IOException ignored) {

        }
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        Set<String> keys = new HashSet<>();

        try (var stream = Files.list(getPath())) {
            stream
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    keys.add(path.getFileName().toString());
                });
        } catch (IOException ignored) {
        }

        return keys;
    }

    @NotNull
    @Override
    public Collection<String> values() {
        Set<String> values = new HashSet<>();

        try (var stream = Files.list(getPath())) {
            stream
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        values.add(Files.readString(path));
                    } catch(IOException ignored) {
                    }
                });
        } catch (IOException ignored) {
        }

        return values;
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        Set<Entry<String, String>> entries = new HashSet<>();
        try (var stream = Files.list(getPath())) {
            stream
                .filter(Files::isRegularFile)
                .forEach(path ->  {
                    try {
                        entries.add( Map.entry(path.getFileName().toString(), Files.readString(path)));
                    } catch (IOException ignored) {
                    }
            });
        } catch (IOException ignored) {
        }

        return entries;
    }

    public static void main(String[] args) throws IOException {

        DiskMap map=new DiskMap("src\\main\\java\\edu\\hw6\\TEST");
        map.put("1","a");
        map.put("2","b");
        map.put("3","c");
        map.put("4","d");
        map.put("5","e");
        map.put("6","f");
        map.put("7","g");
        map.put("8","h");
        map.put("9","i");
        System.out.print(map.get("4"));


    }

}
