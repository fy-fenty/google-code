package org.ymm.test;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.ymm.chapter3.DirectoryJob;


public class QuartZTest {
	public static void main(String[] args) {
		QuartZTest qt=new QuartZTest();
		try {
			Scheduler sch =StdSchedulerFactory.getDefaultScheduler();
			qt.schJob(sch);
			sch.start();
			System.out.println("start:"+new Date());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void schJob(Scheduler sch) throws SchedulerException{
		JobDetail jobDetail=new JobDetail("DirectoryJob",Scheduler.DEFAULT_GROUP,DirectoryJob.class);  
		
		Trigger trigger=TriggerUtils.makeSecondlyTrigger(5);
		
		trigger.setName("DirectoryJob" + "-Trigger");       
	           
        trigger.setStartTime(new Date());       
        trigger.setEndTime( new Date(new Date().getTime() + 30000));
        sch.scheduleJob(jobDetail, trigger);
		      
	}
}
