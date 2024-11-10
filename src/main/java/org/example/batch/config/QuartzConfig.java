package org.example.batch.config;

import org.example.batch.jobs.QuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

	@Bean
	public JobDetail quartzJobDetail() {
		JobKey jobKey = new JobKey(String.valueOf(System.currentTimeMillis()), "quartzJob");
		return JobBuilder.newJob(QuartzJob.class)
			.withIdentity(jobKey)
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger quartzJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(quartzJobDetail())
			.withIdentity("quartzJobTrigger")
			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(5) // 5초마다 실행
				.repeatForever())
			.build();
	}
}
