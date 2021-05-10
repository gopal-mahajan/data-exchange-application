package com.data.kaveri.dataexchange.dto;

import lombok.Data;

@Data
public class DataDTO {
    private Float currentLevel;
    private String id;
    private String observationTime;
    private Float measuredDistance;
    private Float referenceLevel;
}
