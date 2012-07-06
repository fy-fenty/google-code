package org.tender.qz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
/*
 * 运行 ScanDirectoryJob 的多个实例

 */

public class Listing_3_6 {
	 static Log logger = LogFactory.getLog(Listing_3_6.class);       
     
     public static void main(String[] args) {       
          Listing_3_6 example = new Listing_3_6();       
      
          try {       
               // Create a Scheduler and schedule the Job       
               Scheduler scheduler = example.createScheduler();       
      
               // Jobs can be scheduled after Scheduler is running       
               scheduler.start();       
      
               logger.info("Scheduler started at " + new Date());       
      
               // Schedule the first Job       
               example.scheduleJob(scheduler, "ScanDirectory1",       
                         ScanDirectoryJob.class,       
                                     "c:\\quartz-book\\input", 10);       
      
               // Schedule the second Job       
               example.scheduleJob(scheduler, "ScanDirectory2",       
                         ScanDirectoryJob.class,       
                               "c:\\quartz-book\\input2", 15);       
      
          } catch (SchedulerException ex) {       
               logger.error(ex);       
          }       
     }       
      
     /*     
      * return an instance of the Scheduler from the factory     
      */      
     public Scheduler createScheduler() throws SchedulerException {       
          return StdSchedulerFactory.getDefaultScheduler();       
     }       
      
     // Create and Schedule a ScanDirectoryJob with the Scheduler       
     private void scheduleJob(Scheduler scheduler, String jobName,       
               Class jobClass, String scanDir, int scanInterval)       
               throws SchedulerException {       
      
           // Create a JobDetail for the Job       
          JobDetail jobDetail =       
                       new JobDetail(jobName,       
                              Scheduler.DEFAULT_GROUP, jobClass);       
      
          // Configure the directory to scan       
          jobDetail.getJobDataMap().put("SCAN_DIR", scanDir);       
      
          // Trigger that repeats every "scanInterval" secs forever       
          Trigger trigger =       
                      TriggerUtils.makeSecondlyTrigger(scanInterval);       
      
          trigger.setName(jobName + "-Trigger");       
      
          // Start the trigger firing from now       
          trigger.setStartTime(new Date());       
      
          // Associate the trigger with the job in the scheduler       
          scheduler.scheduleJob(jobDetail, trigger);       
     }       

}
