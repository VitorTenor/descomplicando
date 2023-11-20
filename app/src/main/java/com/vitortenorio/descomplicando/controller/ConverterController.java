package com.vitortenorio.descomplicando.controller;

import com.vitortenorio.descomplicando.model.request.Answer;
import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.service.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/converter")
@RequiredArgsConstructor
public class ConverterController {

    private final ConverterService converterService;
    private final Logger LOGGER = Logger.getLogger(ConverterController.class.getName());



    @PostMapping("/findQuestionsByIds")
    public List<AnswerResponse> findQuestionsByIds(@RequestBody String questionJson,
                                                   @RequestParam(value = "assertions") List<Integer> answerIds) {

        LOGGER.info("Finding questions by ids");
        return converterService.processQuestionByAnswer(questionJson, answerIds);
    }
}
