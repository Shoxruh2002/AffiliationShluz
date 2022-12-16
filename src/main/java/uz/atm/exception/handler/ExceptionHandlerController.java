package uz.atm.exception.handler;

import jakarta.persistence.RollbackException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import uz.atm.dto.responses.AppErrorDto;
import uz.atm.dto.responses.DataDto;
import uz.atm.exception.messages.ApiMessages;

import java.sql.SQLException;


/**
 * Author: Khonimov Ulugbek
 * Date: 07/07/22
 * Time: 17:44
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler({ ListenerExecutionFailedException.class,
            NullPointerException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<DataDto<AppErrorDto>> handleBadRequestException(RuntimeException e) {
        return new ResponseEntity<>(new DataDto<>(new AppErrorDto(e.getMessage(), HttpStatus.BAD_REQUEST)), HttpStatus.OK);
    }

//    @ExceptionHandler({BadCredentialsException.class})
//    public ResponseEntity<DataDto<AppErrorDto>> handleTokenException(RuntimeException e) {
//        log.error(" error : {}, couse: [{}]", ApiMessages.UNAUTHORIZED, ExceptionUtils.getRootCauseMessage(e));
//        return new ResponseEntity<>(new DataDto<>(new AppErrorDto(e.getMessage(), HttpStatus.UNAUTHORIZED)), HttpStatus.OK);
//    }


//    @ExceptionHandler({InvalidTokenException.class, PermissionDeniedException.class})
//    public ResponseEntity<DataDto<AppErrorDto>> handleForbiddenException(RuntimeException e) {
//        log.error(" error : {}, couse: [{}]", ApiMessages.FORBIDDEN, ExceptionUtils.getRootCauseMessage(e));
//        return new ResponseEntity<>(new DataDto<>(new AppErrorDto(e.getMessage(), HttpStatus.FORBIDDEN)), HttpStatus.OK);
//    }
//
//    @ExceptionHandler({UsernameNotFoundException.class, ItemNotFoundException.class})
//    public ResponseEntity<DataDto<AppErrorDto>> handleNotFoundException(RuntimeException e) {
//        return new ResponseEntity<>(new DataDto<>(new AppErrorDto(e.getMessage(), HttpStatus.NOT_FOUND)), HttpStatus.OK);
//    }

    @ExceptionHandler({
            SQLException.class})
    public ResponseEntity<DataDto<AppErrorDto>> handleSqlException(RuntimeException e) {
        log.error(" error : {}, couse: [{}]", ApiMessages.INTERNAL_SERVER_ERROR, ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(new DataDto<>(new AppErrorDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)), HttpStatus.OK);
    }


//
//    @ExceptionHandler({JsonParserException.class, CuntomIOException.class})
//    public ResponseEntity<DataDto<AppErrorDto>> handleJsonParsingException(RuntimeException e) {
//        return new ResponseEntity<>(new DataDto<>(new AppErrorDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)), HttpStatus.OK);
//    }

    @ExceptionHandler({HttpServerErrorException.InternalServerError.class, RollbackException.class})
    public ResponseEntity<DataDto<AppErrorDto>> handleInternalServerErrorException(RuntimeException e) {
        log.error(" error : {}, couse: [{}]", ApiMessages.INTERNAL_SERVER_ERROR, ExceptionUtils.getRootCauseMessage(e));
        return new ResponseEntity<>(new DataDto<>(new AppErrorDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)), HttpStatus.OK);
    }
}
