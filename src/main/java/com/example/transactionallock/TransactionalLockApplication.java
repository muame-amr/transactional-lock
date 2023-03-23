package com.example.transactionallock;

import com.example.transactionallock.model.Flight;
import com.example.transactionallock.repository.FlightRepository;
import com.example.transactionallock.service.FlightService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class TransactionalLockApplication implements CommandLineRunner {

	@Resource
	private FlightService flightService;

	public static void main(String[] args) {
		SpringApplication.run(TransactionalLockApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(safeRunnable(flightService::changeFlight1));
		executor.execute(safeRunnable(flightService::changeFlight2));
		executor.shutdown();
	}

	@Bean
	CommandLineRunner runner(FlightRepository flightRepository) {
		return args -> {
			flightRepository.save(new Flight("FLT123", LocalDateTime.parse("2022-04-01T09:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME), 2));
			flightRepository.save(new Flight("FLT234", LocalDateTime.parse("2022-04-01T10:30:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME), 50));
		};
	}

	private Runnable safeRunnable(FailableRunnable<Exception> runnable) {
		return () -> {
			try {
				runnable.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
}
