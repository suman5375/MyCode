package drone.walmart.org.DroneWalmartProject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import drone.walmart.org.DroneWalmartProject.service.DroneDeliveryService;

/**
 * Main Method for testing DroneDelivery functionality
 *
 */
public class DroneDelivery {
	public static void main(String[] args) throws URISyntaxException {

		DroneDeliveryService driveDroneService = new DroneDeliveryService();
		URI fileName = new URI(args[0]);
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			driveDroneService.deliverToFile(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
