package com.vitortenorio.descomplicando.api.v1.client;

import com.vitortenorio.descomplicando.api.v1.service.SingleFileService;
import com.vitortenorio.descomplicando.core.factory.XlsxFactory;
import com.vitortenorio.descomplicando.core.factory.FileDirectoryFactory;
import com.vitortenorio.descomplicando.core.factory.JsonFactory;
import com.vitortenorio.descomplicando.gateway.FileGateway;
import com.vitortenorio.descomplicando.infra.data.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.infra.data.service.SingleQuestionData;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FileClient implements FileGateway {

    private final FileDirectoryFactory fileDirectoryFactory;
    private final SingleFileService singleFileService;
    private final JsonFactory jsonFactory;
    private final XlsxFactory xlsxFactory;
    private final SingleQuestionData singleQuestionData;

    @Value("${file.path.single}")
    private String PATH_SINGLE;

    @Override
    public void processSingleFile() {
        File folder = fileDirectoryFactory.mountFile(PATH_SINGLE);
        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            for (File arquivoJson : files) {
                singleFileService.processAllSingleFile(arquivoJson);
            }
            createAndSaveFile();
        } else {
            System.out.println("Doesn't exist files to process.");
        }
    }

    private void createAndSaveFile() {
        Map<String, List<SingleQuestionModel>> singleQuestionModelMap = singleQuestionData.getSingleQuestionModelMap();

        Workbook workbook = new XSSFWorkbook();
        for (Map.Entry<String, List<SingleQuestionModel>> entry : singleQuestionModelMap.entrySet()) {
            String folderName = entry.getKey();
            List<SingleQuestionModel> singleQuestionModelList = entry.getValue();
            xlsxFactory.createAndSaveSingleFile(singleQuestionModelList, folderName, workbook);
        }

        xlsxFactory.saveWorkbook(workbook);

    }

}
