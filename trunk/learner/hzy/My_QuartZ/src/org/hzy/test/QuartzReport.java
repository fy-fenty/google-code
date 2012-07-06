package org.hzy.test;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

public class QuartzReport implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub	
		JobDetail jd=arg0.getJobDetail();
		
		String jdname=jd.getName();
		System.out.println(jdname+" "+jd.getGroup()+" "+jd.getJobClass());
		
		JobDataMap jmp=jd.getJobDataMap();
		
		String ss=jmp.getString("name");
		
		Trigger tr=arg0.getTrigger();
		System.out.println(tr.getJobName()+" "+tr.getName());
		
		System.out.println("AAAAAAAAAAAAAAAAAAA"+ss);
	}
}
