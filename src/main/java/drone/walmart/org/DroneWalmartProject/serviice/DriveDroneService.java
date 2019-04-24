/**
 * 
 */
package drone.walmart.org.DroneWalmartProject.serviice;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author msuma
 *
 */
public class DriveDroneService {

	Map<String, Double> distanceMap = new HashMap<String, Double>();
	Map<String, Double> zoneMap = new HashMap<String, Double>();

	BiFunction<Integer, Integer, Double> calculateDistance = (xCord, yCord) -> {
		Double distanceInHours = Math.sqrt((xCord * xCord) - (yCord * yCord)) / 60;
		return distanceInHours;
	};

	Function<String[], String> getCordinates = i -> {
		String[] coOrdinates = i[1].replaceAll("(?<!\\S)[^ ](?!\\S)", " ").trim().split("\\s+");
		return coOrdinates[0] + "," + coOrdinates[1];
	};

	Function<Map<String, Double>, String> pickPackage = i -> {
		return "";
	};

	BiFunction<Integer, Integer, Double> getAngle = (xCord, yCord) -> {
		Double angle = Math.toDegrees(Math.atan2(yCord, xCord));
		if (angle < 0)
			angle += 360;
		return angle;
	};

	public String getResult(Stream<String> stream) {
		
		DroneDeliveryService droneService = new DroneDeliveryService();

		List<String[]> list = stream.map(i -> i.split("\\s+")).collect(Collectors.toList());

		for (String[] orderInfo : list) {
			String coOrdinates = getCordinates.apply(orderInfo);
			distanceMap.put(orderInfo[0], calculateDistance.apply(Integer.parseInt(coOrdinates.split(",")[0]),
					Integer.parseInt(coOrdinates.split(",")[1])));
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime now = LocalTime.parse(LocalTime.now().format(formatter));
		LocalTime serviceStartTime = LocalTime.parse("06:00:00");
		LocalTime serviceEndTime = LocalTime.parse("22:00:00");

		// Drone Service hours are from 6:00 am to 10:00 pm
		if (now.isAfter(serviceStartTime) && now.isBefore(serviceEndTime)) {
			// pick a package for delivery
			String pickedOrder = pickPackage.apply(distanceMap);
			double distance = distanceMap.get(pickedOrder);
			//String coOrdinates = getCordinates.apply(orderInfo);
			//droneService.droneDeliveryStart(distance,angle);
			
		} else {
			return "Drone Service not available. Working hours are from 6 a.m to 10 p.m.";
		}

		return null;
	}

}
