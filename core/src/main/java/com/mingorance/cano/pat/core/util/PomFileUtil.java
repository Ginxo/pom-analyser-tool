package com.mingorance.cano.pat.core.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PomFileUtil {

    public static List<File> getProjectPomFilesRecursive(final String projectFolderPath) {
        try (Stream<Path> walk = Files.walk(Paths.get(projectFolderPath))) {
            return walk.filter(Files::isDirectory)
                    .map(Path::toFile)
                    .map(directory -> directory.listFiles((dir1, name) -> "pom.xml".equals(name)))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error reading folder %s", projectFolderPath));
        }
    }

    public static Stream<File> getProjectPomFiles(final String projectFolderPath) {
        return Arrays.asList(new File(projectFolderPath).listFiles()).stream().filter(File::isDirectory)
                .map(directory -> directory.listFiles((dir1, name) -> "pom.xml".equals(name)))
                .flatMap(Arrays::stream);
    }
}
