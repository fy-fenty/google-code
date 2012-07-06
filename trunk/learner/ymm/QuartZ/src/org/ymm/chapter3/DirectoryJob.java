package org.ymm.chapter3;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DirectoryJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail jobDetail = context.getJobDetail();  
		JobDataMap jdm=jobDetail.getJobDataMap();
		String uName=jdm.getString("uname");
		System.out.println(uName+" say hello1...");
	}

}
