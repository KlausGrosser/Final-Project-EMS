package finalproject.exception;


public class ApiRequestException extends RuntimeException {

  /** CUSTOM EXCEPTION
   * This is our custom exception that we can throw throughout pur application.
   * Constructor 1 passed the actual message
   * Constructor 2 passed the actual throwable and the message
   */

  public ApiRequestException(String message) {
    super(message);
  }

  public ApiRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}