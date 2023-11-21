package com.vitortenorio.descomplicando.api.v1.client;

import com.vitortenorio.descomplicando.api.v1.service.SingleFileService;
import com.vitortenorio.descomplicando.core.factory.FileFactory;
import com.vitortenorio.descomplicando.gateway.FileGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class FileClient implements FileGateway {

    private final FileFactory fileFactory;
    private final SingleFileService singleFileService;

    @Value("${file.path.single}")
    private String PATH_SINGLE;

    @Override
    public void processSingleFile() {
        File folder = fileFactory.mountFile(PATH_SINGLE);
        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            for (File arquivoJson : files) {
                singleFileService.processAllSingleFile(arquivoJson);
            }
        } else {
            System.out.println("Não há arquivos no diretório");
        }
    }

}
