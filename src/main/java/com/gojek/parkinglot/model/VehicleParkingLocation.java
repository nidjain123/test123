package com.gojek.parkinglot.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * VehicleParkingLocation class to give vehicle's position.
 */
@Getter
@Setter
@Builder
public class VehicleParkingLocation {

  private int level;
  private int slot;

}
