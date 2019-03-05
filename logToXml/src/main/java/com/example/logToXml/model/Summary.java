package com.example.logToXml.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Summary {
    /**
     * Total number of renderings
     */
    private int count;

    /**
     * Number of double renderings (multiple starts with same UID)
     */
    private int duplicates;

    /**
     * Number of startRenderings without get
     */
    private int unnecessary;
}
