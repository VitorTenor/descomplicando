package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.core.util.XlsxUtil;
import com.vitortenorio.descomplicando.infra.data.model.SingleQuestionModel;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class XlsxFactory {
    @Value("${file.path.answer}")
    private String PATH_ANSWER;
    private final FileDirectoryFactory fileDirectoryFactory;
    private static final String XLSX_EXTENSION = ".xlsx";

    public void createWorkbookSheet(List<SingleQuestionModel> values, String valueKey, Workbook workbook) {
            var sheet = workbook.createSheet(valueKey);

            createHeaderRow(sheet, workbook);
            createContentRow(sheet, values);
    }

    private void createContentRow(Sheet sheet, List<SingleQuestionModel> values) {
        int rowNum = 1;
        for (SingleQuestionModel value : values) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(value.lesson());
            row.createCell(1).setCellValue(value.question());
            row.createCell(2).setCellValue(value.answer());
        }
    }

    private void createHeaderRow(Sheet sheet, Workbook workbook) {
        CellStyle headerCellStyle = XlsxUtil.createBoldCellStyle(workbook);

        Row headerRow = sheet.createRow(0);
        var headers = List.of("LESSON", "QUESTION", "ANSWER");

        headers.forEach(header -> {
            Cell cell = headerRow.createCell(headers.indexOf(header));
            cell.setCellValue(header);
            cell.setCellStyle(headerCellStyle);
        });
    }

    public void saveFile(Workbook workbook) {
        fileDirectoryFactory.validateAndCreateDirectory(PATH_ANSWER);
        try (FileOutputStream fileOut = new FileOutputStream(PATH_ANSWER + "answers" + XLSX_EXTENSION)) {
            workbook.write(fileOut);
        } catch (Exception e) {
            throw new RuntimeException("Error to save workbook.");
        }
    }
}
