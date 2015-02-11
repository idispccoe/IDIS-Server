package hello;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SensorJobTrigger {
/*
	public static void main(String[] args) throws Exception{
		JobDetail job = JobBuilder.newJob(SensorJob.class)
				.withIdentity("sensorJob", "group1").build();

		// Trigger the job to run on the next round minute
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("sensorJobTrigger", "group1")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInMinutes(1).repeatForever())
						.build();

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}*/
}
