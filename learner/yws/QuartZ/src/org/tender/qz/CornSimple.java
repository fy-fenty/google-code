package org.tender.qz;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class CornSimple {
	static Log logger = LogFactory.getLog(CornSimple.class);   
	  
    public static void main(String[] args) {   
    	CornSimple example = new CornSimple();   
         example.runScheduler();  
    }   
 
    public void runScheduler() {   
         Scheduler scheduler = null;   
 
         try {   
             // Create a default instance of the Scheduler   
             scheduler = StdSchedulerFactory.getDefaultScheduler();   
             scheduler.start();   
             System.out.println("Corn used by my frist_____________");
             logger.info("Scheduler was started at " + new Date());   
 
             // Create the JobDetail   
             JobDetail jobDetail =   
                            new JobDetail("PrintInfoJob",   
                       Scheduler.DEFAULT_GROUP,   
                                   SimpleQuartzJob.class);   
 
             // Create a CronTrigger   
             try {   
                  // CronTrigger that fires @7:30am Mon - Fri   
                  CronTrigger trigger = new  
                                   CronTrigger("CronTrigger", null,   
                            "0 30 7 ? * MON-FRI");   
 
                  scheduler.scheduleJob(jobDetail, trigger);   
             } catch (ParseException ex) {   
                  logger.error("Error parsing cron expr", ex);   
 
             }   
 
 
        } catch (SchedulerException ex) {   
             logger.error(ex);   
       }   
   }   

}
