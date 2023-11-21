package com.vitortenorio.descomplicando.api.v1.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vitortenorio.descomplicando.api.v1.input.SingleFileInput;
import com.vitortenorio.descomplicando.api.v1.service.SingleFileService;
import com.vitortenorio.descomplicando.core.factory.FileFactory;
import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import com.vitortenorio.descomplicando.entity.AnswerEntity;
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

    private final FileFactory fileFactory;
    private final AnswerClient answerClient;
    private final SingleFileService singleFileService;
    private final ObjectMapperUtil objectMapperUtil;
    private final SingleQuestionClient singleQuestionClient;

    @Value("${file.path.single}")
    private String PATH_SINGLE;

    @Value("${file.path.answer}")
    private String PATH_ANSWER;

    @Value("${file.type}")
    private String FILE_TYPE;

    @Override
    public void processSingleFile() {
        File pasta = fileFactory.createFile(PATH_SINGLE);
        File[] arquivos = pasta.listFiles();

        if (arquivos != null && arquivos.length > 0) {

            for (File arquivoJson : arquivos) {
                SingleFileInput singleFileInput = objectMapperUtil.readValue(arquivoJson, SingleFileInput.class);
                List<Integer> answerIds = singleFileService.processAnswers(singleFileInput.assertions());
                List<QuestionAnswerEntity> questionAnswer = singleFileService.processQuestionAndAnswer(singleFileInput.questions(), answerIds);

                String filePath = singleFileInput.lessonName().toUpperCase() + FILE_TYPE ;
                String subjectName = PATH_ANSWER + singleFileInput.subjectName().toUpperCase() + "\\";
                saveSingleInJsonFile(questionAnswer, filePath, subjectName);
            }

        } else {
            System.out.println("Não há arquivos no diretório");
        }
    }

    private void saveSingleInJsonFile(List<QuestionAnswerEntity> value, String filePath, String folder) {
        fileFactory.validateAndCreateDirectory(folder);
        File file = fileFactory.createFile(folder + filePath);
        objectMapperUtil.writeValueAsFile(file, value);
    }




}
