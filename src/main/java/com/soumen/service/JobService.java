package com.soumen.service;

import com.soumen.dto.JobDTO;
import com.soumen.exception.NotFoundException;
import com.soumen.model.ImmutableJob;
import com.soumen.model.Job;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JobService {
    private final Map<String, Job> jobRepository = new ConcurrentHashMap<>();

    public Job getJob(String id) {
        return jobRepository.get(id);
    }

    public Job createJob(JobDTO jobDTO) {
        Job newJob = ImmutableJob.builder()
                .id(UUID.randomUUID().toString())
                .title(jobDTO.title())
                .status("Created")
                .build();

        jobRepository.put(newJob.id(), newJob);
        return newJob;
    }

    public Job updateJob(String id, JobDTO jobDTO) {
        if (!jobRepository.containsKey(id)) {
            throw new NotFoundException("Job not found");
        }

        Job updatedJob = ImmutableJob.builder()
                .from(jobRepository.get(id))
                .title(jobDTO.title())
                .build();

        jobRepository.put(id, updatedJob);
        return updatedJob;
    }

    public void deleteJob(String id) {
        jobRepository.remove(id);
    }

    public Job cancelJob(String id) {
        Job job = jobRepository.get(id);

        if (job != null) {
            Job canceledJob = ImmutableJob.builder()
                    .from(job)
                    .status("Cancelled")
                    .build();

            jobRepository.put(id, canceledJob);
            return canceledJob;
        }

        return null;
    }
}
