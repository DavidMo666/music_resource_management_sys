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

    // 1. 捕获 SQL 语法错误异常（如列名不存在）
    @ExceptionHandler(BadSqlGrammarException.class)
    public Result<String> handleBadSqlGrammarException(BadSqlGrammarException e) {
        log.error("SQL语法错误: ", e);
        return Result.error("SQL语法错误或不存在该对象，请检查输入值");
    }

}