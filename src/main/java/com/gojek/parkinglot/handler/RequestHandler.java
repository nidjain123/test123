package com.gojek.parkinglot.handler;

import com.gojek.parkinglot.constant.CommonConstant;
import com.gojek.parkinglot.constant.ErrorConstants;
import com.gojek.parkinglot.constant.VehicleType;
import com.gojek.parkinglot.exception.DbValidationException;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.ParkingLevel;
import com.gojek.parkinglot.model.ParkingSlot;
import com.gojek.parkinglot.model.SmallSlot;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.model.VehicleParkingLocation;
import com.gojek.parkinglot.service.AbstractParkingService;
import com.gojek.parkinglot.service.VehicleParkingService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Processor to handle all requests and calling service layer.
 */
public class RequestHandler implements AbstractHandler {

  private VehicleParkingService parkingService;

  @Override
  public void execute(String input) throws ParkingLotException, DbValidationException {
    String[] inputs = input.split(" ");
    String command = inputs[0];
    switch (command) {
      case CommonConstant.CREATE_PARKING_LOT:
        try {
          int capacity = Integer.parseInt(inputs[1]);
          final List<ParkingLevel> parkingLevels = new ArrayList<>();
          final ParkingLevel zeroParkingLevel = ParkingLevel.builder().level(0).size(capacity).build();
          final List<ParkingSlot> slots =
              IntStream.range(0, capacity).mapToObj(index -> new SmallSlot(index, true)).collect(Collectors.toList());
          zeroParkingLevel.setSlots(slots);
          parkingLevels.add(zeroParkingLevel);
          parkingService.createPartkingLot(parkingLevels);
          System.out.println(String.format("Created a parking lot with %s slots", capacity));
        } catch (NumberFormatException e) {
          throw new ParkingLotException(String.format(ErrorConstants.INVALID_VALUE_ERR_MSG, "capacity"));
        }
        break;
      case CommonConstant.PARK:
        final Vehicle vehicle = Vehicle.builder().color(inputs[2]).registrationNumber(inputs[1]).type(VehicleType.CAR)
            .build();
        final VehicleParkingLocation parkingLocation = parkingService.parkVehicle(vehicle);
        final int parkingSlot = parkingLocation.getSlot() + 1;
        System.out.println("Allocated slot number: " + parkingSlot);
        break;
      case CommonConstant.LEAVE:
        try {
          int slotNumber = Integer.parseInt(inputs[1]) - 1;
          parkingService.leaveSlot(0, slotNumber);
          System.out.println(String.format("Slot number %s is free", slotNumber + 1));
        } catch (NumberFormatException e) {
          throw new ParkingLotException(String.format(ErrorConstants.INVALID_VALUE_ERR_MSG, "slot_number"));
        }
        break;
      case CommonConstant.SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
        List<Integer> slotNumberList = parkingService.getSlotNumbersWithColor(inputs[1]);
        List<Integer> printableSlotNumbers = slotNumberList.stream().map(slotNumber -> slotNumber + 1)
            .collect(Collectors.toList());
        printList(printableSlotNumbers);
        break;
      case CommonConstant.STATUS:
        final List<ParkingLevel> parkingLotStatus = parkingService.getParkingLotStatus();
        System.out.println("Slot No.    Registration No    Colour");
        for (ParkingLevel level : parkingLotStatus) {
          level
              .getSlots()
              .stream()
              .map(slot -> String.format("%s           %s      %s", slot.getSlotNumber() + 1,
                  slot.getVehicle().getRegistrationNumber(), slot.getVehicle().getColor()))
              .forEach(System.out::println);
        }
        break;
      case CommonConstant.REG_NUMBER_FOR_CARS_WITH_COLOR:
        final List<String> registrationNumberList = parkingService.getRegistrationNumbersWithColor(inputs[1]);
        printList(registrationNumberList);
        break;
      case CommonConstant.SLOTS_NUMBER_FOR_REG_NUMBER:
        final int slotNumber = parkingService.getSlotNumberWithRegNumber(inputs[1]) + 1;
        System.out.println(slotNumber);
        break;
      default:
        break;
    }
  }

  private <T> void printList(List<T> list) {
    for (int i = 0; i < list.size(); i++) {
      if (i == list.size() - 1) {
        System.out.println(list.get(i));
      } else {
        System.out.print(list.get(i) + ", ");
      }
    }
  }

  @Override
  public void setService(AbstractParkingService service) {
    this.parkingService = (VehicleParkingService) service;
  }
}
