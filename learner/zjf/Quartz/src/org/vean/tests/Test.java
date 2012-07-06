package org.vean.tests;

import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

public class Test{

	public static void main(String[] args) throws SchedulerException {
//		MyQuartz tz=new MyQuartz();
//		tz.startScheduler();
//		Scheduler sch= tz.createScheduler();
//		tz.schedulerJob(sch);
//		sch.start();
		 SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		 schedFact.getScheduler();
	
	}
}
