package org.fengyao;

import java.util.Date;

import org.fengyao.queatzs.PrintTriangleJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

public class Test {

	public static void main(String[] args) {
		Test test = new Test();
		try {
			Scheduler scheduler = test.createScheduler();
			scheduler.start();
			test.schedulerJob(scheduler, "s1", 1, 5, 2);
			test.schedulerJob(scheduler, "s2", 2, 4, 1);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Scheduler createScheduler() throws SchedulerException {
		return StdSchedulerFactory.getDefaultScheduler();
	}

	private void schedulerJob(Scheduler scheduler, String jobDetailName, int interval, int lines, int count)
			throws SchedulerException {
		JobDetail jobDetail = new JobDetail(jobDetailName, Scheduler.DEFAULT_GROUP, PrintTriangleJob.class);
		JobDataMap dataMap = jobDetail.getJobDataMap();
		dataMap.put("lines", lines);
		Trigger trigger = TriggerUtils.makeSecondlyTrigger(interval, count);
		trigger.setName(jobDetail + " -Trigger");
		trigger.setStartTime(new Date());
		scheduler.scheduleJob(jobDetail, trigger);
	}
}