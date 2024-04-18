package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.models.TrainModel;
import com.train.train.models.status.Status;
import com.train.train.repositories.TrainRepository;
import com.train.train.services.model.ModelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class TrainServiceImpl implements TrainService {
    private final TrainRepository trainRepository;
    private final ModelService modelService;
    private static final String SERIAL_PREFIX = "ВП";
    private static final String DEFAULT_STATUS = String.valueOf(Status.DEFAULT);
    private static final Integer DEFAULT_DISTANCE = 0;
    private static final Integer DEFAULT_WORK_DAYS = 0;
    private static final String DEFAULT_CYCLE = "IS0";
    private static final Integer DEFAULT_SEAT = 0;
    private static final Integer DEFAULT_INTERVAL_MIN = 0;


    @Autowired
    public TrainServiceImpl(
            TrainRepository trainRepository,
            ModelService modelService
    ) {
        this.trainRepository = trainRepository;
        this.modelService = modelService;
    }

    @Override
    public Train getTrainById(Long id) {
        return trainRepository.findTrainById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Train getTrainBySerialNumber(String serialNumber) {
        return trainRepository.findTrainBySerialNumber(serialNumber)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void saveTrain(Train train) {
        trainRepository.save(train);
    }

    @Override
    public void createNewTrains(Integer maxTrains, LocalDate startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow) {
        for (int i = 0; i < maxTrains; i++) {
            saveTrain(new Train()
                    .setUuid(UUID.randomUUID().toString())
                    .setSerialNumber(generateSerialNumber())
                    .setStatus(DEFAULT_STATUS)
                    .setDistance(DEFAULT_DISTANCE)
                    .setDaysInWork(DEFAULT_WORK_DAYS)
                    .setCycle(DEFAULT_CYCLE)
                    .setSeat(DEFAULT_SEAT)
                    .setInterval(DEFAULT_INTERVAL_MIN)
                    .setStartDate(startDate)
                    .setFromMoscowToPiter(fromMoscowToPiter)
                    .setFromPiterToMoscow(fromPiterToMoscow)
            );

            log.info("create train " + i);
        }
    }

    @Override
    public Page<TrainModel> findAllTrains(Pageable pageable) {
        return trainRepository.findAll(pageable)
                .map(modelService::getTrainModel);
    }

    public String generateSerialNumber() {
        Random random = new Random();

        return String.join("-", SERIAL_PREFIX, String.valueOf(random.nextInt(99999) + 10000));
    }
}
