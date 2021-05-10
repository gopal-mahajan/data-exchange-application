package com.data.kaveri.dataexchange.util;

import com.data.kaveri.dataexchange.dto.DataDTO;
import com.data.kaveri.dataexchange.entities.DataEntity;
import com.data.kaveri.dataexchange.exception.DataAlreadyExist;
import com.data.kaveri.dataexchange.exception.InvalidInput;
import com.data.kaveri.dataexchange.repositories.DataRepository;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Utils {

    public DataEntity getDataEntity(DataDTO dataDTO) throws InvalidInput {
        DataEntity dataEntity = new DataEntity();
        dataEntity.setCreatedAt(LocalDateTime.now());
        return getDataEntity(dataDTO, dataEntity);
    }

    public DataEntity getDataEntity(DataDTO dataDTO, DataEntity dataEntity) throws InvalidInput {
        dataEntity.setReferenceLevel(dataDTO.getReferenceLevel());
        dataEntity.setMeasuredDistance(dataDTO.getMeasuredDistance());
        dataEntity.setCurrentLevel(dataDTO.getCurrentLevel());
        dataEntity.setObservationTime(parse(dataDTO.getObservationTime()));
        dataEntity.setId(dataDTO.getId());
        dataEntity.setDeleted(false);
        dataEntity.setUpdatedAt(LocalDateTime.now());
        return dataEntity;
    }

    public DataDTO getDataDTO(DataEntity dataEntity) {
        DataDTO dataDTO = new DataDTO();
        dataDTO.setCurrentLevel(dataEntity.getCurrentLevel());
        dataDTO.setMeasuredDistance(dataEntity.getMeasuredDistance());
        dataDTO.setObservationTime(dataEntity.getObservationTime().toString());
        dataDTO.setId(dataEntity.getId());
        dataDTO.setReferenceLevel(dataEntity.getReferenceLevel());
        return dataDTO;
    }

    public LocalDateTime parse(String localDateTimeOfString) throws InvalidInput {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        try {
            return LocalDateTime.parse(localDateTimeOfString, formatter);
        } catch (Exception e) {
            throw new InvalidInput("Invalid DataTime Format. Please provide it in ISO format.");
        }
    }

    public void validateDataDTO(DataDTO dataDTO) throws InvalidInput, DataAlreadyExist {
        if (StringUtils.isEmpty(dataDTO.getId())
                || dataDTO.getCurrentLevel() == null
                || dataDTO.getReferenceLevel() == null
                || dataDTO.getMeasuredDistance() == null
                || dataDTO.getObservationTime() == null) {
            throw new InvalidInput("Data cannot be null");
        }
        LocalDateTime observationTime = parse(dataDTO.getObservationTime());
        if (!observationTime.isBefore(LocalDateTime.now())) {
            throw new InvalidInput("Observation time should be in past");
        }

    }
}

