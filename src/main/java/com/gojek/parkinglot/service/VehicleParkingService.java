package com.gojek.parkinglot.service;

import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;
import java.util.List;

public interface VehicleParkingService extends AbstractParkingService {

  void createPartkingLot(final int noOflevels, final List<ParkingLevel> levels) throws ParkingLotException;

  VehicleParkingLocation parkVehicle(final Vehicle vehicle) throws ParkingLotException;

  boolean leaveSlot(final int level, final int slotNumber) throws ParkingLotException;

  List<ParkingLevel> getParkingLotStatus() throws ParkingLotException;

  List<String> getRegistrationNumbersWithColor(final String color) throws ParkingLotException;

  List<Integer> getSlotNumbersWithColor(final String color) throws ParkingLotException;

  int getSlotNumberWithRegNumber(final String registrationNumber) throws ParkingLotException;

}
