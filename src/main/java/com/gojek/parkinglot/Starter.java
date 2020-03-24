package com.gojek.parkinglot;

import com.gojek.parkinglot.constant.ErrorConstants;
import com.gojek.parkinglot.dao.VehicleParkingDaoProxyImpl;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.handler.AbstractHandler;
import com.gojek.parkinglot.handler.RequestHandler;
import com.gojek.parkinglot.service.VehicleParkingServiceImpl;
import com.gojek.parkinglot.validator.DbValidatorImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Main class responsible for starting the exceution and has main method in it.
 */
public class Starter {

  public static void main(String[] args) {
    try {
      AbstractHandler requestHandler = new RequestHandler();
      //Instantiating Service
      requestHandler.setService(
          new VehicleParkingServiceImpl(new VehicleParkingDaoProxyImpl(new ArrayList<>(), new DbValidatorImpl())));
      switch (args.length) {
        case 0: // Interactive: command-line input/output
        {
          handleCommandLineInput(requestHandler);
          break;
        }
        case 1: // File input/output
        {
          handleFileInput(args, requestHandler);
          break;
        }
        default:
          System.out.println("Invalid input. Usage Style: java -jar <jar_file_path> <input_file_path>");
      }
    } catch (ParkingLotException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Handle command line input.
   *
   * @param processor processor instance
   * @throws ParkingLotException throws parkinglot exception
   */
  private static void handleCommandLineInput(final AbstractHandler processor) throws ParkingLotException {
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
          }
        }
      }
    } catch (Exception e) {
      throw new ParkingLotException(ErrorConstants.INVALID_REQUEST_ERR_MSG);
    } finally {
      try {
        if (bufferReader != null) {
          bufferReader.close();
        }
      } catch (IOException e) {
      }

    }
  }

  /**
   * Handles file input
   */
  private static void handleFileInput(final String[] args, final AbstractHandler processor)
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
      throw new ParkingLotException(ErrorConstants.INVALID_FILE_ERR_MSG);
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
