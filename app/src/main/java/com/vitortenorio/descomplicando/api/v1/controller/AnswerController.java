package com.vitortenorio.descomplicando.api.v1.controller;

import com.vitortenorio.descomplicando.api.v1.request.AnswerRequest;
import com.vitortenorio.descomplicando.usecase.answer.ProcessTrueAnswerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/answer")
public class AnswerController {
    private final ProcessTrueAnswerUseCase processTrueAnswerUseCase;

    @PostMapping("/process")
    public List<Integer> process(@RequestBody AnswerRequest answer) {
        return processTrueAnswerUseCase.execute(answer.data());
    }
}
