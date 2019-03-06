package com.example.logToXml.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Getter
@Setter
@XmlRootElement
public class Report {
    private Set<Rendering> renderings;
    private Summary summary;
}
