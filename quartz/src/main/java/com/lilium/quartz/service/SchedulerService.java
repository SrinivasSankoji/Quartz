package com.lilium.quartz.service;

import com.lilium.quartz.timer.SimpleTriggerListener;
import com.lilium.quartz.timer.TimerInfo;
import com.lilium.quartz.util.TimerUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.start();
        scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));
    }

    @PreDestroy
    public void destroy() throws SchedulerException {
        scheduler.shutdown();
    }

    public void schedule(final Class jobClass, final TimerInfo timerInfo) throws SchedulerException {
        JobDetail jobDetail = TimerUtil.buildJobDetail(jobClass, timerInfo);
        Trigger trigger = TimerUtil.buildTrigger(jobClass, timerInfo);
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public List<TimerInfo> getAllRunningJobs() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream().map(jobKey -> {
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (TimerInfo) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException schedulerException) {
                            logger.error(schedulerException.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (SchedulerException schedulerException) {
            logger.error(schedulerException.getMessage());
            return Collections.emptyList();
        }
    }

    public TimerInfo getRunningJob(String jobName) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobName));
        if (jobDetail == null) {
            return null;
        }
        return (TimerInfo) jobDetail.getJobDataMap().get(jobName);
    }

    public void updateJob(final String jobName, final TimerInfo timerInfo) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobName));
        if (jobDetail == null) {
            return;
        }
        jobDetail.getJobDataMap().put(jobName, timerInfo);
        scheduler.addJob(jobDetail, true, true);
    }

    public void deleteJob(final String jobName) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobName));
    }
}
