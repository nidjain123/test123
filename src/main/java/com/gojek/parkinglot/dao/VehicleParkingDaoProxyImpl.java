package com.gojek.parkinglot.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.gojek.parkinglot.constant.ParkingSlotType;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.ParkingSlot;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;

public class VehicleParkingDaoProxyImpl implements VehicleParkingDaoProxy {

    private List<ParkingLevel> parkingLot;
    private final static Logger LOGGER = Logger.getLogger(VehicleParkingDaoProxyImpl.class.getName());

    @Override
    public void createParkingLot(final int noOflevels, final List<ParkingLevel> levels) {
        parkingLot = new ArrayList<>();
        //initialize
        for (int level = 0; level < noOflevels; level++) {
            parkingLot.add(level, levels.get(level));
        }
    }

    @Override
    public VehicleParkingLocation parkVehicle(final Vehicle vehicle) {
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
        return parkingSlot.isFree() && parkingSlot.getType().equals(ParkingSlotType.getParkingSlotType(vehicle.getType()));
    }

    @Override
    public boolean leaveSlot(final int level, final int slotNumber) {
        if (parkingLot.get(level) != null
                && parkingLot.get(level).getSlots() != null
                && !parkingLot.get(level).getSlots().isEmpty()
                && parkingLot.get(level).getSlots().get(slotNumber) != null) {
            parkingLot.get(level).getSlots().get(slotNumber).removeVehicle();
            return true;
        }
        return false;
    }

    public List<ParkingLevel> getParkingLotStatus() {
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

    public List<String> getRegistrationNumbersWithColor(String color) {
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
                    listofVehiclesWithGivenColor.add(parkingLot.get(level).getSlots().get(slot).getVehicle().getRegistrationNumber());
                }
            }
        }
        return listofVehiclesWithGivenColor;
    }

    public List<Integer> getSlotNumbersWithColor(String color) {
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

    public int getSlotNumberWithRegNumber(String registrationNumber) {
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
