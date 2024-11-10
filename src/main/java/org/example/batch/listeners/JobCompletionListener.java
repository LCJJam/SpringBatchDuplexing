package org.example.batch.listeners;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Job is about to start...");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("Job has finished.");
	}
}
