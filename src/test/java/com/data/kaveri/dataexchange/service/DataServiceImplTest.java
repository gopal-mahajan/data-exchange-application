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
        Float measuredDistance =1.2f;
        Float referenceLevel = 2.5f;
        DataEntity dataEntity = null;

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(dataEntity);
        Assert.assertThrows(DataNotFoundException.class, () -> dataService.editData(dataId, currentLevel,
                observationDateTime, measuredDistance, referenceLevel));

        dataEntity = new DataEntity();
        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(dataEntity);
        dataService.editData(dataId, currentLevel,observationDateTime, measuredDistance, referenceLevel);

        dataEntity.setId(dataId);
        Assert.assertEquals(dataEntity.getCurrentLevel(), 1.0f, 0.0f);

    }

    @Test
    public void getById() throws DataNotFoundException {
        String dataId = "d1";
        Float currentLevel = 1.0f;
        LocalDateTime observationDateTime = LocalDateTime.now().withNano(0);
        Float measuredDistance =1.2f;
        Float referenceLevel = 2.5f;
        DataEntity dataEntity = null;

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(dataEntity);
        Assert.assertThrows( DataNotFoundException.class, () -> dataService.getById(dataId));

        dataEntity = new DataEntity();
        dataEntity.setId(dataId);
        dataEntity.setCurrentLevel(currentLevel);
        dataEntity.setObservationTime(LocalDateTime.now().withNano(0));

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(dataEntity);
        DataDTO dataDTO = dataService.getById(dataId);
        Assert.assertEquals(dataEntity.getId(), dataDTO.getId());
        Assert.assertEquals(dataEntity.getCurrentLevel(), dataDTO.getCurrentLevel(), 0.0f);
        Assert.assertEquals(dataEntity.getObservationTime().minusNanos(0).toString(), dataDTO.getObservationTime());
    }

    @Test
    public void deleteData() throws DataNotFoundException {
        String dataId = "d1";
        Float currentLevel = 1.0f;
        LocalDateTime observationDateTime = LocalDateTime.now();
        Float measuredDistance =1.2f;
        Float referenceLevel = 2.5f;
        DataEntity dataEntity = null;

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(dataEntity);
        Assert.assertThrows( DataNotFoundException.class, () -> dataService.deleteData(dataId));

        dataEntity = new DataEntity();
        dataEntity.setDeleted(false);
        dataEntity.setReferenceLevel(referenceLevel);
        dataEntity.setId(dataId);
        dataEntity.setCurrentLevel(currentLevel);
        dataEntity.setObservationTime(observationDateTime);
        dataEntity.setMeasuredDistance(measuredDistance);

        Mockito.when(dataRepository.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(dataEntity);
        dataService.deleteData(dataId);
        Assert.assertEquals(dataEntity.isDeleted(), true);
    }

    @Test
    public void addData() throws DataAlreadyExist, InvalidInput {
        String dataId = "d1";
        Float currentLevel = 1.0f;
        String observationTime = LocalDateTime.now().toString();
        Float measuredDistance = 0.0f;
        Float referenceLevel = 0.0f;
        DataEntity dataEntity = new DataEntity();

        dataEntity.setReferenceLevel(referenceLevel);
        dataEntity.setId(dataId);
        dataEntity.setCurrentLevel(currentLevel);
        dataEntity.setObservationTime(Utils.parse(observationTime));
        dataEntity.setMeasuredDistance(measuredDistance);

        DataDTO dataDTO=Utils.getDataDTO(dataEntity);
        dataService.addData(dataDTO);
//        Assert.assertThrows(InvalidInput.class, ()->dataDTO.getObservationTime());

        DataDTO data2 = new DataDTO();
        data2.setId("d1");
        data2.setReferenceLevel(referenceLevel);
        data2.setId(dataId);
        data2.setCurrentLevel(currentLevel);
        data2.setObservationTime(observationTime);
        data2.setMeasuredDistance(measuredDistance);
        Assert.assertEquals(dataEntity.getId(), "d1");
        Assert.assertThrows(NullPointerException.class, () -> dataService.addData(null));
    }

}
