package com.gojek.parkinglot.validator;

import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.model.ParkingLevel;
import java.util.List;

/**
 * A validator interface for db operation.
 */
public interface DbValidator {

  /**
   * Check that a parking lot exists or not.
   */
  void checkParkingLotExists(final List<ParkingLevel> parkingLot) throws DbValidationException;

  /**
   * Check that a parking lot not exists.
   */
  boolean checkParkingLotNotExists(final List<ParkingLevel> parkingLot) throws DbValidationException;
}
