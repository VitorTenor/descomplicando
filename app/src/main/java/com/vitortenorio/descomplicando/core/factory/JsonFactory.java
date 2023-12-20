package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.infra.data.model.SingleQuestionModel;
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

    private final ObjectMapperUtil objectMapperUtil;
    private final FileDirectoryFactory fileDirectoryFactory;

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
            objectMapperUtil.writeValueAsFile(file, v);
        });
    }
}
