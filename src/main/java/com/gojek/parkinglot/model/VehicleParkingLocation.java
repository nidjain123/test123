package com.gojek.parkinglot.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VehicleParkingLocation {

    private int level;
    private int slot;

}
