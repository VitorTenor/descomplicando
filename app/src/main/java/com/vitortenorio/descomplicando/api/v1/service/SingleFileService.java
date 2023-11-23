package com.vitortenorio.descomplicando.api.v1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.api.v1.client.AnswerClient;
import com.vitortenorio.descomplicando.api.v1.client.SingleQuestionClient;
import com.vitortenorio.descomplicando.api.v1.input.SingleFileInput;
import com.vitortenorio.descomplicando.api.v1.request.AnswerRequest;
import com.vitortenorio.descomplicando.core.factory.FileFactory;
import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import com.vitortenorio.descomplicando.entity.AnswerEntity;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleFileService {
    private final FileFactory fileFactory;
    private final AnswerClient answerClient;
    private final ObjectMapperUtil objectMapperUtil;
    private final SingleQuestionClient singleQuestionClient;

    @Value("${file.path.answer}")
    private String PATH_ANSWER;

    @Value("${file.type}")
    private String FILE_TYPE;

    public void processAllSingleFile(File jsonFile) {
        SingleFileInput singleFileInput = readSingleFile(jsonFile);
        List<Integer> answerIds = processAnswers(singleFileInput.assertions());
        List<QuestionAnswerEntity> questionAnswers = processQuestionAndAnswer(singleFileInput.questions(), answerIds);

        String filePath = singleFileInput.lessonName().toUpperCase() + FILE_TYPE;
        String subjectName = PATH_ANSWER + singleFileInput.subjectName().toUpperCase() + "\\";
        saveSingleInJsonFile(questionAnswers, filePath, subjectName);
    }

    private SingleFileInput readSingleFile(File jsonFile) {
        return objectMapperUtil.readValue(jsonFile, SingleFileInput.class);
    }

    private List<Integer> processAnswers(AnswerRequest assertions) {
        List<AnswerEntity> answerEntityList = assertions.toAnswerEntityList();
        return answerClient.processTrueAnswer(answerEntityList);
    }

    private List<QuestionAnswerEntity> processQuestionAndAnswer(JsonNode questions, List<Integer> answerIds) {
        return singleQuestionClient.processQuestionAndAnswer(questions.toString(), answerIds);
    }

    private void saveSingleInJsonFile(List<QuestionAnswerEntity> values, String filePath, String folder) {
        fileFactory.validateAndCreateDirectory(folder);
        File file = fileFactory.mountFile(folder + filePath);
        objectMapperUtil.writeValueAsFile(file, values);
    }
}
