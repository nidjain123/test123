package com.gojek.parkinglot.validator;

import com.gojek.parkinglot.constant.ErrorConstants;
import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.model.ParkingLevel;
import java.util.List;

/**
 * A validator interface for db operation.
 */
public class DbValidatorImpl implements DbValidator {


  @Override
  public void checkParkingLotExists(List<ParkingLevel> parkingLot) throws DbValidationException {
    if (parkingLot == null || parkingLot.size() == 0) {
      throw new DbValidationException(ErrorConstants.PARKING_LOT_DOESNT_EXISTS_ERR_MSG);
    }
  }

  @Override
  public boolean checkParkingLotNotExists(List<ParkingLevel> parkingLot) throws DbValidationException {
    if (parkingLot.size() == 0) {
      return true;
    } else {
      throw new DbValidationException(ErrorConstants.PARKING_ALREADY_EXIST_ERR_MSG);
    }
  }
}
