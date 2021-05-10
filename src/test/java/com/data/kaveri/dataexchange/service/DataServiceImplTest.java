package com.data.kaveri.dataexchange.service;

import com.data.kaveri.dataexchange.dto.DataDTO;
import com.data.kaveri.dataexchange.entities.DataEntity;
import com.data.kaveri.dataexchange.exception.DataAlreadyExist;
import com.data.kaveri.dataexchange.exception.DataNotFoundException;
import com.data.kaveri.dataexchange.exception.InvalidInput;
import com.data.kaveri.dataexchange.repositories.DataRepository;
import com.data.kaveri.dataexchange.util.Utils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class DataServiceImplTest {

    @Mock
    private DataRepository dataRepository;

    @InjectMocks
    private DataServiceImpl dataService;

    @Test
    public void editDataTest() throws DataNotFoundException, InvalidInput {
        String dataId = "d1";
        Float currentLevel = 1.0f;
        String observationDateTime = LocalDateTime.now().toString();
        Float measuredDistance = 0.0f;
        Float referenceLevel = 0.0f;
        DataEntity data = null;

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(data);
        Assert.assertThrows(DataNotFoundException.class, () -> dataService.editData(dataId, currentLevel,
                observationDateTime, measuredDistance, referenceLevel));

        data = new DataEntity();
        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(data);
        dataService.editData(dataId, currentLevel,
                observationDateTime, measuredDistance, referenceLevel);

        data.setId(dataId);
        Assert.assertEquals(data.getCurrentLevel(), 1.0f, 0.0f);

    }

    @Test
    public void getById() throws DataNotFoundException {
        String dataId = "5";
        Float currentLevel = 1.0f;
        LocalDateTime observationDateTime = LocalDateTime.now();
        DataEntity data = null;

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(data);
        Assert.assertThrows("Data not found : ", DataNotFoundException.class, () -> dataService.getById(dataId));

        data = new DataEntity();
        data.setId(dataId);
        data.setCurrentLevel(currentLevel);
        data.setObservationTime(observationDateTime);

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(data);
        DataDTO dataDTO = dataService.getById(dataId);
        Assert.assertEquals(data.getId(), dataDTO.getId());
        Assert.assertEquals(data.getCurrentLevel(), dataDTO.getCurrentLevel(), 0.0f);
        Assert.assertEquals(data.getObservationTime(), dataDTO.getObservationTime());
    }

    @Test
    public void deleteData() throws DataNotFoundException {
        String dataId = "";
        Float currentLevel = 1.0f;
        LocalDateTime observationDateTime = LocalDateTime.now();
        Float measuredDistance = 0.0f;
        Float referenceLevel = 0.0f;
        DataEntity data = null;

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(data);
        Assert.assertThrows("Data does not exist: ", DataNotFoundException.class, () -> dataService.deleteData(dataId));

        data = new DataEntity();
        data.setDeleted(false);
        data.setReferenceLevel(referenceLevel);
        data.setId(dataId);
        data.setCurrentLevel(currentLevel);
        data.setObservationTime(observationDateTime);
        data.setMeasuredDistance(measuredDistance);

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(data);
        dataService.deleteData(dataId);
        Assert.assertEquals(data.isDeleted(), true);
    }

    @Test
    public void addData() throws DataAlreadyExist, InvalidInput {
        // DataDTO dataDto
        String dataId = "d1";
        Float currentLevel = 1.0f;
        String observationTime = LocalDateTime.now().toString();
        Float measuredDistance = 0.0f;
        Float referenceLevel = 0.0f;
        DataEntity data = new DataEntity();

        data.setReferenceLevel(referenceLevel);
        data.setId(dataId);
        data.setCurrentLevel(currentLevel);
        data.setObservationTime(Utils.parse(observationTime));
        data.setMeasuredDistance(measuredDistance);

        DataDTO data2 = new DataDTO();
        data2.setId("d1");
        data2.setReferenceLevel(referenceLevel);
        data2.setId(dataId);
        data2.setCurrentLevel(currentLevel);
        data2.setObservationTime(observationTime);
        data2.setMeasuredDistance(measuredDistance);
        Assert.assertEquals(data.getId(), "d1");
        Assert.assertThrows(NullPointerException.class, () -> dataService.addData(null));
    }

}
