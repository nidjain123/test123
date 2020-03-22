package com.gojek.parkinglot.model;

import com.gojek.parkinglot.constant.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

  private String registrationNumber;
  private VehicleType type;
  private String color;

}
