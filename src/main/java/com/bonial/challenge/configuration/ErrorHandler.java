package com.bonial.challenge.configuration;

import com.bonial.challenge.adapter.controller.exception.NotFoundException;
import com.bonial.challenge.adapter.controller.exception.ParametersException;
import com.bonial.challenge.core.exception.AdapterException;
import com.bonial.challenge.core.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final String X_B3_TRACE_ID = "X-B3-TraceId";
    private static final String X_B3_SPAN_ID = "X-B3-SpanId";
    private static final String PROD_PROFILE = "prod";
    private final HttpServletRequest httpServletRequest;


    public ErrorHandler(final HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiErrorResponse> handle(IllegalArgumentException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.BAD_REQUEST);
    }


    @ExceptionHandler({Throwable.class, UnsupportedOperationException.class})
    public ResponseEntity<ApiErrorResponse> handle(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex, ErrorCode.INTERNAL_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handle(MissingServletRequestParameterException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle(NotFoundException ex) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.NOT_FOUND, ex, ErrorCode.RESOURCE_NOT_FOUND_ERROR);
    }

    @ExceptionHandler(ParametersException.class)
    public ResponseEntity<ApiErrorResponse> handle(ParametersException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(AdapterException.class)
    public ResponseEntity<ApiErrorResponse> handle(AdapterException ex) {
        log.error(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.SERVICE_UNAVAILABLE, ex, ex.getCode());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handle(BusinessException ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex, ex.getCode());
    }


    private ResponseEntity<ApiErrorResponse> buildResponseError(HttpStatus httpStatus, Throwable ex, ErrorCode errorCode) {

        final var apiErrorResponse = ApiErrorResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .name(httpStatus.getReasonPhrase())
                .detail(String.format("%s: %s", ex.getClass().getCanonicalName(), ex.getMessage()))
                .status(httpStatus.value())
                .code(errorCode.value())
                .id(UUID.randomUUID().toString())
                .resource(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @Builder
    @NonNull
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class ApiErrorResponse {

        private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";

        @JsonProperty
        private String name;
        @JsonProperty
        private Integer status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
        private LocalDateTime timestamp;
        @JsonProperty
        private Integer code;
        @JsonProperty
        private String resource;
        @JsonProperty
        private String id;
        @JsonProperty
        private String detail;
    }

}
