package edu.hw10.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileCache implements Cacheable {
    private static  final Logger LOGGER = LogManager.getLogger();
    private final String tmpdir;

    public FileCache(String prefix) throws IOException {
        this.tmpdir = Files.createTempDirectory(prefix).toFile().getAbsolutePath();
    }

    @Override
    public String get(String key) {
        Path path = Path.of(tmpdir, key);

        if (!Files.exists(path)) {
            return null;
        }

        try {
            return Files.readString(path);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());

            return null;
        }
    }

    @Override
    public boolean put(String key, String value) {
        Path path = Path.of(tmpdir, key);

        try {
            Files.writeString(path, value);

            return true;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());

            return false;
        }
    }
}
