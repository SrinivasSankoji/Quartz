package com.lilium.quartz.controller;

import com.lilium.quartz.playground.PlaygroundService;
import com.lilium.quartz.timer.TimerInfo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api/timer")
public class QuartzController {

    @Autowired
    private PlaygroundService playgroundService;

    @GetMapping ("/run")
    public void runHelloWorld() throws SchedulerException {
        playgroundService.runHelloWorldJob();
    }

    @GetMapping
    public List<TimerInfo> getAllRunningJobs(){
        return playgroundService.getAllRunningJobs();
    }

    @GetMapping("/{jobName}")
    public TimerInfo getRunningJob(@PathVariable String jobName) throws SchedulerException {
        return playgroundService.getRunningJob(jobName);
    }

    @DeleteMapping("/{jobName}")
    public void deleteJob(@PathVariable String jobName) throws SchedulerException {
         playgroundService.deleteJob(jobName);
    }
}
