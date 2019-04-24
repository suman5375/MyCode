/**
 * 
 */
package drone.walmart.org.DroneWalmartProject.serviice;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author msuman
 *
 */
public class DroneDeliveryService {
	
	public String droneDeliveryStart(double distance,double angle) {
		//start drone in given angle for given distance
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime now = LocalTime.parse(LocalTime.now().format(formatter));
		String serviceStartingTime = now.toString();
		
		return serviceStartingTime;
	}
	
}
