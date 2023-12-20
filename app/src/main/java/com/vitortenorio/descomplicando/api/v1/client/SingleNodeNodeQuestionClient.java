package com.vitortenorio.descomplicando.api.v1.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.core.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.enums.NodeField;
import com.vitortenorio.descomplicando.gateway.SingleNodeQuestionGateway;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SingleNodeNodeQuestionClient implements SingleNodeQuestionGateway {
    private final JsonNodeUtil jsonNodeUtil;

    @Override
    public List<QuestionAnswerEntity> processQuestionAndAnswer(String question, List<Integer> answerIds) {

        var questionNodes = jsonNodeUtil.buildMainNode(question);
        var questionList = jsonNodeUtil.createListFromSingleNode(questionNodes);

        List<QuestionAnswerEntity> correctAnswerResponse = new ArrayList<>();
        findAndBuildQuestionsWithAnswers(answerIds, questionList, correctAnswerResponse);

        return correctAnswerResponse;
    }


    private void findAndBuildQuestionsWithAnswers(List<Integer> answerIds, List<JsonNode> questions,
                                                  List<QuestionAnswerEntity> correctAnswerResponse) {
        questions.stream()
                .parallel()
                .forEach(question -> buildAssertions(answerIds, question, correctAnswerResponse));
    }

    private void buildAssertions(List<Integer> answerIds, JsonNode question,
                                 List<QuestionAnswerEntity> correctAnswerResponse) {

        JsonNode cleanedQuestion = question.path(NodeField.QUESTION_BY_QUESTION_ID.getField());

        JsonNode assertionOptionsList = cleanedQuestion
                .path(NodeField.ASSERTIONS_BY_QUESTION_ID.getField())
                .path(NodeField.NODES.getField());

        matchAssertionsWithAnswerIds(answerIds, assertionOptionsList, cleanedQuestion, correctAnswerResponse);
    }

    private void matchAssertionsWithAnswerIds(List<Integer> answerIds, JsonNode assertionOptions,
                                              JsonNode question, List<QuestionAnswerEntity> correctAnswerResponse) {

        var assertionList = jsonNodeUtil.createListFromSingleNode(assertionOptions);

        assertionList.stream()
                .parallel()
                .forEach(assertion -> validateAssertion(answerIds, assertion, question, correctAnswerResponse));
    }

    private void validateAssertion(List<Integer> answerIds, JsonNode assertion,
                                   JsonNode question, List<QuestionAnswerEntity> correctAnswerResponse) {
        answerIds.stream()
                .filter(answerId -> assertion.toString().contains(answerId.toString()))
                .parallel()
                .forEach(answerId -> buildAnswer(question, assertion, answerId, correctAnswerResponse));
    }

    private void buildAnswer(JsonNode questionByQuestionId, JsonNode assertion,
                             Integer answerId, List<QuestionAnswerEntity> answerResponses) {

        final var questionText = jsonNodeUtil.getQuestion(questionByQuestionId).asText();
        final var answerText = jsonNodeUtil.getSingleAnswer(assertion).asText();

        final var cleanQuestion = Jsoup.parse(questionText).text();

        var answerResponse = new QuestionAnswerEntity(cleanQuestion, answerText, answerId);
        answerResponses.add(answerResponse);
    }
}
