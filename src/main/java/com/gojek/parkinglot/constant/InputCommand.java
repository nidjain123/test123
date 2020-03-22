package com.gojek.parkinglot.constant;

import java.util.HashMap;
import java.util.Map;

public class InputCommand
{
	private static volatile Map<String, Integer> commandsParameterMap = new HashMap<String, Integer>();
	
	static
	{
		commandsParameterMap.put(CommonConstant.CREATE_PARKING_LOT, 1);
		commandsParameterMap.put(CommonConstant.PARK, 2);
		commandsParameterMap.put(CommonConstant.LEAVE, 1);
		commandsParameterMap.put(CommonConstant.STATUS, 0);
		commandsParameterMap.put(CommonConstant.REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
		commandsParameterMap.put(CommonConstant.SLOTS_NUMBER_FOR_CARS_WITH_COLOR, 1);
		commandsParameterMap.put(CommonConstant.SLOTS_NUMBER_FOR_REG_NUMBER, 1);
	}
	
	/**
	 * @return the commandsParameterMap
	 */
	public static Map<String, Integer> getCommandsParameterMap()
	{
		return commandsParameterMap;
	}

	
}
