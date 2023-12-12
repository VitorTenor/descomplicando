package com.vitortenorio.descomplicando.api.v1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.api.v1.client.AnswerClient;
import com.vitortenorio.descomplicando.api.v1.client.SingleQuestionClient;
import com.vitortenorio.descomplicando.api.v1.input.SingleFileInput;
import com.vitortenorio.descomplicando.api.v1.request.AnswerRequest;
import com.vitortenorio.descomplicando.core.factory.JsonFactory;
import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import com.vitortenorio.descomplicando.core.util.StringUtil;
import com.vitortenorio.descomplicando.entity.AnswerEntity;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.infra.data.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.infra.data.service.SingleQuestionData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleFileService {
    private final AnswerClient answerClient;
    private final ObjectMapperUtil objectMapperUtil;
    private final SingleQuestionClient singleQuestionClient;
    private final JsonFactory jsonFactory;
    private final SingleQuestionData singleQuestionData;

    public void processAllSingleFile(File jsonFile) {
        SingleFileInput singleFileInput = readSingleFile(jsonFile);
        List<Integer> answerIds = processAnswers(singleFileInput.assertions());
        List<QuestionAnswerEntity> questionAnswers = processQuestionAndAnswer(singleFileInput.questions(), answerIds);

        String fileName = StringUtil.divideAndCleanWord(singleFileInput.lessonName().toUpperCase());

        String folderName = StringUtil.divideAndCleanWord(singleFileInput.subjectName().toUpperCase()) ; //+ "\\";

//        jsonFactory.createAndSaveFile(questionAnswers, folderName, fileName);

        saveInData(questionAnswers, folderName, fileName);
    }

    private void saveInData(List<QuestionAnswerEntity> questionAnswers, String folderName, String fileName) {
        List<SingleQuestionModel> singleQuestionModelList = questionAnswers.stream().map(
                questionAnswerEntity -> SingleQuestionModel.fromQuestionAnswerEntity(questionAnswerEntity, fileName)).toList();

        singleQuestionData.addSingleQuestionModel(folderName, singleQuestionModelList);
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
}
