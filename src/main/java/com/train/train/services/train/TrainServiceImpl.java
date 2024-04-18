package com.train.train.services.train;

import com.train.train.entity.status.Status;
import com.train.train.models.TrainModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class TrainServiceImpl implements TrainService {
    private final List<TrainModel> trainModelList = new ArrayList<>();
    private static final String SERIAL_PREFIX = "ВП";
    private static final String DEFAULT_STATUS = String.valueOf(Status.DEFAULT);
    private static final Integer DEFAULT_DISTANCE = 0;
    private static final Integer DEFAULT_WORK_DAYS = 0;
    private static final String DEFAULT_CYCLE = "IS0";
    private static final Integer DEFAULT_SEAT = 0;
    private static final Integer DEFAULT_INTERVAL_MIN = 0;

    @Override
    public List<TrainModel> createNewTrains(Integer maxTrains, LocalDate startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow) {
        if (!trainModelList.isEmpty()) {
            trainModelList.clear();
        }

        for (int i = 0; i < maxTrains; i++) {
            TrainModel trainModel = new TrainModel()
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
                    .setFromPiterToMoscow(fromPiterToMoscow);

            trainModelList.add(trainModel);
        }

        return trainModelList;
    }

    public String generateSerialNumber() {
        Random random = new Random();

        return String.join("-", SERIAL_PREFIX, String.valueOf(random.nextInt(99999) + 10000));
    }
}
