package com.vitortenorio.descomplicando.usecase.filereader;

import com.vitortenorio.descomplicando.gateway.FileGateway;

import javax.inject.Named;

@Named
public class ProcessSingleFileUseCase {
    private final FileGateway fileGateway;

    public ProcessSingleFileUseCase(FileGateway fileGateway) {
        this.fileGateway = fileGateway;
    }

    public void execute() {
        fileGateway.processSingleFile();
    }
}
