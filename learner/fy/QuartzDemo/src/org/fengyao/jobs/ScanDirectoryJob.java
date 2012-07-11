package org.fengyao.jobs;

import java.io.File;
import java.io.FileFilter;

import org.fengyao.filters.FileExtensionFileFilter;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScanDirectoryJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap dataMap = jobDetail.getJobDataMap();
		String dirName = dataMap.getString("SCAN_DIR");
		if (dirName == null) {
			throw new JobExecutionException("Directory not configured");
		}
		File dir = new File(dirName);
		if (!dir.exists()) {
			throw new JobExecutionException("Invalid dir " + dirName);
		}
		String suffix = ".xml";
		FileFilter filter = new FileExtensionFileFilter(suffix);
		File[] files = dir.listFiles(filter);
		if (files != null && files.length > 0) {
			int size = files.length;
			System.out.println("The suffix is " + suffix + " files have " + size);
		} else {
			System.out.println("No found match file");
		}
	}
}