package org.fengyao.jobs;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

public class PrintTriangleJob implements StatefulJob {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap dataMap = jobDetail.getJobDataMap();
		int lines = dataMap.getInt("lines");
		String[] strs = new String[lines];
		int spaces = 0;
		System.out.println("------------------------------------");
		for (int i = lines; i > 0; i--, spaces++) {
			strs[i - 1] = "";
			for (int j = 0; j < spaces; j++) {
				strs[i - 1] += " ";
			}
			for (int j = 0; j < i * 2 - 1; j++) {
				strs[i - 1] += "*";
			}
		}
		for (int i = 0; i < strs.length; i++) {
			System.out.println(strs[i]);
		}
		dataMap.put("lines", ++lines);
		System.out.println("------------------------------------");
	}
}