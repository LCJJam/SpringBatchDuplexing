# Spring Batch 이중화 분산락 ( Redisson 활용 )

- Spring 5.0^
- Java 17 Requirement
- Redisson Used ( Distributed Lock )


## Test

- Input_file.txt  ( 1...10 )
- Enviroment
  * testdb=1;port=8080
  * testdb=2;port=8081


## 추가 예정 사항

- JobExplorer 를 이용한 Job 실행 내역 모니터링 (Metric)
- 특정 Job 에 대한 API 모듈화
