package org.example;

import lombok.Data;

@Data
public class Analysis {
    String domainCode;
    Integer analysisCodeType;
    String analysisCode;
    Integer commentIndex;
    String description;
    Boolean isActive;
    Integer anMstrQADD01;
    String dataOperation;

}
