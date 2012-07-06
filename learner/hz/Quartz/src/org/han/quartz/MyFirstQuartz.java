package org.han.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyFirstQuartz implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println(arg0.getJobRunTime());
		System.out.println(arg0.hashCode());
		System.out.println(arg0.getTrigger().getGroup());
		System.out.println(arg0.getTrigger().getName());
		System.out.println("Hello world");

	}
	public void test(){
		System.out.println("xxxxxxxxxxxxxxxxxxxxx"+new Date().getTime());
	}
}
