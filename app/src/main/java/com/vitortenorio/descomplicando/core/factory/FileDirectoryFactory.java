package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileDirectoryFactory {

    public void createDirectory(final Path pathFolder) {
        try {
            Files.createDirectories(pathFolder);
        } catch (Exception e) {
            throw new BusinessException("Error to create directory: " + pathFolder);
        }
    }

    public void validateAndCreateDirectory(final String path) {
        var pathFolder = Path.of(path);

        if (!Files.exists(pathFolder)) {
            createDirectory(pathFolder);
        }
    }
}
