package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import com.vitortenorio.descomplicando.infra.data.model.SingleQuestionModel;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvFactory {
    @Value("${file.path.answer}")
    private String PATH_ANSWER;

    private static final String CSV_EXTENSION = ".csv";
    private final ObjectMapperUtil objectMapperUtil;
    private final FileFactory fileFactory;


    public void createAndSaveSingleFile(List<SingleQuestionModel> list, String fileName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(fileName);

            // Criação de uma fonte com negrito
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            // Criação de um estilo com a fonte negrito
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Criação do cabeçalho em negrito
            Row headerRow = sheet.createRow(0);
            String[] headers = {"LESSON", "QUESTION", "ANSWER"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowNum = 1;
            for (SingleQuestionModel answer : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(answer.lesson());
                row.createCell(1).setCellValue(answer.question());
                row.createCell(2).setCellValue(answer.answer());
            }

            // Salvamento do arquivo
            fileFactory.validateAndCreateDirectory(PATH_ANSWER);
            try (FileOutputStream fileOut = new FileOutputStream(PATH_ANSWER + fileName + ".xlsx")) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
