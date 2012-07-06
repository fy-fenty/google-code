package org.tender.qz;
import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.tender.qz.FileExtensionFileFilter;
public class ScanDirectoryJob {
	 static Log logger = LogFactory.getLog(ScanDirectoryJob.class);       
     
     public void execute(JobExecutionContext context)       
               throws JobExecutionException {       
      
          // Every job has its own job detail       
          JobDetail jobDetail = context.getJobDetail();       
      
          // The name is defined in the job definition       
          String jobName = jobDetail.getName();       
      
          // Log the time the job started       
          logger.info(jobName + " fired at " + new Date());       
      
          // The directory to scan is stored in the job map       
          JobDataMap dataMap = jobDetail.getJobDataMap();       
                    String dirName = dataMap.getString("SCAN_DIR");       
      
          // Validate the required input       
          if (dirName == null) {       
               throw new JobExecutionException( "Directory not configured" );       
          }       
      
          // Make sure the directory exists       
          File dir = new File(dirName);       
          if (!dir.exists()) {       
           throw new JobExecutionException( "Invalid Dir "+ dirName);       
          }       
      
          // Use FileFilter to get only XML files       
          FileFilter filter = new FileExtensionFileFilter(".xml");       
      
          File[] files = dir.listFiles(filter);       
      
          if (files == null || files.length <= 0) {       
               logger.info("No XML files found in " + dir);       
      
               // Return since there were no files       
               return;       
          }       
      
          // The number of XML files       
          int size = files.length;       
      
          // Iterate through the files found       
          for (int i = 0; i < size; i++) {       
      
               File file = files[i];       
      
               // Log something interesting about each file.       
               File aFile = file.getAbsoluteFile();       
               long fileSize = file.length();       
               String msg = aFile + " - Size: " + fileSize;       
               logger.info(msg);       
          }       
     }       

}
