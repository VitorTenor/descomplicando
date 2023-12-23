package com.vitortenorio.descomplicando.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {
    private String title;
    private String detail;
    private Integer status;
    private String userMessage;
    private LocalDateTime timestamp;
    private String uri;
    private List<Object> objects;

    @Builder
    @Getter
    public static class Object {
        private String name;
        private String userMessage;
    }
}

