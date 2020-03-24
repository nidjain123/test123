package com.gojek.parkinglot.exception;

/**
 * Exception class to handle all validation exceptions.
 */
public class DbValidationException extends Exception {

  private int errorCode;

  DbValidationException(final int errorCode, final String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
  }

  public DbValidationException(final String errorMessage) {
    super(errorMessage);
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(final int errorCode) {
    this.errorCode = errorCode;
  }
}
