package hello;

import java.io.IOException;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SensorJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Checking Sensor Status"+new Date());
		Sender sender = new Sender("AIzaSyD6eVL0f9fFEzq1i2mTDEIjp2oPIhO0h2I");
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
		}
		//System.out.println();
		//SimpleRead.writeData();
	}

}
