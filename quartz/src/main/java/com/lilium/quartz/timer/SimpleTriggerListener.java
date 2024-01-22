package com.lilium.quartz.timer;

import com.lilium.quartz.service.SchedulerService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class SimpleTriggerListener implements TriggerListener {

    private SchedulerService schedulerService;

    public SimpleTriggerListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        String jobName=trigger.getKey().getName();
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        TimerInfo timerInfo= (TimerInfo) jobDataMap.get(jobName);
        try {
            if (!timerInfo.isRunForever()) {
                int remainingFireCount = timerInfo.getRemainingFireCount();
                if(remainingFireCount==0){
                    return;
                }
                timerInfo = timerInfo.toBuilder().remainingFireCount(remainingFireCount - 1).build();
            }
            schedulerService.updateJob(jobName, timerInfo);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
