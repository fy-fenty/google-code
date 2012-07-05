package org.hzy.test;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class MyTest {
	  public static void startScheduler() throws Exception{
		  SchedulerFactory scfac=new StdSchedulerFactory();
		  
//			Scheduler scd=scfac.getScheduler();
		  Scheduler scd=StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail jobDetail=new JobDetail("a", "b", QuartzReport.class);//任务名，任务组，任务执行类  
			
			jobDetail.getJobDataMap().put("name", "lucyaaa");
			
			CronTrigger trigger=new CronTrigger("c","d");// 触发器名,触发器组  
			trigger.setCronExpression("0/1 * * * * ?");
			
			scd.scheduleJob(jobDetail,trigger);
			
			if (!scd.isShutdown()){  
				scd.start();  
	        } 
	  	}
	public static void main(String[] args) throws Exception {
		startScheduler();
	}
}
