package org.vean.quartzs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

public class ScanDirectoryJob implements Job {
	
	static Log loger=LogFactory.getLog(ScanDirectoryJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stu
		JobDetail til= arg0.getJobDetail();
		System.out.println(1111);
		loger.info(til.getName());
	}

}
