package org.hzy.test;

import java.util.Date;

import org.hzy.tools.QuartzManager;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

public class MyTest {
	public static void startScheduler() throws Exception {
		// SchedulerFactory scfac=new StdSchedulerFactory();

		// Scheduler scd=scfac.getScheduler();
		Scheduler scd = StdSchedulerFactory.getDefaultScheduler();

		JobDetail jobDetail = new JobDetail("a", "b", QuartzReport.class);

		jobDetail.getJobDataMap().put("name", "aabb");

		Trigger trigger = TriggerUtils.makeSecondlyTrigger(10);// 隔10秒
		trigger.setName("as");
		trigger.setStartTime(new Date());

		// CronTrigger trigger=new CronTrigger("c","d","0/1 * * * * ?");
		// CronTrigger trigger=new CronTrigger("c","d");
		// trigger.setCronExpression("0/1 * * * * ?");

		scd.scheduleJob(jobDetail, trigger);

		scd.start();
		modifySchd(scd);
//		scd.shutdown();
	}

	private static void modifySchd(Scheduler scheduler) {
		try {
			if (!scheduler.isInStandbyMode()) {
				scheduler.standby();
			}
//			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		startScheduler();
	}
}
