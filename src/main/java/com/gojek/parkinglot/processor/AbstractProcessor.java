package com.gojek.parkinglot.processor;

import com.gojek.parkinglot.constant.InputCommand;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.service.AbstractParkingService;

public interface AbstractProcessor {

    void setService(AbstractParkingService service);

    void execute(String action) throws ParkingLotException;

    default boolean validateInput(String inputString) {
        // Split the input string to validate command and input value
        boolean valid = true;
        try {
            String[] inputs = inputString.split(" ");
            int params = InputCommand.getCommandsParameterMap().get(inputs[0]);
            switch (inputs.length) {
                case 1:
                    if (params != 0) // e.g status -> inputs = 1
                    {
                        valid = false;
                    }
                    break;
                case 2:
                    if (params != 1) // create_parking_lot 4 -> inputs = 2
                    {
                        valid = false;
                    }
                    break;
                case 3:
                    if (params != 2) // park HR-02-P-173 White -> inputs = 3
                    {
                        valid = false;
                    }
                    break;
                default:
                    valid = false;
            }
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }
}
