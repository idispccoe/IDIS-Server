package hello;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SensorJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Checking Sensor Status"+new Date());
		//System.out.println();
		//SimpleRead.writeData();
	}

}
