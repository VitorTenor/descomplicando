package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.core.util.XlsxUtil;
import com.vitortenorio.descomplicando.infrastructure.database.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.infrastructure.filemanager.FileManager;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class XlsxFactory {
    @Value("${file.path.answer}")
    private String PATH_ANSWER;
    private final FileManager fileManager;

    public void createWorkbookSheet(String valueKey, List<SingleQuestionModel> values, Workbook workbook) {
        var sheet = workbook.createSheet(valueKey);

        createHeaderRow(sheet, workbook);
        createContentRow(sheet, values);
    }

    private void createContentRow(Sheet sheet, List<SingleQuestionModel> values) {
        var rowNum = new AtomicInteger(1);
        values.forEach(value -> addContent(value, sheet, rowNum));
    }

    private void addContent(final SingleQuestionModel value, Sheet sheet, AtomicInteger rowNum) {
        var row = sheet.createRow(rowNum.getAndIncrement());
        row.createCell(0).setCellValue(value.lesson());
        row.createCell(1).setCellValue(value.question());
        row.createCell(2).setCellValue(value.answer());
    }

    private void createHeaderRow(Sheet sheet, Workbook workbook) {
        var headerCellStyle = XlsxUtil.createBoldCellStyle(workbook);

        Row headerRow = sheet.createRow(0);
        var headers = List.of("LESSON", "QUESTION", "ANSWER");

        headers.forEach(header -> addHeader(headerRow, headerCellStyle, header, headers.indexOf(header)));
    }

    private void addHeader(Row headerRow, CellStyle headerCellStyle, String header, int index) {
        Cell cell = headerRow.createCell(index);
        cell.setCellValue(header);
        cell.setCellStyle(headerCellStyle);
    }

    public void saveFile(Workbook workbook) {
        fileManager.validateAndCreateDirectory(PATH_ANSWER);

        final var directoryWithFile = STR."\{ PATH_ANSWER }answers\{ FileType.XLSX.extension() }";
        var file = new File(directoryWithFile);
        fileManager.write(file, workbook, FileType.XLSX);
    }
}
