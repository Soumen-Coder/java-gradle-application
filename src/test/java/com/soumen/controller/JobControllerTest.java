package com.soumen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soumen.configuration.ApplicationConfig;
import com.soumen.dto.JobDTO;
import com.soumen.model.ImmutableJob;
import com.soumen.model.Job;
import com.soumen.service.JobQueueService;
import com.soumen.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class JobControllerTest {
    @Mock
    private JobService jobService;
    @InjectMocks
    private JobController jobController;
    @Mock
    private JobQueueService jobQueueService;

    private static final String expectedJson = """
            {
              "id": "58274f9a-8ca0-483d-986b-958797164f22",
              "title": "Job Test",
              "status": "Pending"
            }""";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jobService = Mockito.mock(JobService.class);
        jobQueueService = Mockito.mock(JobQueueService.class);
        jobController = new JobController(jobService, jobQueueService);
    }

    @Test
    public void testGetJob() throws Exception {
        // Given
        String jobId = "58274f9a-8ca0-483d-986b-958797164f22";
        Job job = ImmutableJob.builder()
                .id(jobId)
                .title("Job Test")
                .status("Pending")
                .build();
        when(jobService.getJob(jobId)).thenReturn(job);
        // When
        CompletableFuture<Job> jobFuture = jobController.getJob(jobId);
        // Then
        Job actualJob = jobFuture.get();
        // Convert the actualJob to JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        String actualJobJson = objectMapper.writeValueAsString(actualJob);
        // Compare actualJob JSON with expectedJson
        JSONAssert.assertEquals(expectedJson, actualJobJson, true);

    }

    @Test
    public void testCreateJob() throws Exception {
        // Given
        JobDTO jobDTO = new JobDTO("createJOB");
        String jobId = "58274f9a-8ca0-483d-986b-958797164f22";
        Job job = ImmutableJob.builder()
                .id(jobId)
                .title("Created Job")
                .status("Created")
                .build();
        when(jobService.createJob(jobDTO)).thenReturn(job);
        // When
        CompletableFuture<Job> jobFuture = jobController.createJob(jobDTO);
        // Then
        Job actualJob = jobFuture.get();
        assertThat(actualJob).isEqualTo(job);
    }
}