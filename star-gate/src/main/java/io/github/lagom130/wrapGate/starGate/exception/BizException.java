package io.github.lagom130.wrapGate.starGate.exception;

public class BizException extends RuntimeException{

  public BizException(String message, Throwable cause) {
    super(message, cause);
  }

  public BizException(String message) {
    super(message);
  }
}
