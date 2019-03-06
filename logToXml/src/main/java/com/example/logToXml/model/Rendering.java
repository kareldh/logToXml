package com.example.logToXml.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@XmlRootElement
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
    @XmlElement(name="start")
    Set<Date> startRenderings;

    /**
     * One or more timestamps of getRendering
     */
    @XmlElement(name="get")
    Set<Date> getRenderings;

    public Rendering() {
        this.startRenderings = new HashSet<>();
        this.getRenderings = new HashSet<>();
    }

    public void addStartRendering(Date date){
        if(!startRenderings.contains(date)){
            startRenderings.add(date);
        }
    }

    public void addGetRendering(Date date){
        if(!getRenderings.contains(date)){
            getRenderings.add(date);
        }
    }
}
