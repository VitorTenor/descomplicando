package com.vitortenorio.descomplicando.api.v1.trigger;

import com.vitortenorio.descomplicando.usecase.filereader.ProcessSingleFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ProcessFilesTrigger {
    private final ProcessSingleFileUseCase processSingleFileUseCase;

    @PostConstruct
    public void processFile() throws IOException {
        processSingleFileUseCase.execute();
    }
}
