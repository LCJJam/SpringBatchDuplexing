package org.example.batch.config;

import org.example.batch.listeners.JobCompletionListener;
import org.example.batch.tasks.SampleTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private final JobRepository jobRepository;

	@Autowired
	private final PlatformTransactionManager platformTransactionManager;

	@Autowired
	private final SampleTasklet sampleTasklet;

	@Autowired
	private final JobCompletionListener jobCompletionListener;

	public BatchConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
		SampleTasklet sampleTasklet, JobCompletionListener jobCompletionListener) {
		this.jobRepository = jobRepository;
		this.platformTransactionManager = platformTransactionManager;
		this.sampleTasklet = sampleTasklet;
		this.jobCompletionListener = jobCompletionListener;
	}

	@Bean
	public Job exampleJob(JobRepository jobRepository) {
		return new JobBuilder("exampleJob",jobRepository)
			.listener(jobCompletionListener)
			.start(exampleStep())
			.build();
	}

	@Bean
	public Step exampleStep() {
		return new StepBuilder("exampleStep",jobRepository)
			.tasklet(sampleTasklet, platformTransactionManager)
			.build();
	}

	@Bean
	public Tasklet exampleTasklet() {
		return (contribution, chunkContext) -> {
			System.out.println("Executing example tasklet...");
			return null;
		};
	}
}
