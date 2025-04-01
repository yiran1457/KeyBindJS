package com.common.keybindjs.kubejs.extra;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSIO {
    private static final List<String> AllowSuffixes = Arrays.asList(".js", ".ts", ".txt", ".toml");
    public static JSIO INSTANCE = new JSIO();

    public List<@NotNull String> read(String path) throws IOException {
        if (isAllowed(path)) return new ArrayList<>();
        return Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
    }

    public void write(String path, String[] file) throws IOException {
        if (isAllowed(path)) return;
        Path filePath = Path.of(path);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, List.of(file));
    }

    public boolean isAllowed(@NotNull String path) {
        boolean allow = false;
        for (String allowSuffix : JSIO.AllowSuffixes) {
            if (path.endsWith(allowSuffix)) {
                allow = true;
                break;
            }
        }
        return !allow;
    }

    public List<String> getAllowSuffix() {
        return new ArrayList<>(JSIO.AllowSuffixes);
    }
}
