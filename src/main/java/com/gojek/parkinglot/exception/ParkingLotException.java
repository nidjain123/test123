package com.gojek.parkinglot.exception;

public class ParkingLotException extends Exception {

  private int errorCode;

  ParkingLotException(final int errorCode, final String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
  }

  public ParkingLotException(final String errorMessage) {
    super(errorMessage);
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(final int errorCode) {
    this.errorCode = errorCode;
  }
}
