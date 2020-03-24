package com.gojek.parkinglot.dao;

import com.gojek.parkinglot.constant.ParkingSlotType;
import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.ParkingSlot;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;
import com.gojek.parkinglot.validator.DbValidator;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class of VehicleParkingDaoProxy.
 */
public class VehicleParkingDaoProxyImpl implements VehicleParkingDaoProxy {

  private List<ParkingLevel> parkingLot;
  private DbValidator dbValidator;

  public VehicleParkingDaoProxyImpl(final List<ParkingLevel> parkingLot, final DbValidator dbValidator) {
    this.parkingLot = parkingLot;
    this.dbValidator = dbValidator;
  }


  @Override
  public void createPartkingLot(final List<ParkingLevel> levels) throws DbValidationException {
    if (parkingLot != null) {
      if (dbValidator.checkParkingLotNotExists(parkingLot)) {
        parkingLot.addAll(levels);
      }
    }
  }

  @Override
  public VehicleParkingLocation parkVehicle(final Vehicle vehicle) throws DbValidationException {

    dbValidator.checkParkingLotExists(parkingLot);

    VehicleParkingLocation parkingLocation = null;

    for (int level = 0; level < parkingLot.size(); level++) {
      for (int slot = 0; slot < parkingLot.get(level).getSlots().size(); slot++) {
        ParkingSlot parkingSlot = parkingLot.get(level).getSlots().get(slot);
        if (isVehicleParkable(vehicle, parkingSlot)) {
          parkingLocation = VehicleParkingLocation.builder().level(level).slot(parkingSlot.getSlotNumber()).build();
          parkingSlot.assignVehicle(vehicle);
          break;
        }
      }
    }
    return parkingLocation;
  }

  private boolean isVehicleParkable(Vehicle vehicle, ParkingSlot parkingSlot) {
    return parkingSlot.isFree() && parkingSlot.getType()
        .equals(ParkingSlotType.getParkingSlotType(vehicle.getType()));
  }

  @Override
  public boolean leaveSlot(final int level, final int slotNumber) throws DbValidationException {

    dbValidator.checkParkingLotExists(parkingLot);

    if (level < parkingLot.size() && slotNumber < parkingLot.get(level).getSlots().size()) {
      if (parkingLot.get(level) != null &&
          parkingLot.get(level).getSlots() != null &&
          !parkingLot.get(level).getSlots().isEmpty() &&
          !parkingLot.get(level).getSlots().get(slotNumber).isFree()) {
        parkingLot.get(level).getSlots().get(slotNumber).removeVehicle();
        return true;
      }
    }
    return false;
  }


  public List<ParkingLevel> getParkingLotStatus() throws DbValidationException {

    dbValidator.checkParkingLotExists(parkingLot);

    final List<ParkingLevel> outParkingLevels = new ArrayList<>();

    for (int level = 0; level < parkingLot.size(); level++) {
      ParkingLevel occupiedParkingLevel = null;
      List<ParkingSlot> allocatedSlotInALevel = null;
      for (int slot = 0; slot < parkingLot.get(level).getSlots().size(); slot++) {
        if (!parkingLot.get(level).getSlots().get(slot).isFree()) {
          if (occupiedParkingLevel == null) {
            occupiedParkingLevel = ParkingLevel.builder().level(level).build();
            allocatedSlotInALevel = new ArrayList<>();
          }
          allocatedSlotInALevel.add(parkingLot.get(level).getSlots().get(slot));
        }
        if (occupiedParkingLevel != null) {
          occupiedParkingLevel.setSlots(allocatedSlotInALevel);
        }
      }
      if (occupiedParkingLevel != null) {
        outParkingLevels.add(occupiedParkingLevel);
      }
    }
    return outParkingLevels;
  }

  public List<String> getRegistrationNumbersWithColor(String color) throws DbValidationException {

    dbValidator.checkParkingLotExists(parkingLot);

    final List<String> listofVehiclesWithGivenColor = new ArrayList<>();
    for (int level = 0; level < parkingLot.size(); level++) {
      for (int slot = 0; slot < parkingLot.get(level).getSlots().size(); slot++) {
        if (!parkingLot.get(level).getSlots().get(slot).isFree() && parkingLot
            .get(level)
            .getSlots()
            .get(slot)
            .getVehicle()
            .getColor()
            .equalsIgnoreCase(color)) {
          listofVehiclesWithGivenColor
              .add(parkingLot.get(level).getSlots().get(slot).getVehicle().getRegistrationNumber());
        }
      }
    }
    return listofVehiclesWithGivenColor;
  }

  public List<Integer> getSlotNumbersWithColor(String color) throws DbValidationException {

    dbValidator.checkParkingLotExists(parkingLot);

    final List<Integer> listofVehiclesWithGivenColor = new ArrayList<>();
    for (int level = 0; level < parkingLot.size(); level++) {
      for (int slot = 0; slot < parkingLot.get(level).getSlots().size(); slot++) {
        if (!parkingLot.get(level).getSlots().get(slot).isFree() && parkingLot
            .get(level)
            .getSlots()
            .get(slot)
            .getVehicle()
            .getColor()
            .equalsIgnoreCase(color)) {
          listofVehiclesWithGivenColor.add(parkingLot.get(level).getSlots().get(slot).getSlotNumber());
        }
      }
    }
    return listofVehiclesWithGivenColor;
  }

  public int getSlotNumberWithRegNumber(String registrationNumber) throws DbValidationException {

    dbValidator.checkParkingLotExists(parkingLot);

    for (int level = 0; level < parkingLot.size(); level++) {
      for (int slot = 0; slot < parkingLot.get(level).getSlots().size(); slot++) {
        if (!parkingLot.get(level).getSlots().get(slot).isFree() && parkingLot
            .get(level)
            .getSlots()
            .get(slot)
            .getVehicle()
            .getRegistrationNumber()
            .equalsIgnoreCase(registrationNumber)) {
          return slot;
        }
      }
    }
    return -1;
  }
}
