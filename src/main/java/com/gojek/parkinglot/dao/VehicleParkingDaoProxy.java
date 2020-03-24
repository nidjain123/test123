package com.gojek.parkinglot.dao;

import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;
import java.util.List;

/**
 * Inteface which is responsible for interaction with data.
 */
public interface VehicleParkingDaoProxy {

  /**
   * Creates Parking lot with given levels data.
   *
   * @param levels List of levels populated with slots.
   */
  void createPartkingLot(final List<ParkingLevel> levels) throws DbValidationException;

  /**
   * Method to park a vehicle.
   *
   * @param vehicle vehicle object
   * @return parking location of vehicle
   */
  VehicleParkingLocation parkVehicle(final Vehicle vehicle) throws DbValidationException;

  /**
   * Method to unpark a vehicle.
   *
   * @param level level of the vehicle
   * @param slotNumber slot of the vehicle
   * @return true/false
   */
  boolean leaveSlot(final int level, final int slotNumber) throws DbValidationException;

  /**
   * Returns current status of the parking lot.
   *
   * @return all occupied slot list
   */
  List<ParkingLevel> getParkingLotStatus() throws DbValidationException;

  /**
   * Returns all vehicle's registration number according to color.
   *
   * @param color input color
   * @returnlist list of regNo
   */
  List<String> getRegistrationNumbersWithColor(final String color) throws DbValidationException;

  /**
   * Returns all vehicle's slot number according to color.
   *
   * @param color input color
   * @returnlist list of slot number
   */
  List<Integer> getSlotNumbersWithColor(final String color) throws DbValidationException;

  /**
   * Returns vehicle's slot number according to regNumber.
   *
   * @param registrationNumber input regNumber
   * @returnlist list of regNo
   */
  int getSlotNumberWithRegNumber(final String registrationNumber) throws DbValidationException;

}
