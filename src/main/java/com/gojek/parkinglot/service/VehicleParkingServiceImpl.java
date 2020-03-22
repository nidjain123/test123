package com.gojek.parkinglot.service;

import java.util.List;

import com.gojek.parkinglot.constant.ErrorConstants;
import com.gojek.parkinglot.dao.VehicleParkingDaoProxy;
import com.gojek.parkinglot.dao.VehicleParkingDaoProxyImpl;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;

public class VehicleParkingServiceImpl implements VehicleParkingService {

    VehicleParkingDaoProxy vehicleParkingDaoProxy;

    public void createPartkingLot(final int noOflevels, final List<ParkingLevel> levels) throws ParkingLotException {
        if (vehicleParkingDaoProxy != null) {
            throw new ParkingLotException(ErrorConstants.PARKING_ALREADY_EXIST_ERR_MSG);
        }
        //Assuming all levels will be added during initialization.
        vehicleParkingDaoProxy = new VehicleParkingDaoProxyImpl();
        vehicleParkingDaoProxy.createPartkingLot(noOflevels, levels);
    }

    public VehicleParkingLocation parkVehicle(Vehicle vehicle) throws ParkingLotException {
        final VehicleParkingLocation vehicleParkingLocation = vehicleParkingDaoProxy.parkVehicle(vehicle);
        if (vehicleParkingLocation == null) {
            throw new ParkingLotException(ErrorConstants.PARKING_LOT_FULL_ERR_MSG);
        } else {
            return vehicleParkingLocation;
        }
    }

    public boolean leaveSlot(final int level, final int slotNumber) throws ParkingLotException {
        if (!vehicleParkingDaoProxy.leaveSlot(level - 1, slotNumber)) {
            throw new ParkingLotException(String.format(ErrorConstants.INVALID_VALUE_ERR_MSG, "slotNumber"));
        }
        return true;
    }

    public List<ParkingLevel> getParkingLotStatus() throws ParkingLotException {
        final List<ParkingLevel> parkingLevelData = vehicleParkingDaoProxy.getParkingLotStatus();
        if (parkingLevelData.isEmpty()) {
            throw new ParkingLotException(ErrorConstants.PARKING_LOT_EMPTY_ERR_MSG);
        }
        return parkingLevelData;
    }

    public List<String> getRegistrationNumbersWithColor(final String color) throws ParkingLotException {
        final List<String> regNumberList = vehicleParkingDaoProxy.getRegistrationNumbersWithColor(color);
        if (regNumberList.isEmpty()) {
            throw new ParkingLotException(ErrorConstants.NOT_FOUND_ERR_MSG);
        }
        return regNumberList;
    }

    public List<Integer> getSlotNumbersWithColor(final String color) throws ParkingLotException {
        final List<Integer> slotNumberList = vehicleParkingDaoProxy.getSlotNumbersWithColor(color);
        if (slotNumberList.isEmpty()) {
            throw new ParkingLotException(ErrorConstants.NOT_FOUND_ERR_MSG);
        }
        return slotNumberList;
    }

    public int getSlotNumberWithRegNumber(final String registrationNumber) throws ParkingLotException {
        final int slotNumber = vehicleParkingDaoProxy.getSlotNumberWithRegNumber(registrationNumber);
        if (slotNumber == -1) {
            throw new ParkingLotException(ErrorConstants.NOT_FOUND_ERR_MSG);
        }
        return slotNumber;
    }
}
