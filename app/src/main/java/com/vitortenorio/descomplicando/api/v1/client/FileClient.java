package com.vitortenorio.descomplicando.api.v1.client;

import com.vitortenorio.descomplicando.api.v1.service.SingleNodeFileService;
import com.vitortenorio.descomplicando.core.factory.JsonFactory;
import com.vitortenorio.descomplicando.core.factory.XlsxFactory;
import com.vitortenorio.descomplicando.infrastructure.database.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.infrastructure.database.repository.impl.SingleQuestionRepositoryImpl;
import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.gateway.FileGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileClient implements FileGateway {

    private final SingleNodeFileService singleNodeFileService;
    private final XlsxFactory xlsxFactory;
    private final SingleQuestionRepositoryImpl singleQuestionDataRepository;
    private final JsonFactory jsonFactory;

    @Value("${file.path.single}")
    private String PATH_SINGLE;

    @Override
    public void process(FileType fileType) {
        processSingleNodeFile(fileType);
    }

    public void processSingleNodeFile(FileType fileType) {
        final var files = new File(PATH_SINGLE).listFiles();

        if (Objects.nonNull(files) && files.length > 0) {
            this.processFiles(files);
            this.createAndSaveFile(fileType);
        }
    }

    private void processFiles(final File[] files) {
        Arrays.stream(files)
                .parallel()
                .forEach(singleNodeFileService::processSingleFile);
    }

    private void createAndSaveFile(FileType fileType) {
        final var singleQuestionModelMap = singleQuestionDataRepository.getAllMap();

        switch (fileType) {
            case XLSX -> this.createAndSaveXlsxFile(singleQuestionModelMap);
            case JSON -> this.createAndSaveJsonFile(singleQuestionModelMap);
        }
    }

    private void createAndSaveJsonFile(Map<String, List<SingleQuestionModel>> data) {
        data.forEach(jsonFactory::createJsonFile);
    }

    private void createAndSaveXlsxFile(Map<String, List<SingleQuestionModel>> data) {
        var workbook = new XSSFWorkbook();

        data.forEach((key, value) -> xlsxFactory.createWorkbookSheet(key, value, workbook));

        xlsxFactory.saveFile(workbook);
    }
}
