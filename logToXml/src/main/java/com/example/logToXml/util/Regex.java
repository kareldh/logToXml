package com.example.logToXml.util;

import com.example.logToXml.data.LogFileDAO;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
  Helper class with the precompiled regex patterns
 */
@Component
public class Regex {
    private final Pattern startRenderPattern;
    private final Pattern startRenderReturnPattern;
    private final Pattern getRenderPattern;

    public Regex() {
        String dateString = "(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3})";
        String startRenderString = "\\[WorkerThread-(\\d)] INFO\\s{2}\\[ServiceProvider]: Executing request startRendering with arguments \\[(\\d+), (\\d+)]";
        String startRenderReturnString = "\\[WorkerThread-(\\d)] INFO\\s{2}\\[ServiceProvider]: Service startRendering returned (.*)";
        String getRenderString = "\\[WorkerThread-(\\d)] INFO\\s{2}\\[ServiceProvider\\]: Executing request getRendering with arguments \\[(.*)\\]";

        startRenderPattern = Pattern.compile(dateString+".*"+startRenderString);
        startRenderReturnPattern = Pattern.compile(dateString+".*"+startRenderReturnString);
        getRenderPattern = Pattern.compile(dateString+".*"+getRenderString);
    }

    public void matchLine(String line, LogFileDAO logFileDAO){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss','SSS");
        Matcher startRenderMatcher = startRenderPattern.matcher(line);
        if(startRenderMatcher.find()){
            try{
                Date date = simpleDateFormat.parse(startRenderMatcher.group(1));
                logFileDAO.addStartRendering(Integer.parseInt(startRenderMatcher.group(3)),Integer.parseInt(startRenderMatcher.group(4)),Integer.parseInt(startRenderMatcher.group(2)),date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            Matcher startRenderReturnMatcher = startRenderReturnPattern.matcher(line);
            if(startRenderReturnMatcher.find()){
                logFileDAO.addUniqueReturnValue(startRenderReturnMatcher.group(3),Integer.parseInt(startRenderReturnMatcher.group(2)));
            }
            else {
                Matcher getRenderMatcher = getRenderPattern.matcher(line);
                if(getRenderMatcher.find()){
                    try{
                        Date date = simpleDateFormat.parse(getRenderMatcher.group(1));
                        logFileDAO.addGetRendering(getRenderMatcher.group(3),date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
