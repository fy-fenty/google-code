package org.fengyao.queatzs;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleScheduler {

	public static void main(String[] args) {
		SimpleScheduler t = new SimpleScheduler();
		t.start();
	}

	public void start() {
		Scheduler scheduler;
		try {
			System.out.println("init");
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			System.out.println("begining");
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}