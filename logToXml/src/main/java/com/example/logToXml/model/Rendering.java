package com.example.logToXml.model;

import com.example.logToXml.format.DateAdapter;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Rendering {
    /**
     * Document id
     */
    private int document;

    /**
     * UID of the startRendering
     */
    private String uid;

    /**
     * One or more timestamps of startRendering
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "start")
    Set<Date> starts;

    /**
     * One or more timestamps of getRendering
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "get")
    Set<Date> gets;

    public Rendering() {
        this.starts = new HashSet<>();
        this.gets = new HashSet<>();
    }

    public void addStartRendering(Date date){
        if(!starts.contains(date)){
            starts.add(date);
        }
    }

    public void addGetRendering(Date date){
        if(!gets.contains(date)){
            gets.add(date);
        }
    }
}
