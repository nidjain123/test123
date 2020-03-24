package com.gojek.parkinglot.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for defining a level and its property.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLevel {

  private int level;
  private int size;
  private List<ParkingSlot> slots; // level has list of slots

}
