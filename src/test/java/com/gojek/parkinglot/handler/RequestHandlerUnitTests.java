package com.gojek.parkinglot.handler;

/**
 * Unit Test case class for Request handler.
 */

import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.service.VehicleParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RequestHandlerUnitTests {

  private transient AbstractHandler handler;

  @Mock
  private transient VehicleParkingService parkingService;

  @BeforeEach
  public void beforeEach() {
    MockitoAnnotations.initMocks(this);
    handler = new RequestHandler();
    handler.setService(parkingService);

  }

  @Test
  void execute_WithInputCreateParkingLot_ExecutesSucessfully() throws DbValidationException, ParkingLotException {
    //Arrange
    Mockito.doNothing().when(parkingService).createPartkingLot(Mockito.anyList());
    final String input = "create_parking_lot 6";

    //Act
    handler.execute(input);

    //Assert
    Mockito.verify(parkingService, Mockito.times(1)).createPartkingLot(Mockito.anyList());
  }


}
