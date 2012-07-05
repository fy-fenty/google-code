package org.han.test;

import java.text.ParseException;

import org.han.quartz.MyFirstQuartz;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class Test {

	/**
	 * @param args
	 * @throws SchedulerException
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws SchedulerException, ParseException {
		// TODO Auto-generated method stub
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail jobDetail = new JobDetail("myJob",
				Scheduler.DEFAULT_GROUP, MyFirstQuartz.class);
		SimpleTrigger simpleTrigger = new SimpleTrigger("trigger", "tgroup");
		simpleTrigger.setRepeatInterval(1000);
		simpleTrigger.setRepeatCount(10);
		scheduler.scheduleJob(jobDetail, simpleTrigger);
		scheduler.start();
	}

}
