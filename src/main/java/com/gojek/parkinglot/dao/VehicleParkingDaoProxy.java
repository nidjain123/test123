package com.gojek.parkinglot.dao;

import java.util.List;

import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;

public interface VehicleParkingDaoProxy {

    void createParkingLot(final int noOflevels, final List<ParkingLevel> levels);

    VehicleParkingLocation parkVehicle(final Vehicle vehicle);

    boolean leaveSlot(final int level, final int slotNumber);

    List<ParkingLevel> getParkingLotStatus();

    List<String> getRegistrationNumbersWithColor(final String color);

    List<Integer> getSlotNumbersWithColor(final String color);

    int getSlotNumberWithRegNumber(final String registrationNumber);

}
