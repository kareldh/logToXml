package com.example.logToXml;

import com.example.logToXml.model.Report;
import com.example.logToXml.reader.LogFileReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogToXmlApplication implements CommandLineRunner{

	private final LogFileReporter logFileReporter;

	@Autowired
	public LogToXmlApplication(LogFileReporter logFileReporter) {
		this.logFileReporter = logFileReporter;
	}

	public static void main(String[] args) {
		SpringApplication.run(LogToXmlApplication.class, args);
	}

	@Override
	public void run(String... args){
		Report r = logFileReporter.reportLogFile("D:\\Schooldocumenten\\Github_projects\\server\\server.log");
	}
}
