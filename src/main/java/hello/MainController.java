package hello;


import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	
	private Random randomGenerator = new Random();
    

	@RequestMapping("/start")
    public ServerResponse startMotor() {       
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent("Motor started successfully");
        return res;
    }	
	
	@RequestMapping("/stop")
    public ServerResponse stopMotor() {       
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent("Motor stopped successfully");
        return res;
    }
	
	@RequestMapping("/waterLevel")
    public ServerResponse checkWaterLevel() {       
		int randomInt = randomGenerator.nextInt(100);
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent(""+randomInt);
        return res;
    }	
	
	@RequestMapping("/sensorStatus")
    public ServerResponse checkSensorStatus() {       
		double randomDouble = randomGenerator.nextDouble();
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent("Sensor Status: "+randomDouble);
        return res;
    }	
}
