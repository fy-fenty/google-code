package org.fengyao;

import java.io.File;
import java.util.Date;

import org.fengyao.jobs.PrintTriangleJob;
import org.fengyao.jobs.ScanDirectoryJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

public class Test {

	public void testScanDirectory() {
		Scheduler scheduler;
		try {
			scheduler = this.createScheduler();
			JobDetail jobDetail = new JobDetail(this.getClass().getName(), Scheduler.DEFAULT_GROUP,
					ScanDirectoryJob.class);
			JobDataMap dataMap = jobDetail.getJobDataMap();
			dataMap.put("SCAN_DIR", System.getProperty("user.dir") + File.separator + "src");
			Trigger trigger = TriggerUtils.makeSecondlyTrigger(5, 1);
			trigger.setName(jobDetail.getFullName() + " -Trigger");
			trigger.setStartTime(new Date());
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testPrintTriangle() {
		Scheduler scheduler;
		try {
			scheduler = this.createScheduler();
			JobDetail jobDetail = new JobDetail(this.getClass().getName(), Scheduler.DEFAULT_GROUP,
					PrintTriangleJob.class);
			JobDataMap dataMap = jobDetail.getJobDataMap();
			dataMap.put("lines", 5);
			Trigger trigger = TriggerUtils.makeSecondlyTrigger(1, 5);
			trigger.setName(jobDetail.getFullName() + " -Trigger");
			trigger.setStartTime(new Date());
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Scheduler createScheduler() throws SchedulerException {
		return StdSchedulerFactory.getDefaultScheduler();
	}

	public static void main(String[] args) throws SchedulerException {
		Scheduler du = new StdSchedulerFactory("quartz.properties").getScheduler();
		du.start();
	}
}