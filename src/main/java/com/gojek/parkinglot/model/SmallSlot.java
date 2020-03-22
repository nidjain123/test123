package com.gojek.parkinglot.model;

import com.gojek.parkinglot.constant.ParkingSlotType;

public class SmallSlot extends ParkingSlot {

  public SmallSlot(final int slotNumber, final boolean free) {
    super(ParkingSlotType.SMALL, slotNumber, free);
  }
}
