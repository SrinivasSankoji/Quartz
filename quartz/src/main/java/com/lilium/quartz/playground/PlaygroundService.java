package com.lilium.quartz.playground;

import com.lilium.quartz.job.HelloWorldJob;
import com.lilium.quartz.service.SchedulerService;
import com.lilium.quartz.timer.TimerInfo;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaygroundService {
    @Autowired
    private SchedulerService schedulerService;

    public void runHelloWorldJob() throws SchedulerException {
        TimerInfo timerInfo = TimerInfo.builder()
                .totalFireCount(5)
                .remainingFireCount(0)
                .repeatIntervalInMilliSeconds(2000)
                .initialOffsetInMilliSeconds(1000)
                .callbackData("Hello Quartz Scheduler")
                .build();
        timerInfo = timerInfo.toBuilder().remainingFireCount(timerInfo.getTotalFireCount()).build();
        schedulerService.schedule(HelloWorldJob.class, timerInfo);
    }

    public List<TimerInfo> getAllRunningJobs(){
        return schedulerService.getAllRunningJobs();
    }

    public TimerInfo getRunningJob(String jobName) throws SchedulerException {
        return schedulerService.getRunningJob(jobName);
    }

    public void deleteJob(String jobName) throws SchedulerException {
        schedulerService.deleteJob(jobName);
    }
}
