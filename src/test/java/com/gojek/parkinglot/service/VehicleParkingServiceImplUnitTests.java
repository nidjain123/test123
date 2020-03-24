package com.gojek.parkinglot.service;

import com.gojek.parkinglot.constant.UnitTestConstant;
import com.gojek.parkinglot.dao.VehicleParkingDaoProxy;
import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Unit test cases for Vehicle Service class.
 */
public class VehicleParkingServiceImplUnitTests {

  @Mock
  private transient VehicleParkingDaoProxy vehicleParkingDaoProxy;

  private VehicleParkingServiceImpl vehicleParkingService;


  @BeforeEach
  public void beforeEach() {
    MockitoAnnotations.initMocks(this);
    vehicleParkingService = new VehicleParkingServiceImpl(vehicleParkingDaoProxy);
  }

  @Test
  void createPartkingLot_WithValidInput_RunsSuccessfully() throws ParkingLotException, DbValidationException {
    //Arrange
    final List<ParkingLevel> parkingSlot = new ArrayList<>();
    Mockito.doNothing().when(vehicleParkingDaoProxy).createPartkingLot(Mockito.anyList());

    //Act
    vehicleParkingService.createPartkingLot(parkingSlot);

    //Assert
    Mockito.verify(vehicleParkingDaoProxy, Mockito.times(1)).createPartkingLot(Mockito.anyList());
  }

  @Test
  void parkVehicle_WithValidInput_RunsSuccessfully() throws ParkingLotException, DbValidationException {
    //Arrange
    final Vehicle vehicle = Vehicle.builder().build();
    final VehicleParkingLocation vehicleParkingLocation = VehicleParkingLocation.builder().level(0).build();
    Mockito.doReturn(vehicleParkingLocation).when(vehicleParkingDaoProxy).parkVehicle(Mockito.any());

    //Act
    VehicleParkingLocation result = vehicleParkingService.parkVehicle(vehicle);

    //Assert
    Assertions.assertNotNull(result);
    Assertions.assertEquals(0, result.getLevel());
    Mockito.verify(vehicleParkingDaoProxy, Mockito.times(1)).parkVehicle(Mockito.any());
  }

  @Test
  void leaveSlot_WithValidInput_RunsSuccessfully() throws ParkingLotException, DbValidationException {
    //Arrange
    Mockito.doReturn(true).when(vehicleParkingDaoProxy).leaveSlot(0, 0);

    //Act
    vehicleParkingService.leaveSlot(0, 0);

    //Assert
    Mockito.verify(vehicleParkingDaoProxy, Mockito.times(1)).leaveSlot(0, 0);
  }

  @Test
  void getRegistrationNumbersWithColor_WithValidInput_RunsSuccessfully()
      throws ParkingLotException, DbValidationException {
    //Arrange
    final List<String> regNumbers = new ArrayList<>();
    regNumbers.add(UnitTestConstant.VEHICLE_REG_NO);
    regNumbers.add(UnitTestConstant.VEHICLE_REG_NO_2);
    Mockito.doReturn(regNumbers).when(vehicleParkingDaoProxy)
        .getRegistrationNumbersWithColor(UnitTestConstant.VEHICLE_COLOR);

    //Act
    final List<String> result = vehicleParkingService.getRegistrationNumbersWithColor(UnitTestConstant.VEHICLE_COLOR);

    //Assert
    Assertions.assertNotNull(result);
    Assertions.assertEquals(2, result.size());
    Mockito.verify(vehicleParkingDaoProxy, Mockito.times(1))
        .getRegistrationNumbersWithColor(UnitTestConstant.VEHICLE_COLOR);
  }

}
