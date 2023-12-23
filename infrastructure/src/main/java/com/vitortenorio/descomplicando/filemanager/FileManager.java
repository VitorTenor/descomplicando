package com.vitortenorio.descomplicando.filemanager;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.filemanager.service.FileDirectoryService;
import com.vitortenorio.descomplicando.filemanager.service.FileReaderService;
import com.vitortenorio.descomplicando.filemanager.service.FileWriterService;
import lombok.RequiredArgsConstructor;
import org.apache.xpath.functions.FuncStringLength;
import org.springframework.stereotype.Component;

import java.io.File;


@Component
@RequiredArgsConstructor
public class FileManager {
    private final FileReaderService fileReaderService;
    private final FileWriterService fileWriterService;
    private final FileDirectoryService fileDirectoryService;

    public void write(final File file, final Object object, final FileType fileType) {
        fileWriterService.write(file, object, fileType);
    }

    public JsonNode readTree(String json) {
        return fileReaderService.readTree(json);
    }

    public <T> T readValue(final File file, final Class<T> clazz) {
        return fileReaderService.readValue(file, clazz);
    }

    public void validateAndCreateDirectory(final String directoryPath) {
        fileDirectoryService.validateAndCreateDirectory(directoryPath);
    }
}
