package com.g12.handler;

import com.g12.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException ex) {
        log.error("运行时异常: {}", ex.getMessage());
        return Result.error(ex.getMessage());
    }


}