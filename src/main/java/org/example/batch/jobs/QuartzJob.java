package org.example.batch.jobs;

import java.util.concurrent.TimeUnit;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzJob implements Job {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private org.springframework.batch.core.Job exampleJob; // 주입된 Spring Batch Job

	@Autowired
	private RedissonClient redissonClient; // Redisson 클라이언트 주입

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Redisson 분산 락을 설정합니다.
		RLock lock = redissonClient.getLock("quartzTaskletLock");

		boolean isLocked = false;
		try {
			// 락 획득 시도 (최대 5초 대기, 10초 동안 락 유지)
			isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);
			if (isLocked) {
				// 락이 성공적으로 획득되었을 경우 작업 실행
				JobParameters params = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis()) // 유니크한 파라미터 추가
					.toJobParameters();
				jobLauncher.run(exampleJob, params);
			} else {
				// 락 획득 실패 시 로그 또는 적절한 처리
				System.out.println("Lock not acquired. Skipping job execution.");
			}
		} catch (Exception e) {
			throw new JobExecutionException("Error executing batch job through Quartz with Redisson lock", e);
		} finally {
			if (isLocked) {
				lock.unlock(); // 락 해제
				System.out.println("Releasing lock...");
			}
		}
	}
}
