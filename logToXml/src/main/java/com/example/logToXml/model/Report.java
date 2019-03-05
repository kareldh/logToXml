package com.example.logToXml.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class Report {
    private Rendering[] renderings;
    private Summary summary;
}
