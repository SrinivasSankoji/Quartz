package com.lilium.quartz.util;

import com.lilium.quartz.timer.TimerInfo;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Date;

public final class TimerUtil {
    private TimerUtil() {
    }
    public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo timerInfo) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), timerInfo);
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }
    public static Trigger buildTrigger(final Class jobClass, final TimerInfo timerInfo){
        SimpleScheduleBuilder scheduler = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(timerInfo.getRepeatIntervalInMilliSeconds());
        if (timerInfo.isRunForever()) {
            scheduler = scheduler.repeatForever();
        } else {
            scheduler = scheduler.withRepeatCount(timerInfo.getTotalFireCount() - 1);
        }
        return TriggerBuilder.newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(scheduler)
                .startAt(new Date(System.currentTimeMillis()+timerInfo.getInitialOffsetInMilliSeconds()))
                .build();
    }
}

