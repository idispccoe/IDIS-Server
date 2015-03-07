package hello;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	
	private MongoDAOImpl mongoDaoImpl = MongoDAOImpl.getInstance();
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private Date startTime = new Date();
	private Date stopTime = new Date();

	@RequestMapping("/start")
    public ServerResponse startMotor() {       
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent("Motor started successfully");
		Map<String, String> data = new HashMap<String, String>();
		startTime = new Date();
		data.put("datetime", df.format(startTime));
		data.put("status", "ON");
		try{
			mongoDaoImpl.insert("motorStatus", data);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
        return res;
    }	
	
	@RequestMapping("/stop")
    public ServerResponse stopMotor() {       
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent("Motor stopped successfully");
		Map<String, String> data = new HashMap<String, String>();
		stopTime = new Date();
		data.put("datetime", df.format(stopTime));
		data.put("status", "OFF");
		Long totalONTime = stopTime.getTime()-startTime.getTime();
		data.put("totalONtime", totalONTime.toString());
		try{
			mongoDaoImpl.insert("motorStatus", data);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
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
