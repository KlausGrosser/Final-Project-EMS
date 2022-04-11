package finalproject.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ErrorResponse {
  private String message;
  private HttpStatus httpStatus;
  private ZonedDateTime timestamp;


  public ErrorResponse(String message,
                       HttpStatus httpStatus,
                       ZonedDateTime timestamp) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
  }

  public ErrorResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }


  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

// https://www.youtube.com/watch?v=PzK4ZXa2Tbc

