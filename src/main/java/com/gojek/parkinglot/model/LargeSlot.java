package com.gojek.parkinglot.model;

import com.gojek.parkinglot.constant.ParkingSlotType;

class LargeSlot extends ParkingSlot {

    public LargeSlot(final int slotNumber, final boolean free) {
        super(ParkingSlotType.LARGE, slotNumber, free);
    }
}

