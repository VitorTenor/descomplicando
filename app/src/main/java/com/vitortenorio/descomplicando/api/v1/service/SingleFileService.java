package com.vitortenorio.descomplicando.api.v1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.api.v1.client.AnswerClient;
import com.vitortenorio.descomplicando.api.v1.client.SingleNodeQuestionClient;
import com.vitortenorio.descomplicando.api.v1.input.SingleFileInput;
import com.vitortenorio.descomplicando.api.v1.request.AnswerRequest;
import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import com.vitortenorio.descomplicando.core.util.StringUtil;
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
    private final SingleQuestionData singleQuestionData;
    private final SingleNodeQuestionClient singleNodeQuestionClient;

    public void processSingleFile(final File jsonFile) {
        var singleFileInput = objectMapperUtil.readValue(jsonFile, SingleFileInput.class);

        var questionAnswers = processQuestionAndAnswerEntity(singleFileInput);

        String lessonName = StringUtil.divideAndCleanWord(singleFileInput.lessonName()).toUpperCase();
        String subjectName = StringUtil.divideAndCleanWord(singleFileInput.subjectName()).toUpperCase();

        saveInData(questionAnswers, subjectName, lessonName);
    }

    private void saveInData(final List<QuestionAnswerEntity> questionAnswers, final String subjectName,
                            final String lessonName) {

        final var data = questionAnswers.stream().parallel().map(
                questionAnswerEntity -> SingleQuestionModel.fromQuestionAnswerEntity(questionAnswerEntity, lessonName)
        ).toList();

        singleQuestionData.addOrCreate(subjectName, data);
    }

    private List<QuestionAnswerEntity> processQuestionAndAnswerEntity(SingleFileInput singleFileInput) {
        var answerIds = processAnswers(singleFileInput.assertions());
        var questions = singleFileInput.questions();

        return processQuestionAndAnswer(questions, answerIds);
    }

    private List<Integer> processAnswers(AnswerRequest assertions) {
        var answerEntityList = assertions.toAnswerEntityList();
        return answerClient.processTrueAnswer(answerEntityList);
    }

    private List<QuestionAnswerEntity> processQuestionAndAnswer(JsonNode questions, List<Integer> answerIds) {
        return singleNodeQuestionClient.processQuestionAndAnswer(questions.toString(), answerIds);
    }
}
