package org.hzy.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzReport implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
//		System.out.println( "输出："+arg0.getJobDetail().getJobDataMap().get("name")+new Date());
		System.out.println("AAAAAAAAAAAAAAAAAAA");
	}
}
