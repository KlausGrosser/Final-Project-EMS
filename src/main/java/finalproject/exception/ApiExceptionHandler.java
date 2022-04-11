package finalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The purpose of this class is to handle custom exception or even handle existing exceptions,
 * but then can customize the way we throw the actual error to the client.
 */

@ControllerAdvice
public class ApiExceptionHandler {

@ExceptionHandler(value = {ApiRequestException.class})
  public ResponseEntity<Object>handleApiRequestException(ApiRequestException e){

    //1.Create payload containing exception details
    //This object contains the actual payload information
    HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    ErrorResponse errorResponse = new ErrorResponse(
            e.getMessage(),
            badRequest,
            ZonedDateTime.now(ZoneId.of("Z"))
    );
    //2.Return response entity
    return new ResponseEntity<>(errorResponse, badRequest);

  }
}
