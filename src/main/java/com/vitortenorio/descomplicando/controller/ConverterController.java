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

    @PostMapping(value = "/filterTrue", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> filterTrue(@RequestBody Answer assertt) {
        LOGGER.info("Filtering true assertions");
        return converterService.filterTrue(assertt);
    }

    @PostMapping("/findQuestionsByJson")
    public List<AnswerResponse> findQuestionsByJson(@RequestBody String questionJson,
                                                    @RequestParam(value = "startIndex") Integer startIndex,
                                                    @RequestParam(value = "assertions") String answerJson) {

        LOGGER.info("Finding questions by json");
        return converterService.processQuestionByAnswer(questionJson, startIndex, answerJson);
    }

    @PostMapping("/findQuestionsByIds")
    public List<AnswerResponse> findQuestionsByIds(@RequestBody String questionJson,
                                                   @RequestParam(value = "startIndex") Integer startIndex,
                                                   @RequestParam(value = "assertions") List<Integer> answerIds) {

        LOGGER.info("Finding questions by ids");
        return converterService.processQuestionByAnswer(questionJson, startIndex, answerIds);
    }

}
