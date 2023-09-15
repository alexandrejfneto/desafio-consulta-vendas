package com.devsuperior.dsmeta;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DsmetaApplication implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(DsmetaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//teste com as variáveis de data
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		System.out.println(today);
		LocalDate resultMinDate = today.minusYears(1L);
		System.out.println(resultMinDate);
		
	}
}
