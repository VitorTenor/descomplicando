package com.vitortenorio.descomplicando.api.v1.client;

import com.vitortenorio.descomplicando.api.v1.service.SingleNodeFileService;
import com.vitortenorio.descomplicando.core.factory.XlsxFactory;
import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.gateway.FileGateway;
import com.vitortenorio.descomplicando.infra.data.service.SingleQuestionData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileClient implements FileGateway {

    private final SingleNodeFileService singleNodeFileService;
    private final XlsxFactory xlsxFactory;
    private final SingleQuestionData singleQuestionData;

    @Value("${file.path.single}")
    private String PATH_SINGLE;

    @Override
    public void process(FileType fileType) {
        processSingleNodeFile();
    }

    public void processSingleNodeFile() {
        log.info("Starting to process files in the directory: {}", PATH_SINGLE);
        final var files = new File(PATH_SINGLE).listFiles();

        if (Objects.nonNull(files) && files.length > 0) {
            processFiles(files);
            createAndSaveFile();
        } else {
            log.info("No files found in the directory: {}", PATH_SINGLE);
        }
    }


    private void processFiles(final File[] files) {
        Arrays.stream(files)
                .parallel()
                .forEach(singleNodeFileService::processSingleFile);
    }

    private void createAndSaveFile() {
        final var singleQuestionModelMap = singleQuestionData.getAll();

        var workbook = new XSSFWorkbook();

        singleQuestionModelMap.forEach((key, value) -> xlsxFactory.createWorkbookSheet(key, value, workbook));

        xlsxFactory.saveFile(workbook);
        log.info("File created and saved successfully.");
    }
}
