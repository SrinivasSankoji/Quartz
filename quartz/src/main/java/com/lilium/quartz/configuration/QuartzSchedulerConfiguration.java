package com.lilium.quartz.configuration;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzSchedulerConfiguration {

    @Bean(name = "scheduler")
    public Scheduler quartzScheduler() throws SchedulerException {
        return new StdSchedulerFactory().getScheduler();
    }
}
