package com.vitortenorio.descomplicando.infrastructure.filemanager.service;

import com.vitortenorio.descomplicando.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileDirectoryService {

    public void validateAndCreateDirectory(final String path) {
        var pathFolder = Path.of(path);

        if (!Files.exists(pathFolder)) {
            createDirectory(pathFolder);
        }
    }

    private void createDirectory(final Path pathFolder) {
        try {
            Files.createDirectories(pathFolder);
        } catch (Exception e) {
            throw new BusinessException("Error to create directory: " + pathFolder);
        }
    }
}
