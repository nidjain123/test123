package com.gojek.parkinglot.service;

import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;
import java.util.List;

/**
 * interface responsible for handiling business logic of parking.
 */
public interface VehicleParkingService extends AbstractParkingService {

  /**
   * Creates Parking lot with given levels data.
   *
   * @param levels levels
   * @throws ParkingLotException exception is thrown when parking lot is already created.
   */
  void createPartkingLot(final List<ParkingLevel> levels) throws ParkingLotException, DbValidationException;

  /**
   * Method to park a vehicle.
   *
   * @param vehicle vehicle object
   * @return parking location of vehicle
   * @throws ParkingLotException exception is thrown when parking is full.
   */
  VehicleParkingLocation parkVehicle(final Vehicle vehicle) throws ParkingLotException, DbValidationException;

  /**
   * Method to unpark a vehicle.
   *
   * @param level level of the vehicle
   * @param slotNumber slot of the vehicle
   * @return true/false
   * @throws ParkingLotException when an invalid input/slot number is given
   */
  boolean leaveSlot(final int level, final int slotNumber) throws ParkingLotException, DbValidationException;

  /**
   * Returns current status of the parking lot.
   *
   * @return all occupied slot list
   * @throws ParkingLotException when an lot is empty
   */
  List<ParkingLevel> getParkingLotStatus() throws ParkingLotException, DbValidationException;

  /**
   * Returns all vehicle's registration number according to color.
   *
   * @param color input color
   * @throws ParkingLotException when an lot is empty
   * @returnlist list of regNo
   */
  List<String> getRegistrationNumbersWithColor(final String color) throws ParkingLotException, DbValidationException;

  /**
   * Returns all vehicle's slot number according to color.
   *
   * @param color input color
   * @returnlist list of slot number
   */
  List<Integer> getSlotNumbersWithColor(final String color) throws ParkingLotException, DbValidationException;

  /**
   * Returns vehicle's slot number according to regNumber.
   *
   * @param registrationNumber input regNumber
   * @returnlist list of regNo
   */
  int getSlotNumberWithRegNumber(final String registrationNumber) throws ParkingLotException, DbValidationException;

}
