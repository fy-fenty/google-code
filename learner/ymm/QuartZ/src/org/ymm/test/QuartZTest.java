package org.ymm.test;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.ymm.chapter3.DirectoryJob;
import org.ymm.chapter3.DirectoryJob2;


public class QuartZTest {
	public static void main(String[] args) {
		QuartZTest qt=new QuartZTest();
		try {
			Scheduler sch =StdSchedulerFactory.getDefaultScheduler();
			sch.start();
			qt.schJob(sch,"DirectoryJob",DirectoryJob.class,"yy",5);
			//qt.schJob(sch,"DirectoryJob2",DirectoryJob2.class,"c:\\quartz-book\\input2",8);
			System.out.println("start:"+new Date());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void schJob(Scheduler sch,String jobName,Class jobclass,String name,Integer scanInterval) throws SchedulerException{
		
		JobDetail jobDetail=new JobDetail(jobName,Scheduler.DEFAULT_GROUP,jobclass);  
		
		jobDetail.getJobDataMap().put("uname", name);
		
		Trigger trigger=TriggerUtils.makeSecondlyTrigger(scanInterval);
		
		trigger.setName("DirectoryJob" + "-Trigger");

        trigger.setStartTime(new Date()); 

        //得到当前时间
        Calendar nTime = Calendar.getInstance();
        nTime.add(Calendar.MINUTE, 1);
		trigger.setEndTime(nTime.getTime());
       // trigger.setEndTime(new Date(new Date().getTime() + 30000));
        
		sch.scheduleJob(jobDetail, trigger);
	}
}
