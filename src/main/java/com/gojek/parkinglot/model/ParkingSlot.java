package com.gojek.parkinglot.model;

import com.gojek.parkinglot.constant.ParkingSlotType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ParkingSlot {

    private int slotNumber;
    private boolean free;
    private Vehicle vehicle;
    private ParkingSlotType type;

    public ParkingSlot(final ParkingSlotType type, final int slotNumber, final boolean free) {
        this.type = type;
        this.slotNumber = slotNumber;
        this.free = free;
    }

    public boolean isFree() {
        return free;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        free = false;
    }

    public void removeVehicle() {
        this.vehicle = null;
        free = true;
    }
}
