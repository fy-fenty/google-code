package org.vean.quartzs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

public class MyQuartz{

	static Log loger=LogFactory.getLog(MyQuartz.class);
	public void startScheduler(){
		Scheduler sch=null;
		
		try {
			sch=StdSchedulerFactory.getDefaultScheduler();
			modify(sch);
//			sch.start();
			loger.info("Ok");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void modify(Scheduler sch){
		try {
			if(sch.isInStandbyMode()){
				sch.standby();
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Scheduler createScheduler() throws SchedulerException{
		return StdSchedulerFactory.getDefaultScheduler();
	}
	
	public void schedulerJob(Scheduler sch) throws SchedulerException{

		JobDetail detail=new JobDetail("SchDirtory",sch.DEFAULT_GROUP,ScanDirectoryJob.class);
		Trigger trigger=TriggerUtils.makeSecondlyTrigger(10);
		trigger.setName("scanTrigger");  
		trigger.setStartTime(new Date());
		sch.scheduleJob(detail,trigger);
	}
}
