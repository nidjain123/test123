package com.gojek.parkinglot.service;

import com.gojek.parkinglot.constant.ErrorConstants;
import com.gojek.parkinglot.dao.VehicleParkingDaoProxy;
import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;
import java.util.List;

/**
 * Implemenation class for vehicle service interface.
 */
public class VehicleParkingServiceImpl implements VehicleParkingService {

  private VehicleParkingDaoProxy vehicleParkingDaoProxy;

  public VehicleParkingServiceImpl(final VehicleParkingDaoProxy vehicleParkingDaoProxy) {
    this.vehicleParkingDaoProxy = vehicleParkingDaoProxy;
  }


  public void createPartkingLot(final List<ParkingLevel> levels) throws ParkingLotException, DbValidationException {
    //Assuming all levels will be added during initialization.
    vehicleParkingDaoProxy.createPartkingLot(levels);
  }

  public VehicleParkingLocation parkVehicle(Vehicle vehicle) throws ParkingLotException, DbValidationException {
    final VehicleParkingLocation vehicleParkingLocation = vehicleParkingDaoProxy.parkVehicle(vehicle);
    if (vehicleParkingLocation == null) {
      throw new ParkingLotException(ErrorConstants.PARKING_LOT_FULL_ERR_MSG);
    } else {
      return vehicleParkingLocation;
    }
  }

  public boolean leaveSlot(final int level, final int slotNumber) throws ParkingLotException, DbValidationException {
    if (!vehicleParkingDaoProxy.leaveSlot(level, slotNumber)) {
      throw new ParkingLotException(String.format(ErrorConstants.INVALID_VALUE_ERR_MSG, "slotNumber"));
    }
    return true;
  }

  public List<ParkingLevel> getParkingLotStatus() throws ParkingLotException, DbValidationException {
    final List<ParkingLevel> parkingLevelData = vehicleParkingDaoProxy.getParkingLotStatus();
    if (parkingLevelData.isEmpty()) {
      throw new ParkingLotException(ErrorConstants.PARKING_LOT_EMPTY_ERR_MSG);
    }
    return parkingLevelData;
  }

  public List<String> getRegistrationNumbersWithColor(final String color)
      throws ParkingLotException, DbValidationException {
    final List<String> regNumberList = vehicleParkingDaoProxy.getRegistrationNumbersWithColor(color);
    if (regNumberList.isEmpty()) {
      throw new ParkingLotException(ErrorConstants.NOT_FOUND_ERR_MSG);
    }
    return regNumberList;
  }

  public List<Integer> getSlotNumbersWithColor(final String color) throws ParkingLotException, DbValidationException {
    final List<Integer> slotNumberList = vehicleParkingDaoProxy.getSlotNumbersWithColor(color);
    if (slotNumberList.isEmpty()) {
      throw new ParkingLotException(ErrorConstants.NOT_FOUND_ERR_MSG);
    }
    return slotNumberList;
  }

  public int getSlotNumberWithRegNumber(final String registrationNumber)
      throws ParkingLotException, DbValidationException {
    final int slotNumber = vehicleParkingDaoProxy.getSlotNumberWithRegNumber(registrationNumber);
    if (slotNumber == -1) {
      throw new ParkingLotException(ErrorConstants.NOT_FOUND_ERR_MSG);
    }
    return slotNumber;
  }
}
