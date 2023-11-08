package com.soumen.controller;

import com.soumen.dto.JobDTO;
import com.soumen.model.Job;
import com.soumen.service.JobQueueService;
import com.soumen.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
public class JobController {
    private final JobService jobService;

    private final JobQueueService jobQueueService;

    @Autowired
    public JobController(JobService jobService, JobQueueService jobQueueService) {
        this.jobService = jobService;
        this.jobQueueService = jobQueueService;
    }

    @GetMapping(value = "/getJob/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<Job> getJob(@PathVariable String id) {
        System.out.println("Inside getJob Method with id : "+id);
        return CompletableFuture.supplyAsync(() -> jobService.getJob(id));
    }

    @PostMapping("/createJob")
    public CompletableFuture<Job> createJob(@RequestBody JobDTO jobDTO) {
        System.out.println("Controller is called with jobDTO: " + jobDTO);
        Job job = jobService.createJob(jobDTO);
        jobQueueService.addJob(job);

        System.out.println("Job created: " + job);

        return CompletableFuture.completedFuture(job);
    }

    @PutMapping("/updateJob/{id}")
    public CompletableFuture<Job> updateJob(@PathVariable String id, @RequestBody JobDTO jobDTO) {
        return CompletableFuture.supplyAsync(() -> jobService.updateJob(id, jobDTO));
    }

    @DeleteMapping("/deleteJob/{id}")
    public CompletableFuture<Void> deleteJob(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> {
            jobService.deleteJob(id);
            return null;
        });
    }

    @PostMapping("/cancelJob/{id}")
    public CompletableFuture<Job> cancelJob(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> jobService.cancelJob(id));
    }
}
