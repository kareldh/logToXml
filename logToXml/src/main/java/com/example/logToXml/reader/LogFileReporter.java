package com.example.logToXml.reader;

import com.example.logToXml.data.LogFileDAO;
import com.example.logToXml.model.Report;
import com.example.logToXml.model.Summary;
import com.example.logToXml.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class LogFileReporter {
    private final Regex regex;

    @Autowired
    public LogFileReporter(Regex regex) {
        this.regex = regex;
    }

    public Report reportLogFile(String fileLocation){
        LogFileDAO logFileDAO = new LogFileDAO();
        try (Stream<String> stream = Files.lines(Paths.get(fileLocation))) {
            stream.forEach(line -> regex.matchLine(line,logFileDAO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Report report = new Report();
        report.setRenderings(logFileDAO.getRenderings());
        Summary s = new Summary();
        s.setCount(report.getRenderings().size());
        s.setDuplicates(logFileDAO.getAmountOfDoubleRenderings());
        s.setUnnecessary(logFileDAO.getAmountOfStartRenderingsWithoutGet());
        report.setSummary(s);
        return report;
    }

}
