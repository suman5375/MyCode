package drone.walmart.org.DroneWalmartProject.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author msuma
 *
 *         This class is responsible for preparing the drone delivery metrics
 *         and to start ad stop drone service
 */
public class DroneDeliveryService {

	Map<String, Double> distanceMap = new HashMap<String, Double>();
	Map<String, Double> zoneMap = new HashMap<String, Double>();
	Map<String, String> coOrdinatesMap = new HashMap<String, String>();
	Queue<String> deliveryQueue = new ArrayDeque<String>();
	List<String> outputList = new ArrayList<String>();

	/*
	 * Function for calculating distance that drone needs to travel
	 */
	BiFunction<Integer, Integer, Double> calculateDistance = (xCord, yCord) -> {
		Double distanceInHours = Math.sqrt((xCord * xCord) - (yCord * yCord)) / 60;
		return distanceInHours;
	};

	/*
	 * Function for getting the co-ordinates from input
	 */
	Function<String[], String> getCordinates = i -> {
		String[] coOrdinates = i[1].replaceAll("[^0-9 ]", " ").trim().split("\\s+");
		return coOrdinates[0] + "," + coOrdinates[1];
	};

	/*
	 * Consumer for preparing the Order Deliveries by shortest distance First
	 */
	Consumer<Map<String, Double>> setOrderDeliveries = inputMap -> {
		for (Map.Entry<String, Double> entry : inputMap.entrySet()) {
			deliveryQueue.add(entry.getKey());
		}
	};

	/*
	 * Function for calculating angle that drone needs to follow
	 */
	BiFunction<Integer, Integer, Double> getAngle = (xCord, yCord) -> {
		Double angle = Math.toDegrees(Math.atan2(yCord, xCord));
		if (angle < 0)
			angle += 360;
		return angle;
	};

	/*
	 * Consumer for writing to output file
	 */
	Consumer<List<String>> writeToFile = i -> {
		String output = String.join("\n", i);
		try {
			Files.write(Paths.get("./deliveryOutput.txt"), output.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	/* Start Execution for Delivery */
	public void deliverToFile(Stream<String> stream) {
		DroneExecution droneService = new DroneExecution();
		List<String[]> list = stream.map(i -> i.split("\\s+")).collect(Collectors.toList());

		for (String[] orderInfo : list) {
			String coOrdinates = getCordinates.apply(orderInfo);
			coOrdinatesMap.put(orderInfo[0], coOrdinates);
			distanceMap.put(orderInfo[0], calculateDistance.apply(Integer.parseInt(coOrdinates.split(",")[0]),
					Integer.parseInt(coOrdinates.split(",")[1])));
		}

		// pick a package for delivery
		Map<String, Double> sortedDistanceMap = distanceMap.entrySet().stream()
				.sorted(Map.Entry.<String, Double>comparingByValue())
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		setOrderDeliveries.accept(sortedDistanceMap);

		if (deliveryQueue.size() > 0) {
			int numOfDeliveries = 0;
			for (String orderNum : deliveryQueue) {

				String[] coOrd = coOrdinatesMap.get(orderNum).split(",");
				LocalTime deliveryTime = droneService.droneDeliveryStart(orderNum, sortedDistanceMap.get(orderNum),
						getAngle.apply(Integer.parseInt(coOrd[0]), Integer.parseInt(coOrd[1])), numOfDeliveries);
				deliveryQueue.remove();
				outputList.add(orderNum + " " + deliveryTime.toString());
				numOfDeliveries++;
			}
		}
		writeToFile.accept(outputList);
		Path path = Paths.get("deliveryOutput.txt");
		Path filePath = path.toAbsolutePath();
		System.out.println(filePath);
	}
}