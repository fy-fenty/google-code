package org.tender.qz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleQuartzJob implements Job {

    public SimpleQuartzJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	System.out.println("this is frist to use corn");
        System.out.println("In SimpleQuartzJob - executing its JOB at " 
                + new Date() + " by " + context.getTrigger().getName());
    }

}
