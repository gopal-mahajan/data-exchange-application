package com.data.kaveri.dataexchange.dto;

import lombok.Data;

@Data
public class DataDTO {
    private float currentLevel;
    private String id;
    private String observationTime;
    private float measuredDistance;
    private float referenceLevel;
}
