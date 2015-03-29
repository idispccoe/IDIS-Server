package hello;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	
	private MongoDAOImpl mongoDaoImpl = MongoDAOImpl.getInstance();
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private Date startTime = new Date();
	private Date stopTime = new Date();

	@RequestMapping("/start")
    public ServerResponse startMotor() {
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendMotorStartCmd();
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent("Motor started successfully");
		//Map<String, String> data = new HashMap<String, String>();
		startTime = new Date();
		/*data.put("datetime", df.format(startTime));
		data.put("status", "ON");
		try{
			mongoDaoImpl.insert("motorStatus", data);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}*/
        return res;
    }	
	
	@RequestMapping("/stop")
    public ServerResponse stopMotor() {
		//Check Water Flow Meter Value
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendWaterFlowMeterCmd();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String waterFlowMeter = SimpleRead.getCurrentStatus();
		Long waterFlowMeterValue = Long.valueOf(waterFlowMeter);
		//Send Motor Stop Command
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendMotorStopCmd();
		stopTime = new Date();
		
		//Total Motor Running Time 
		Long totalONTime = stopTime.getTime()-startTime.getTime();
		totalONTime = totalONTime/60000;
		//Calculate Total Water Flown 
		Long totalWaterFlown = (totalONTime*waterFlowMeterValue)/100;
		
		System.out.println("Motor Start Time: "+ startTime);
		System.out.println("Motor Stop Time: "+ stopTime);
		System.out.println("Total Motor Running Time: "+ totalONTime);
		System.out.println("Flow Meter Value: "+ waterFlowMeter);
		System.out.println("Total Water Flown: "+ totalWaterFlown);
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("DateTime", df.format(new Date()));
		data.put("MotorStartTime", df.format(startTime));
		data.put("MotorStopTime", df.format(stopTime));
		data.put("TotalMotorRunningTime", totalONTime.toString());
		data.put("FlowMeterValue", waterFlowMeterValue.toString());
		data.put("TotalWaterFlown", totalWaterFlown.toString());
		
		try{
			mongoDaoImpl.insert("ActivityLog", data);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent("Motor stopped successfully");
        return res;
    }
	
	@RequestMapping("/reset")
    public ServerResponse resetAllSensors() {
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendResetCmd();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent("Sensors Reset successfully");
        return res;
    }
	
	@RequestMapping("/waterLevel")
    public ServerResponse checkWaterLevel() {
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendWaterLevelCmd();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
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
			
			int randomInt = waterlevel.intValue();
			randomInt = randomInt/3;		
			res.setContent(""+randomInt);
			Map<String, String> data = new HashMap<String, String>();	
			data.put("datetime", df.format(new Date()));
			data.put("value", ""+randomInt);
			try{
				mongoDaoImpl.insert("waterLevel", data);
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}
        return res;
    }	
	
	@RequestMapping("/flowMeter")
    public ServerResponse checkFlowMeter() {
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendWaterFlowMeterCmd();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String currStatus = SimpleRead.getCurrentStatus();
		currStatus.trim();
		System.out.println(currStatus);
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent(currStatus);
		Map<String, String> data = new HashMap<String, String>();	
		data.put("datetime", df.format(new Date()));
		data.put("value", currStatus);
		try{
			mongoDaoImpl.insert("waterFlowMeter", data);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
        return res;
    }
	
	@RequestMapping("/sensor1")
    public ServerResponse checkSensor1Status() {
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendSensor1Cmd();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String currStatus = SimpleRead.getCurrentStatus();
		currStatus.trim();
		System.out.println(currStatus);
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent(currStatus);
		Map<String, String> data = new HashMap<String, String>();	
		data.put("datetime", df.format(new Date()));
		data.put("value", currStatus);
		try{
			mongoDaoImpl.insert("sensor1", data);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
        return res;
    }
	
	@RequestMapping("/sensor2")
    public ServerResponse checkSensor2Status() {
		SimpleRead.resetCurrentStatus();
		SimpleRead.sendSensor2Cmd();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String currStatus = SimpleRead.getCurrentStatus();
		currStatus.trim();
		System.out.println(currStatus);
		ServerResponse res = new ServerResponse();
		res.setId("1");
		res.setContent(currStatus);
		Map<String, String> data = new HashMap<String, String>();	
		data.put("datetime", df.format(new Date()));
		data.put("value", currStatus);
		try{
			mongoDaoImpl.insert("sensor2", data);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
        return res;
    }

	@RequestMapping(value="/exportExcelToDB",method = RequestMethod.GET)
	public String getJSONfromExcel() {
		try {
			File file = new File("IDISServer.xls");
			Workbook workbook;

			workbook = Workbook.getWorkbook(file);

			// Get the first Sheet.
			Sheet sheet = workbook.getSheet(0);

			// Iterate through the rows.
			JSONArray jSONArray = new JSONArray();
			Cell[] titlesArray=sheet.getRow(0);
			
			for (int row = 1; row < sheet.getRows(); row++) {

				JSONObject colData = new JSONObject();

				Cell[] cellsArray=sheet.getRow(row);
		
				if(cellsArray!=null && titlesArray!=null){
					for(int i=0; i<titlesArray.length ;i++){			
						try {
							if(i<cellsArray.length){
								colData.put(titlesArray[i].getContents(), cellsArray[i].getContents());
							}else{
								colData.put(titlesArray[i].getContents(), "");
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				mongoDaoImpl.insertData("ActivityLog", colData);
				jSONArray.put(colData);
			}
			
			return jSONArray.toString();

		} catch (BiffException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) { 
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
}
