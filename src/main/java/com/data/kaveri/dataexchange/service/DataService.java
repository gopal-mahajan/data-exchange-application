package com.data.kaveri.dataexchange.service;

import com.data.kaveri.dataexchange.dto.DataDTO;
import com.data.kaveri.dataexchange.exception.DataAlreadyExist;
import com.data.kaveri.dataexchange.exception.DataNotFoundException;
import com.data.kaveri.dataexchange.exception.InvalidInput;

public interface DataService {
    void editData(String dataId, Float currentLevel, String observationDateTime,
                  Float measuredDistance, Float referenceLevel) throws DataNotFoundException, InvalidInput;

    DataDTO getById(String dataId) throws DataNotFoundException;

    void deleteData(String dataId) throws DataNotFoundException;

    void addData(DataDTO dataDto) throws DataAlreadyExist, InvalidInput;
}
