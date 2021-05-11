package com.data.kaveri.dataexchange.service;

import com.data.kaveri.dataexchange.dto.DataDTO;
import com.data.kaveri.dataexchange.entities.DataEntity;
import com.data.kaveri.dataexchange.exception.DataAlreadyExist;
import com.data.kaveri.dataexchange.exception.DataNotFoundException;
import com.data.kaveri.dataexchange.exception.InvalidInput;
import com.data.kaveri.dataexchange.repositories.DataRepository;
import com.data.kaveri.dataexchange.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataRepository dataRepository;

    @Override
    public void editData(String dataId, Float currentLevel, String observationDateTime,
                         Float measuredDistance, Float referenceLevel) throws DataNotFoundException, InvalidInput {
        DataEntity data = dataRepository.findByIdAndDeleted(dataId, false);
        if (data == null) {
            throw new DataNotFoundException(dataId);
        }
        if (currentLevel != null) {
            data.setCurrentLevel(currentLevel);
        }
        if (observationDateTime != null) {
            data.setObservationTime(Utils.parse(observationDateTime));
        }
        if (measuredDistance != null) {
            data.setMeasuredDistance(measuredDistance);
        }
        if (referenceLevel != null) {
            data.setReferenceLevel(referenceLevel);
        }
        data.setUpdatedAt(LocalDateTime.now());
        dataRepository.save(data);
    }

    @Override
    public DataDTO getById(String dataId) throws DataNotFoundException {
        DataEntity dataEntity = dataRepository.findByIdAndDeleted(dataId, false);
        if (dataEntity == null) {
            throw new DataNotFoundException(dataId);
        }
        return Utils.getDataDTO(dataEntity);
    }

    @Override
    public void deleteData(String dataId) throws DataNotFoundException {

        DataEntity data = dataRepository.findByIdAndDeleted(dataId, false);
        if (data == null) {
            throw new DataNotFoundException(dataId);
        }
        data.setUpdatedAt(LocalDateTime.now());
        data.setDeleted(true);
        dataRepository.save(data);
    }

    @Override
    public void addData(DataDTO dataDto) throws DataAlreadyExist, InvalidInput {
        DataEntity dataEntity;
        try {
            Utils.validateDataDTO(dataDto);
            if ((!dataRepository.existsById(dataDto.getId())) || dataRepository.getById(dataDto.getId()).isDeleted()) {
                dataEntity = Utils.getDataEntity(dataDto);
                dataRepository.save(dataEntity);
            }
            else {
                throw new DataAlreadyExist(dataDto.getId());
            }
        } catch (InvalidInput e) {
            throw e;
        }
//        catch (DataAlreadyExist dataAlreadyExist){
//            throw new DataAlreadyExist(dataDto.getId());
//        }
    }
}
