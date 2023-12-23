package com.vitortenorio.descomplicando.api.exceptionhandler;

import com.vitortenorio.descomplicando.enums.ProblemType;
import com.vitortenorio.descomplicando.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        final var UNEXPECTED_ERROR = "Unexpected error";
        final var status = HttpStatus.INTERNAL_SERVER_ERROR;
        final var problemType = ProblemType.SYSTEM_ERROR;

        var problem = createProblem(status, problemType, ex.getMessage());
        problem.setDetail(UNEXPECTED_ERROR);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {
        final var status = HttpStatus.BAD_REQUEST;
        final var problemType = ProblemType.BUSINESS_ERROR;
        final var problem = createProblem(status, problemType, ex.getMessage());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(body, headers, status);
    }

    private Problem createProblem(final HttpStatus status, final ProblemType problemType, final String detail) {
        return Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .uri(problemType.uri())
                .title(problemType.title())
                .userMessage(detail)
                .detail(detail)
                .build();
    }
}
