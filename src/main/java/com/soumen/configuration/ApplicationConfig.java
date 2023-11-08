package com.soumen.configuration;

import com.soumen.service.JobQueueService;
import com.soumen.service.JobService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig
{
    @Bean("jobService")
    public JobService jobService() {
        return new JobService();
    }

    @Bean("jobQueueService")
    public JobQueueService jobQueueService() {
        return new JobQueueService();
    }
}