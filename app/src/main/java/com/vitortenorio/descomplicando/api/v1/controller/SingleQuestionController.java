package com.vitortenorio.descomplicando.api.v1.controller;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.usecase.singlequestion.ProcessSingleQuestionAndAnswerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/single-question")
public class SingleQuestionController {
    private final ProcessSingleQuestionAndAnswerUseCase processSingleQuestionAndAnswerUseCase;

    @PostMapping("/process")
    public List<QuestionAnswerEntity> process(@RequestBody String question,
                                              @RequestParam(name = "answerIds") List<Integer> answerIds) {
        return processSingleQuestionAndAnswerUseCase.execute(question, answerIds);
    }
}
