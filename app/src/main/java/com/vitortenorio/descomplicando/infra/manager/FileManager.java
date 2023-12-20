package com.vitortenorio.descomplicando.infra.manager;

import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

@Component
@RequiredArgsConstructor
public class FileManager {
    private final ObjectMapperUtil objectMapperUtil;

    public void write(File file, Object object, FileType fileType) {
        switch (fileType) {
            case JSON -> writeJson(file, object);
            case XLSX -> writeXlsx(file,(Workbook) object);
        }
    }

    private void writeXlsx(File file, Workbook workbook) {
        try {
            workbook.write(new FileOutputStream(file));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private void writeJson(File file, Object object) {
        objectMapperUtil.writeValueAsFile(file, object);
    }
}
