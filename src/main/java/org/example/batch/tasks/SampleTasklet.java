package org.example.batch.tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class SampleTasklet implements Tasklet {

	private static final String filePath = "input_file.txt"; // 파일 경로 지정

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

		try {
			File file = new File(filePath);

			// 파일이 존재하는 경우
			if (file.exists()) {
				List<String> lines = Files.readAllLines(Paths.get(filePath));
				System.out.println("Existing Content:");
				lines.forEach(System.out::println);
			} else {
				System.out.println("File does not exist. A new file will be created.");
			}

			// 파일에 1부터 10까지 추가, 0.5초 딜레이 포함
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true)); // true로 append 모드
			for (int i = 1; i <= 10; i++) {
				writer.write(i + " ");
				System.out.print(i);
				Thread.sleep(500); // 0.5초 대기
			}
			System.out.println();
			writer.flush(); // 즉시 파일에 쓰기
			writer.write("\n"); // 마지막에 줄 바꿈 추가
			writer.close();

			System.out.println("Numbers from 1 to 10 have been written/appended with a 0.5-second delay between each number.");
		} catch (IOException | InterruptedException e) {
			System.err.println("Error occurred: " + e.getMessage());
		}

		return RepeatStatus.FINISHED;
	}
}
