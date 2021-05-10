package com.data.kaveri.dataexchange.controller;


import com.data.kaveri.dataexchange.dto.DataDTO;
import com.data.kaveri.dataexchange.exception.DataAlreadyExist;
import com.data.kaveri.dataexchange.exception.DataNotFoundException;
import com.data.kaveri.dataexchange.exception.InvalidInput;
import com.data.kaveri.dataexchange.service.DataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping()
    public ResponseEntity getData(@RequestParam("data_id") String dataId) {
        try {
            return ResponseEntity.ok(dataService.getById(dataId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addData(@RequestBody DataDTO dataDTO) {
        try {
            dataService.addData(dataDTO);
            return ResponseEntity.ok("Entity Created with ID " + dataDTO.getId());
        } catch (DataAlreadyExist | InvalidInput e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @ApiOperation("Date format should be ISO Format")
    public ResponseEntity<String> editData(@RequestParam("data_id") String dataId,
                                           @RequestParam(value = "current_level", required = false) Float currentLevel,
                                           @RequestParam(value = "observation_date_time", required = false) String observationDateTime,
                                           @RequestParam(value = "reference_level", required = false) Float referenceLevel,
                                           @RequestParam(value = "measured_distance", required = false) Float measuredDistance) {
        try {
            dataService
                    .editData(dataId, currentLevel, observationDateTime, measuredDistance, referenceLevel);
            return ResponseEntity.ok("Data Edited Successfully");
        } catch (DataNotFoundException | InvalidInput e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity deleteData(@RequestParam("data_id") String dataId) {
        try {
            dataService.deleteData(dataId);
            return ResponseEntity.ok("Data Deleted Successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
