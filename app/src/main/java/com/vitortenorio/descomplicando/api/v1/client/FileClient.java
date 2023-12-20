package com.vitortenorio.descomplicando.api.v1.client;

import com.vitortenorio.descomplicando.api.v1.service.SingleFileService;
import com.vitortenorio.descomplicando.core.factory.XlsxFactory;
import com.vitortenorio.descomplicando.core.factory.FileDirectoryFactory;
import com.vitortenorio.descomplicando.core.factory.JsonFactory;
import com.vitortenorio.descomplicando.gateway.FileGateway;
import com.vitortenorio.descomplicando.infra.data.service.SingleQuestionData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileClient implements FileGateway {

    private final SingleFileService singleFileService;
    private final XlsxFactory xlsxFactory;
    private final SingleQuestionData singleQuestionData;

    @Value("${file.path.single}")
    private String PATH_SINGLE;

    @Override
    public void processSingleFile() {
        System.out.println("FileClient.processSingleFile => " + new Date());
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
                .forEach(singleFileService::processSingleFile);
    }

    private void createAndSaveFile() {
        final var singleQuestionModelMap = singleQuestionData.getAll();

        var workbook = new XSSFWorkbook();

        singleQuestionModelMap.entrySet()
                .parallelStream()
                .forEach(entry -> xlsxFactory.createWorkbookSheet(entry.getKey(), entry.getValue(), workbook));

        xlsxFactory.saveFile(workbook);
        System.out.println("FileClient.createAndSaveFile => " + new Date());
        log.info("File created and saved successfully.");
    }
}
