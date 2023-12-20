package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.infra.database.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.infra.manager.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonFactory {
    @Value("${file.path.answer}")
    private String PATH_ANSWER;
    private static final String EXTENSION = FileType.JSON.extension();

    private final FileDirectoryFactory fileDirectoryFactory;
    private final FileManager fileManager;

    public void createJsonFile(String key, List<SingleQuestionModel> values) {
        final var directoryPath = STR."\{PATH_ANSWER}/\{key}";

        fileDirectoryFactory.validateAndCreateDirectory(directoryPath);

        Map<String, List<SingleQuestionModel>> organizedValues = new HashMap<>();

        values.forEach(value -> {
            var data = organizedValues.computeIfAbsent(value.lesson(), v -> new ArrayList<>());
            data.add(value);
        });

        organizedValues.forEach((k, v) -> {
            var file = new File(STR."\{directoryPath}/\{k}\{EXTENSION}");
            fileManager.write(file, v, FileType.JSON);
        });
    }
}
