package org.hzy.test;

import java.util.Date;

import org.hzy.tools.QuartzManager;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

public class MyTest {
	  public static void startScheduler() throws Exception{
		  SchedulerFactory scfac=new StdSchedulerFactory();
		  
//			Scheduler scd=scfac.getScheduler();
		  Scheduler scd=StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail jobDetail=new JobDetail("a", "b", QuartzReport.class);//任务名，任务组，任务执行类  
			
			jobDetail.getJobDataMap().put("name", "aabb");
			
			Trigger trigger=TriggerUtils.makeSecondlyTrigger(10);
			trigger.setName("as");
			trigger.setStartTime(new Date());
			
//			CronTrigger trigger=new CronTrigger("c","d","0/1 * * * * ?");
//			CronTrigger trigger=new CronTrigger("c","d");
//			trigger.setCronExpression("0/1 * * * * ?");
			
			
			scd.scheduleJob(jobDetail,trigger);		

			if (!scd.isShutdown()){  
				scd.start();  
	        } 
	  	}
	  
	public static void main(String[] args) throws Exception {
		startScheduler();	
	}
}
