package com.vitortenorio.descomplicando.filemanager.service;

import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.exception.BusinessException;
import com.vitortenorio.descomplicando.filemanager.util.ObjectMapperAbstract;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

@Service
@RequiredArgsConstructor
public class FileWriterService extends ObjectMapperAbstract {

    public void write(final File file, final Object object, final FileType fileType) {
        switch (fileType) {
            case JSON -> this.writeJson(file, object);
            case XLSX -> this.writeXlsx(file,(Workbook) object);
        }
    }

    private void writeXlsx(final File file, final Workbook workbook) {
        try {
            workbook.write(new FileOutputStream(file));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private void writeJson(final File file, final Object object) {
        super.writeValueAsFile(file, object);
    }
}
