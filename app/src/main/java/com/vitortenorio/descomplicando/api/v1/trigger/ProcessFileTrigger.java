package com.vitortenorio.descomplicando.api.v1.trigger;

import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.usecase.filereader.ProcessSingleNodeFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ProcessFileTrigger {
    private final ProcessSingleNodeFileUseCase processSingleNodeFileUseCase;

    @PostConstruct
    public void processFile() {
        processSingleNodeFileUseCase.execute(FileType.JSON);
        processSingleNodeFileUseCase.execute(FileType.XLSX);
    }
}
