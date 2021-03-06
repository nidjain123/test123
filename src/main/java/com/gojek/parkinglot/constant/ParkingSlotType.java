package com.gojek.parkinglot.constant;

/**
 * Enum Class to define slot type.
 */
public enum ParkingSlotType {
  SMALL, LARGE;

  public static ParkingSlotType getParkingSlotType(final VehicleType vehicleType) {
    switch (vehicleType) {
      case CAR:
      case MOTORBIKE:
        return ParkingSlotType.SMALL;
      case TRUCK:
        return ParkingSlotType.LARGE;
      default:
        return null;
    }
  }
}
