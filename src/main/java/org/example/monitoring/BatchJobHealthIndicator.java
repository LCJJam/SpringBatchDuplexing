package org.example.monitoring;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class BatchJobHealthIndicator implements HealthIndicator {

	private final JobExplorer jobExplorer;

	public BatchJobHealthIndicator(JobExplorer jobExplorer) {
		this.jobExplorer = jobExplorer;
	}

	@Override
	public Health health() {
		var jobInstances = jobExplorer.findRunningJobExecutions("exampleJob");
		if (jobInstances.isEmpty()) {
			return Health.up().withDetail("Job Status", "No running jobs").build();
		} else {
			return Health.down().withDetail("Job Status", "Job is currently running").build();
		}
	}
}
