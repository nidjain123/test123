
package com.gojek.parkinglot.processor;

import com.gojek.parkinglot.constant.CommonConstant;
import com.gojek.parkinglot.constant.VehicleType;
import com.gojek.parkinglot.exception.ErrorDescription;
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

public class RequestProcessor implements AbstractProcessor {

  private VehicleParkingService parkingService;

  public void setParkingService(VehicleParkingService parkingService) throws ParkingLotException {
    this.parkingService = parkingService;
  }

  @Override
  public void execute(String input) throws ParkingLotException {
    int noOflevels = 1;
    String[] inputs = input.split(" ");
    String command = inputs[0];
    switch (command) {
      case CommonConstant.CREATE_PARKING_LOT:
        try {
          int capacity = Integer.parseInt(inputs[1]);
          final List<ParkingLevel> parkingLevels = new ArrayList<>();
          final ParkingLevel zeroParkingLevel = ParkingLevel.builder().level(0).size(capacity).build();
          final List<ParkingSlot> slots = new ArrayList<>();
          for (int index = 0; index < capacity; index++) {
            ParkingSlot slot = new SmallSlot(index, true);
            slots.add(slot);
          }
          zeroParkingLevel.setSlots(slots);
          parkingLevels.add(zeroParkingLevel);
          parkingService.createPartkingLot(noOflevels, parkingLevels);
          System.out.println(String.format("Created a parking lot with %s slots", capacity));
        } catch (NumberFormatException e) {
          throw new ParkingLotException(String.format(ErrorDescription.INVALID_VALUE_ERR_MSG, "capacity"));
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
          parkingService.leaveSlot(noOflevels, slotNumber);
          System.out.println(String.format("Slot number %s is free", slotNumber + 1));
        } catch (NumberFormatException e) {
          throw new ParkingLotException(String.format(ErrorDescription.INVALID_VALUE_ERR_MSG, "slot_number"));
        }
        break;
      case CommonConstant.SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
        List<Integer> slotNumberList = parkingService.getSlotNumbersWithColor(inputs[1]);
        for (int i = 0; i < slotNumberList.size(); i++) {
          if (i == slotNumberList.size() - 1) {
            System.out.println(slotNumberList.get(i) + 1);
          } else {
            System.out.print(slotNumberList.get(i) + 1 + ", ");
          }
        }
        break;
      case CommonConstant.STATUS:
        final List<ParkingLevel> parkingLotStatus = parkingService.getParkingLotStatus();
        System.out.println("Slot No.\t\t\tRegistration No\t\t\tColour");
        for (ParkingLevel level : parkingLotStatus) {
          for (ParkingSlot slot : level.getSlots()) {
            System.out.println(
                (slot.getSlotNumber() + 1) + "\t\t\t" + slot.getVehicle().getRegistrationNumber() + "\t\t\t" + slot
                    .getVehicle()
                    .getColor());
          }
        }
        break;
      case CommonConstant.REG_NUMBER_FOR_CARS_WITH_COLOR:
        final List<String> registerationNumberList = parkingService.getRegistrationNumbersWithColor(inputs[1]);
        for (int i = 0; i < registerationNumberList.size(); i++) {
          if (i == registerationNumberList.size() - 1) {
            System.out.println(registerationNumberList.get(i));
          } else {
            System.out.print(registerationNumberList.get(i) + ", ");
          }
        }
        break;
      case CommonConstant.SLOTS_NUMBER_FOR_REG_NUMBER:
        final int slotNumber = parkingService.getSlotNumberWithRegNumber(inputs[1]) + 1;
        System.out.println(slotNumber);
        break;
      default:
        break;
    }
  }

  @Override
  public void setService(AbstractParkingService service) {
    this.parkingService = (VehicleParkingService) service;
  }
}
