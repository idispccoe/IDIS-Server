package hello;

import java.io.IOException;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SensorJob implements Job{
	
	private static boolean isJobRunning = false; 
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if(!isJobRunning){
			isJobRunning = true;
			System.out.println("Checking Sensor Status"+new Date());
			/*Sender sender = new Sender("AIzaSyD6eVL0f9fFEzq1i2mTDEIjp2oPIhO0h2I");
			Message message = new Message.Builder()
			 //.collapseKey(collapseKey)
			 .timeToLive(3)
			 .delayWhileIdle(true)
			 .dryRun(true)
			 .restrictedPackageName("edu.pccoe.idis")
			 .build();
			try {
				sender.send(message, "692387001179", 1);
				System.out.println("Push Notification Sent");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//System.out.println();
			//SimpleRead.writeData();
			boolean flag = true;
			while(flag){
				//Step 1 - Check Sensor1 Status
				SimpleRead.resetCurrentStatus();
				SimpleRead.sendSensor1Cmd();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String sensor1 = SimpleRead.getCurrentStatus();
				if(sensor1!=null && !sensor1.isEmpty()){
					try{
						Integer sensor1Reading = new Integer(sensor1);
						System.out.println("Sensor1 Reading: "+sensor1Reading);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//Step 2 - If Sensor1 Status is less than minimum moisture level, Check Water Level
						if(sensor1Reading>=200){
							//Step 3 - If Water level is more than 20%, Start Motor
							SimpleRead.resetCurrentStatus();
							SimpleRead.sendWaterLevelCmd();
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							String waterLevel = SimpleRead.getCurrentStatus();
							if(waterLevel!=null && !waterLevel.isEmpty()){
								Integer waterLevelReading = new Integer(waterLevel);
								System.out.println("Water Level Reading: "+waterLevelReading);
								if(waterLevelReading>=30){
									SimpleRead.resetCurrentStatus();
									SimpleRead.sendMotorStartCmd();
									System.out.println("Motor Running");
								}else{
									SimpleRead.resetCurrentStatus();
									SimpleRead.sendMotorStopCmd();
									flag = false;
									System.out.println("Motor Stopped");
									isJobRunning = false;
								}
							}
						}else if(sensor1Reading<200){
							SimpleRead.resetCurrentStatus();
							SimpleRead.sendMotorStopCmd();
							flag = false;
							System.out.println("Motor Stopped");
							isJobRunning = false;
						}
					}catch(NumberFormatException ex){
						System.out.println(ex);
					}
					//Step 4 - While Motor is running, sleep for 15 secs and repeat Step 1
					if(flag){
						System.out.println("Sleeping for 15 secs");
						try {
							Thread.sleep(15000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("Waking up now!!!");
					}
				}
			}
		}
	}
}
