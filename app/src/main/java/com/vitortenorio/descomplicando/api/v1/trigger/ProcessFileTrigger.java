package com.vitortenorio.descomplicando.api.v1.trigger;

import com.vitortenorio.descomplicando.enums.FileType;
import com.vitortenorio.descomplicando.usecase.filereader.ProcessSingleFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ProcessFileTrigger {
    private final ProcessSingleFileUseCase processSingleFileUseCase;

    @PostConstruct
    public void processFile() {
        processSingleFileUseCase.execute(FileType.XLSX);
    }
}
