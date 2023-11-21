package com.vitortenorio.descomplicando.api.v1.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vitortenorio.descomplicando.api.v1.input.SingleFileInput;
import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.gateway.FileGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileClient implements FileGateway {

    private final AnswerClient answerClient;
    private final ObjectMapperUtil objectMapperUtil;
    private final SingleQuestionClient singleQuestionClient;

    @Value("${file.path.single}")
    private String PATH_SINGLE;

    @Value("${file.path.answer}")
    private String PATH_ANSWER;

    @Override
    public void processSingleFile() {
        File pasta = new File(PATH_SINGLE);
        File[] arquivos = pasta.listFiles();

        if (arquivos != null && arquivos.length > 0) {
            for (File arquivoJson : arquivos) {
                try {
                    SingleFileInput singleFileInput = objectMapperUtil.readValue(arquivoJson, SingleFileInput.class);
                    List<Integer> answerIds = answerClient.processTrueAnswer(singleFileInput.assertions().toAnswerEntityList());
                    List<QuestionAnswerEntity> questionAnswerEntityList = singleQuestionClient.processQuestionAndAnswer(singleFileInput.questions().toString(), answerIds);
                    gravarListaEmArquivoJson(questionAnswerEntityList, singleFileInput.lessonName().toUpperCase() + ".json", singleFileInput.disciplinaName().toUpperCase());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } else {
            System.out.println("Não há arquivos no diretório");
        }
    }

    private void gravarListaEmArquivoJson(List<QuestionAnswerEntity> lista, String caminhoArquivo, String folder) {
        String path = PATH_ANSWER + folder + "\\";
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
