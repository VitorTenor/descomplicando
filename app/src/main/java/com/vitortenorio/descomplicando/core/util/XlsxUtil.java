package com.vitortenorio.descomplicando.core.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class XlsxUtil {
    public static Font createBoldFont(final Workbook workbook) {
        final var font = workbook.createFont();
        font.setBold(true);
        return font;
    }

    public static CellStyle createBoldCellStyle(final Workbook workbook) {
        final var boldFont = createBoldFont(workbook);
        final var cellStyle = workbook.createCellStyle();
        cellStyle.setFont(boldFont);
        return cellStyle;
    }
}
