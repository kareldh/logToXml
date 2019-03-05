package com.example.logToXml.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

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
    Date[] startRenderings;

    /**
     * One or more timestamps of getRendering
     */
    @XmlElement(name="get")
    Date[] getRenderings;
}
