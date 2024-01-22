package com.lilium.quartz.job;

import com.lilium.quartz.timer.TimerInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldJob implements Job {
    private static final Logger logger= LoggerFactory.getLogger(HelloWorldJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        TimerInfo timerInfo= (TimerInfo) jobDataMap.get(HelloWorldJob.class.getSimpleName());
        logger.info("Remaining fire count is "+timerInfo.getRemainingFireCount());
    }
}
