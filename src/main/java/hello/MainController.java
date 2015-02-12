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
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendWaterLevelCmd();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String currStatus = SimpleRead.getCurrentStatus();
		currStatus.trim();
		System.out.println(currStatus);
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent("0");
		if(currStatus!=null && !currStatus.isEmpty()){
			Integer waterlevel = new Integer(currStatus);
			
			int randomInt = waterlevel.intValue();//randomGenerator.nextInt(100);
			randomInt = randomInt/3;		
			res.setContent(""+randomInt);
		}
        return res;
    }	
	
	@RequestMapping("/sensorStatus")
    public ServerResponse checkSensorStatus() {
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendAllSensorCmd();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String currStatus = SimpleRead.getCurrentStatus();
		currStatus.trim();
		System.out.println(currStatus);
		//double randomDouble = randomGenerator.nextDouble();
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent(currStatus);
        return res;
    }	
}
