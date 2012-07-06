package org.fengyao.queatzs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScanDirectoryJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// System.out.print(" in ===========================");
		JobDetail jobDetail = context.getJobDetail();
		// String jobName = jobDetail.getName();
		// logger.info(jobName + " fired at " + new Date());
		JobDataMap dataMap = jobDetail.getJobDataMap();
		// String dirName = dataMap.getString("SCAN_DIR");
		// if (dirName == null) {
		// throw new JobExecutionException("Directory not configured");
		// }
		// File dir = new File(dirName);
		// if (!dir.exists()) {
		// throw new JobExecutionException("Invalid dir of" + dirName);
		// }
		// FileFilter filter = new FileExtensionFileFilter(".xml");
		// File files[] = dir.listFiles(filter);
		// if (files == null || files.length == 0) {
		// logger.info("no file found in" + dirName);
		// return;
		// }
		// int size = files.length;
		// System.out.println(size);
		// System.out.print(" out ===========================");
		System.out.println(dataMap.get("print"));
	}
}