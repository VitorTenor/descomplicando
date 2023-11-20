package com.vitortenorio.descomplicando.api.v1.controller;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.usecase.multiplequestion.ProcessMultipleQuestionAndAnswerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/multiple-question")
public class MultipleQuestionController {
    private final ProcessMultipleQuestionAndAnswerUseCase processMultipleQuestionAndAnswerUseCase;

    @PostMapping("/process")
    public List<QuestionAnswerEntity> process(@RequestBody String question,
                                              @RequestParam(name = "answerIds") List<Integer> answerIds) {
        return processMultipleQuestionAndAnswerUseCase.execute(question, answerIds);
    }
}
