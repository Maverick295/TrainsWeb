package com.train.train.repositories;

import com.train.train.entity.Train;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends CrudRepository<Train, Long> {
    Optional<Train> findTrainById(Long id);
    Optional<Train> findTrainBySerialNumber(String serialNumber);
    Page<Train> findAll(Pageable pageable);
}
