package edu.hw6.Task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("uncommentedmain")

public class CloneFile {

    private CloneFile() {
    }

    public static void cloneFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException();
        }
        String[] args = path.getFileName().toString().split("\\.");
        String filename = args[0];
        String extension = "";
        if (args.length > 1) {
            extension = "." + args[1];
        }
        int i = 1;

        while (true) {
            StringBuilder builder = new StringBuilder(filename);

            builder.append(" - копия");
            if (i > 1) {
                builder.append(" (").append(i).append(")");
            }
            if (!extension.equals(".")) {
                builder.append(extension);
            }

            Path newPath = Path.of(path.getParent().toString(), builder.toString());

            if (Files.exists(newPath)) {
                i++;

                continue;
            }

            Files.copy(path, newPath);
            return;
        }
    }

    public static void main(String[] args) throws IOException {

        cloneFile(Path.of("src\\main\\java\\edu\\hw6\\TEST\\2"));

    }
}

