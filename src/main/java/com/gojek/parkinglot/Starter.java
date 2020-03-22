package com.gojek.parkinglot;

import com.gojek.parkinglot.exception.ErrorDescription;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.processor.AbstractProcessor;
import com.gojek.parkinglot.processor.RequestProcessor;
import com.gojek.parkinglot.service.VehicleParkingServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Starter {

  public static void main(String[] args) {
    try {
      System.out.println("===================================================================");
      System.out.println("===================      GOJEK PARKING LOT     ====================");
      System.out.println("===================================================================");
      AbstractProcessor processor = new RequestProcessor();
      processor.setService(new VehicleParkingServiceImpl());
      printUsage();
      switch (args.length) {
        case 0: // Interactive: command-line input/output
        {
          System.out.println("Please Enter 'exit' to end Execution");
          System.out.println("Input:");
          handleCommandLineInput(processor);
          break;
        }
        case 1: // File input/output
        {
          handleFileInput(args, processor);
          break;
        }
        default:
          System.out.println("Invalid input. Usage Style: java -jar <jar_file_path> <input_file_path>");
      }
    } catch (ParkingLotException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void printUsage() {
    StringBuffer buffer = new StringBuffer();
    buffer = buffer.append(
        "--------------Please Enter one of the below commands. {variable} to be replaced -----------------------")
        .append("\n");
    buffer = buffer.append("A) For creating parking lot of size n               ---> create_parking_lot {capacity}")
        .append("\n");
    buffer = buffer
        .append("B) To park a car                                    ---> park <<car_number>> {car_clour}")
        .append("\n");
    buffer = buffer.append("C) Remove(Unpark) car from parking                  ---> leave {slot_number}")
        .append("\n");
    buffer = buffer.append("D) Print status of parking slot                     ---> status").append("\n");
    buffer = buffer.append(
        "E) Get cars registration no for the given car color ---> registration_numbers_for_cars_with_color {car_color}")
        .append("\n");
    buffer = buffer.append(
        "F) Get slot numbers for the given car color         ---> slot_numbers_for_cars_with_color {car_color}")
        .append("\n");
    buffer = buffer.append(
        "G) Get slot number for the given car number         ---> slot_number_for_registration_number {car_number}")
        .append("\n");
    System.out.println(buffer.toString());
  }

  private static void handleCommandLineInput(final AbstractProcessor processor) throws ParkingLotException {
    BufferedReader bufferReader = null;
    try {
      while (true) {
        bufferReader = new BufferedReader(new InputStreamReader(System.in));
        String input = bufferReader.readLine().trim();
        if (input.equalsIgnoreCase("exit")) {
          break;
        } else {
          if (processor.validateInput(input)) {
            try {
              processor.execute(input.trim());
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
          } else {
            printUsage();
          }
        }
      }
    } catch (Exception e) {
      throw new ParkingLotException(ErrorDescription.INVALID_REQUEST_ERR_MSG);
    } finally {
      try {
        if (bufferReader != null) {
          bufferReader.close();
        }
      } catch (IOException e) {
      }
    }
  }

  private static void handleFileInput(final String[] args, final AbstractProcessor processor)
      throws ParkingLotException {
    File inputFile = new File(args[0]);
    BufferedReader bufferReader = null;
    String input;
    try {
      bufferReader = new BufferedReader(new FileReader(inputFile));
      int lineNo = 1;
      while ((input = bufferReader.readLine()) != null) {
        input = input.trim();
        if (processor.validateInput(input)) {
          try {
            processor.execute(input);
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        } else {
          System.out.println("Incorrect Command Found at line: " + lineNo + " ,Input: " + input);
        }
        lineNo++;
      }
    } catch (Exception e) {
      throw new ParkingLotException(ErrorDescription.INVALID_FILE_ERR_MSG);
    } finally {
      try {
        if (bufferReader != null) {
          bufferReader.close();
        }
      } catch (IOException e) {
      }
    }
  }
}
