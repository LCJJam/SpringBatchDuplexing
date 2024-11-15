package org.example.controller;

import java.util.Set;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

	private final JobExplorer jobExplorer;

	public BatchController(JobExplorer jobExplorer) {
		this.jobExplorer = jobExplorer;
	}

	@GetMapping("/running-jobs")
	public String findRunningJobs(@RequestParam String jobName) {
		Set<?> jobExecutions = jobExplorer.findRunningJobExecutions(jobName);
		if (jobExecutions.isEmpty()) {
			return "No running jobs found for job name: " + jobName;
		} else {
			return "Running jobs found: " + jobExecutions.size();
		}
	}
}
