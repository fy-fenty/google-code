package org.tender.qz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
/*
 * 编程式安排一个 Job
 */
public class Listing_3_5  {
	static Log logger = LogFactory.getLog(Listing_3_5.class);       
    
    public static void main(String[] args) {       
         Listing_3_5 example = new Listing_3_5();       
     
         try {       
              // Create a Scheduler and schedule the Job       
              Scheduler scheduler = example.createScheduler();       
              example.scheduleJob(scheduler);       
     
              // Start the Scheduler running       
              scheduler.start();       
     
              logger.info( "Scheduler started at " + new Date() );   
     
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
   private void scheduleJob(Scheduler scheduler)       
        throws SchedulerException {       
     
        // Create a JobDetail for the Job       
        JobDetail jobDetail =       
                      new JobDetail("ScanDirectory",       
                  Scheduler.DEFAULT_GROUP,       
                            ScanDirectoryJob.class);       
     
        // Configure the directory to scan       
        jobDetail.getJobDataMap().put("SCAN_DIR",       
                  "c:\\quartz-book\\input");       
     
        // Create a trigger that fires every 10 seconds, forever       
        Trigger trigger = TriggerUtils.makeSecondlyTrigger(10);       
        trigger.setName("scanTrigger");       
        // Start the trigger firing from now       
        trigger.setStartTime(new Date());       
     
        // Associate the trigger with the job in the scheduler       
        scheduler.scheduleJob(jobDetail, trigger);       
   }       

}
