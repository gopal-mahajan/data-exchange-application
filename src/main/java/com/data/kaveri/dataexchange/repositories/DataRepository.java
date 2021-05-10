package com.data.kaveri.dataexchange.repositories;


import com.data.kaveri.dataexchange.entities.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, String> {

    DataEntity findByIdAndDeleted(String id, boolean deleted);
}
