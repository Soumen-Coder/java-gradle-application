package com.soumen.service;

import com.soumen.model.Job;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class JobQueueService {
    private BlockingQueue<Job> jobQueue = new LinkedBlockingQueue<>();

    public void addJob(Job job) {
        jobQueue.offer(job);
    }

    public Job getNextJob() {
        return jobQueue.poll();
    }
}
