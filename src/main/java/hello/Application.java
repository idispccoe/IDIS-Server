package hello;

import gnu.io.CommPortIdentifier;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Application {
	
	public static void main(String[] args) {
		/*
		SimpleRead.portList = CommPortIdentifier.getPortIdentifiers();

        while (SimpleRead.portList.hasMoreElements()) {
        	SimpleRead.portId = (CommPortIdentifier) SimpleRead.portList.nextElement();
            if (SimpleRead.portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                 if (SimpleRead.portId.getName().equals("COM4")) {
                    SimpleRead reader = new SimpleRead();
                }
            }
        }
		
		JobDetail job = JobBuilder.newJob(SensorJob.class)
				.withIdentity("sensorJob", "group1").build();

		// Trigger the job to run on the next round minute
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("sensorJobTrigger", "group1")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInMinutes(10).repeatForever())
						.build();

		// schedule it
		try {
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			System.out.println("Could not start SensorJobScheduler!!!");
			e.printStackTrace();
		}*/
        SpringApplication.run(Application.class, args);
    }
}
