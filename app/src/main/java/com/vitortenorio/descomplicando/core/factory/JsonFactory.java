package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonFactory {

    private final FileFactory fileFactory;
    private final ObjectMapperUtil objectMapperUtil;

    @Value("${file.path.answer}")
    private String PATH_ANSWER;
    private static final String JSON_EXTENSION = ".json";

    public <T> void createAndSaveFile(List<T> values, String folderName, String fileName) {
        String finalPath = PATH_ANSWER + folderName;
        fileFactory.validateAndCreateDirectory(finalPath);

        saveFile(
                createFile(finalPath + fileName),
                values
        );
    }

    private File createFile(String folderAndFileName) {
        return fileFactory.mountFile(folderAndFileName + JSON_EXTENSION);
    }

    private <T> void saveFile(File file, List<T> values) {
        objectMapperUtil.writeValueAsFile(file, values);
    }
}
