package com.vitortenorio.descomplicando.core.factory;

import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileFactory {
    public File mountFile(String s) {
        return new File(s);
    }

    public void createDirectory(Path pathFolder) {
        try {
            Files.createDirectory(pathFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateAndCreateDirectory(String path) {
        Path pathFolder = Path.of(path);

        if (!Files.exists(pathFolder)) {
            createDirectory(pathFolder);
        }
    }
}
