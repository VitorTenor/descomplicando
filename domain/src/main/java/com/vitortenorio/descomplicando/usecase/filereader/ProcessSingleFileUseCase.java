package com.vitortenorio.descomplicando.usecase.filereader;

import com.vitortenorio.descomplicando.gateway.FileReaderGateway;

import javax.inject.Named;
import java.io.IOException;

@Named
public class ProcessSingleFileUseCase {
    private final FileReaderGateway fileReaderGateway;

    public ProcessSingleFileUseCase(FileReaderGateway fileReaderGateway) {
        this.fileReaderGateway = fileReaderGateway;
    }

    public void execute() throws IOException {
        fileReaderGateway.processSingleFile();
    }
}
