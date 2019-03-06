package com.example.logToXml;

import com.example.logToXml.model.Report;
import com.example.logToXml.app.LogFileReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

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
		if(args == null || args.length < 1){
			System.out.println("No arguments given: use 'LogToXmlApplication.jar <logfile>' or 'LogToXmlApplication.jar <logfile> <output directory>'");
		}
		else{
			Report r = logFileReporter.reportLogFile(args[0]);
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				if(args.length==1){
					jaxbMarshaller.marshal(r,System.out);
				}
				else{
					jaxbMarshaller.marshal(r,new File(args[1]));
				}
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}
}
