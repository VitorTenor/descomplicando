package com.vitortenorio.descomplicando.api.v1.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vitortenorio.descomplicando.api.v1.input.SingleFileInput;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.gateway.FileReaderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileReaderClient implements FileReaderGateway {

    private final AnswerClient answerClient;
    private final SingleQuestionClient singleQuestionClient;

    @Override
    public void processSingleFile() {
        ObjectMapper objectMapper = new ObjectMapper();

        String diretorio = "C:\\DESCOMPLICANDO\\SIMPLE";

        File pasta = new File(diretorio);
        File[] arquivos = pasta.listFiles();

        if (arquivos != null && arquivos.length > 0) {
            for (File arquivoJson : arquivos) {
                try {
                    SingleFileInput singleFileInput = objectMapper.readValue(arquivoJson, SingleFileInput.class);
                    List<Integer> answerIds = answerClient.processTrueAnswer(singleFileInput.assertions().toAnswerEntityList());
                    List<QuestionAnswerEntity> questionAnswerEntityList = singleQuestionClient.processQuestionAndAnswer(singleFileInput.questions().toString(), answerIds);
                    gravarListaEmArquivoJson(questionAnswerEntityList, singleFileInput.lessonName().toUpperCase() + ".json", singleFileInput.disciplinaName().toUpperCase());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void gravarListaEmArquivoJson(List<QuestionAnswerEntity> lista, String caminhoArquivo, String folder) {
        String path = "C:\\DESCOMPLICANDO\\ANSWER\\" + folder + "\\";
        Path pathFolder = Path.of(path);
        if (!Files.exists(pathFolder)) {
            try {
                Files.createDirectory(pathFolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(path + caminhoArquivo), lista);
            System.out.println("Lista gravada com sucesso em: " + path + caminhoArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
