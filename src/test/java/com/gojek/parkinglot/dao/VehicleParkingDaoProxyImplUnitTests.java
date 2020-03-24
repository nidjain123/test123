package com.gojek.parkinglot.dao;

import com.gojek.parkinglot.constant.ParkingSlotType;
import com.gojek.parkinglot.constant.UnitTestConstant;
import com.gojek.parkinglot.constant.VehicleType;
import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.ParkingSlot;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;
import com.gojek.parkinglot.validator.DbValidatorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test cases for Vehicle Parking Proxy class.
 */
public class VehicleParkingDaoProxyImplUnitTests {

  private transient VehicleParkingDaoProxyImpl vehicleParkingDaoProxy;

  @BeforeEach
  public void beforeEach() {
    final List<ParkingLevel> parkingLevels = new ArrayList<>();
    final List<ParkingSlot> parkingSlots = new ArrayList<>();
    parkingSlots.add(ParkingSlot.builder()
        .free(false)
        .slotNumber(0)
        .type(ParkingSlotType.SMALL)
        .vehicle(Vehicle.builder()
            .color(UnitTestConstant.VEHICLE_COLOR)
            .registrationNumber(UnitTestConstant.VEHICLE_REG_NO)
            .build())
        .build());
    parkingSlots.add(ParkingSlot.builder().free(true).slotNumber(1).type(ParkingSlotType.SMALL).build());
    final ParkingLevel parkingLevel = ParkingLevel.builder().level(0).slots(parkingSlots).build();
    parkingLevels.add(parkingLevel);
    vehicleParkingDaoProxy = new VehicleParkingDaoProxyImpl(parkingLevels, new DbValidatorImpl());
  }

  @Test
  void createPartkingLot_WithValidInput_RunsSuccessfully() throws DbValidationException {
    //Arrange
    vehicleParkingDaoProxy = new VehicleParkingDaoProxyImpl(new ArrayList<>(),new DbValidatorImpl());
    final List<ParkingLevel> parkingLevels = new ArrayList<>();
    final List<ParkingSlot> parkingSlots = new ArrayList<>();
    parkingSlots.add(ParkingSlot.builder().free(true).slotNumber(0).build());
    final ParkingLevel parkingLevel = ParkingLevel.builder().level(0).slots(parkingSlots).build();
    parkingLevels.add(parkingLevel);

    //Act
    vehicleParkingDaoProxy.createPartkingLot(parkingLevels);
  }

  @Test
  void parkVehicle_WithValidInput_RunsSuccessfully() throws DbValidationException {
    //Arrange
    final Vehicle vehicle = Vehicle.builder().color(UnitTestConstant.VEHICLE_COLOR)
        .registrationNumber(UnitTestConstant.VEHICLE_REG_NO).type(VehicleType.CAR).build();
    //Act
    final VehicleParkingLocation vehicleParkingLocation = vehicleParkingDaoProxy.parkVehicle(vehicle);

    //assert
    Assertions.assertNotNull(vehicleParkingLocation);
    Assertions.assertEquals(1, vehicleParkingLocation.getSlot());
    Assertions.assertEquals(0, vehicleParkingLocation.getLevel());
  }

  @Test
  void leaveSlot_WithValidInput_RunsSuccessfully() throws DbValidationException {
    //Act
    final boolean isSuccess = vehicleParkingDaoProxy.leaveSlot(0, 0);

    //assert
    Assertions.assertTrue(isSuccess);
  }

  @Test
  void getParkingLotStatus_WithValidInput_RunsSuccessfully() throws DbValidationException {
    //Act
    final List<ParkingLevel> parkingSlot = vehicleParkingDaoProxy.getParkingLotStatus();

    //assert
    Assertions.assertNotNull(parkingSlot);
    Assertions.assertTrue(!parkingSlot.isEmpty());
    Assertions.assertTrue(!parkingSlot.get(0).getSlots().isEmpty());
  }

  @Test
  void getSlotNumbersWithColor_WithValidInput_RunsSuccessfully() throws DbValidationException {
    //Act
    final List<Integer> parkingSlotNumbers = vehicleParkingDaoProxy
        .getSlotNumbersWithColor(UnitTestConstant.VEHICLE_COLOR);

    //assert
    Assertions.assertNotNull(parkingSlotNumbers);
    Assertions.assertTrue(!parkingSlotNumbers.isEmpty());
    Assertions.assertEquals(0, parkingSlotNumbers.get(0));
  }

  @Test
  void getRegistrationNumbersWithColor_WithValidInput_RunsSuccessfully() throws DbValidationException {
    //Act
    final List<String> vehicleRegNumbers = vehicleParkingDaoProxy
        .getRegistrationNumbersWithColor(UnitTestConstant.VEHICLE_COLOR);

    //assert
    Assertions.assertNotNull(vehicleRegNumbers);
    Assertions.assertTrue(!vehicleRegNumbers.isEmpty());
    Assertions.assertEquals(UnitTestConstant.VEHICLE_REG_NO, vehicleRegNumbers.get(0));
  }

  @Test
  void getSlotNumberWithRegNumber_WithValidInput_RunsSuccessfully() throws DbValidationException {
    //Act
    final int slotNumber = vehicleParkingDaoProxy
        .getSlotNumberWithRegNumber(UnitTestConstant.VEHICLE_REG_NO);

    //assert
    Assertions.assertEquals(0, slotNumber);
  }

  @Test
  void getSlotNumberWithRegNumber_WhenNoSlotExists_ReturnsMinusOne() throws DbValidationException {
    //Act
    final int slotNumber = vehicleParkingDaoProxy
        .getSlotNumberWithRegNumber(UnitTestConstant.VEHICLE_REG_NO_2);

    //assert
    Assertions.assertEquals(-1, slotNumber);
  }

}
