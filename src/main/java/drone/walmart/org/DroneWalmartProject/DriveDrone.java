package drone.walmart.org.DroneWalmartProject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import drone.walmart.org.DroneWalmartProject.serviice.DriveDroneService;

/**
 * Hello world!
 *
 */
public class DriveDrone 
{
    public static void main( String[] args )
    {
    	
    	URI fileName = null;
    	
    	DriveDroneService driveDroneService = new DriveDroneService();
		try {
			fileName = new URI("file:///C://Users//msuma//Desktop//Drone//drone_input.txt");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    	try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
    		
           //String output = driveDroneService.getResult(stream); 
           	//stream.forEach(System.out::println);
    		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            //LocalTime now = LocalTime.parse(LocalTime.now().format(formatter));
            //System.out.println(now);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static String droneService(String string) {
    	return null;
    }
}
