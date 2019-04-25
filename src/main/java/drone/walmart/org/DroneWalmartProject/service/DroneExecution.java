/**
 * 
 */
package drone.walmart.org.DroneWalmartProject.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author msuman
 *This class has properties to start and return to home
 */
public class DroneExecution {

	/*
	 * This method starts delivering to the customers
	 */
	public LocalTime droneDeliveryStart(String OrderNmber, Double distance, double angle) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime now = LocalTime.parse(LocalTime.now().format(formatter));
//		System.out.println("Drone started for OrderNum: " + OrderNmber + " and will travel for " + distance
//				+ " units in " + angle + " degree angle");

		return now;
	}

	public LocalTime returnToWarehouse(Double distance) {
		// TODO Auto-generated method stub
		//System.out.println("Returned back to home to pick next delivery");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime now = LocalTime.parse(LocalTime.now().format(formatter));
		
		return now.plusMinutes(Math.round(distance));
	}

}
