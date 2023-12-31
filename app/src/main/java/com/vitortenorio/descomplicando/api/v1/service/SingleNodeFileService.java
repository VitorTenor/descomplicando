package com.vitortenorio.descomplicando.api.v1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.api.v1.client.AnswerClient;
import com.vitortenorio.descomplicando.api.v1.client.SingleNodeQuestionClient;
import com.vitortenorio.descomplicando.api.v1.input.SingleFileInput;
import com.vitortenorio.descomplicando.api.v1.input.AnswerInput;
import com.vitortenorio.descomplicando.core.util.StringUtil;
import com.vitortenorio.descomplicando.infrastructure.database.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.infrastructure.database.repository.impl.SingleQuestionRepositoryImpl;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.infrastructure.filemanager.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleNodeFileService {
    private final AnswerClient answerClient;
    private final FileManager fileManager;
    private final SingleQuestionRepositoryImpl singleQuestionDataRepository;
    private final SingleNodeQuestionClient singleNodeQuestionClient;

    public void processSingleFile(final File jsonFile) {
        var singleFileInput = fileManager.readValue(jsonFile, SingleFileInput.class);

        var questionAnswers = this.processQuestionAndAnswerEntity(singleFileInput);

        final var lessonName = StringUtil.divideAndCleanWord(singleFileInput.lessonName()).toUpperCase();
        final var subjectName = StringUtil.divideAndCleanWord(singleFileInput.subjectName()).toUpperCase();

        this.saveInData(questionAnswers, subjectName, lessonName);
    }

    private void saveInData(final List<QuestionAnswerEntity> questionAnswers, final String subjectName,
                            final String lessonName) {

        final var data = questionAnswers.stream()
                .parallel()
                .map(questionAnswerEntity ->
                        SingleQuestionModel.fromQuestionAnswerEntity(questionAnswerEntity, lessonName))
                .toList();

        singleQuestionDataRepository.addOrCreate(subjectName, data);
    }

    private List<QuestionAnswerEntity> processQuestionAndAnswerEntity(SingleFileInput singleFileInput) {
        var answerIds = this.processAnswers(singleFileInput.assertions());
        var questions = singleFileInput.questions();

        return this.processQuestionAndAnswer(questions, answerIds);
    }

    private List<Integer> processAnswers(AnswerInput assertions) {
        var answerEntityList = assertions.toAnswerEntityList();
        return answerClient.processTrueAnswer(answerEntityList);
    }

    private List<QuestionAnswerEntity> processQuestionAndAnswer(JsonNode questions, List<Integer> answerIds) {
        return singleNodeQuestionClient.processQuestionAndAnswer(questions.toString(), answerIds);
    }
}
