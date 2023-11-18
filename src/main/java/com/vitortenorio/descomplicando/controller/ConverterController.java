package com.vitortenorio.descomplicando.controller;

import com.vitortenorio.descomplicando.model.request.Assert;
import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.service.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/converter")
@RequiredArgsConstructor
public class ConverterController {

    private final ConverterService converterService;

    @PostMapping(value = "/filterTrue", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> filterTrue(@RequestBody Assert assertt) {
        return converterService.filterTrue(assertt);
    }

    @PostMapping("/findQuestionsByJson")
    public List<AnswerResponse> findQuestionsByJson(@RequestBody String json,
                                                    @RequestParam(value = "startIndex") Integer startIndex,
                                                    @RequestParam(value = "assertions") String jsonAssert) {

        return converterService.processNodes(json, startIndex, jsonAssert);
    }

    @PostMapping("/findQuestionsByIds")
    public List<AnswerResponse> findQuestionsByIds(@RequestBody String json,
                                                   @RequestParam(value = "startIndex") Integer startIndex,
                                                   @RequestParam(value = "assertions") List<Integer> filterTrue) {

        return converterService.processNodes(json, startIndex, filterTrue);
    }

}
