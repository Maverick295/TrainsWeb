package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.entity.status.Status;
import com.train.train.models.TrainModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class TrainServiceImpl implements TrainService {
    private static final String SERIAL_PREFIX = "ВП";
    private static final String DEFAULT_STATUS = String.valueOf(Status.DEFAULT);
    private static final Integer DEFAULT_DISTANCE = 0;
    private static final Integer DEFAULT_WORK_DAYS = 0;
    private static final String DEFAULT_CYCLE = "IS0";
    private static final Integer DEFAULT_SEAT = 450;
    private static final Integer DEFAULT_INTERVAL_MIN = 0;

    @Override
    public List<Train> createNewTrains(Integer maxTrains, LocalDate startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow) {
        List<Train> trainsList = new ArrayList<>();

        for (int i = 0; i < maxTrains; i++) {
            Train train = new Train()
                    .setUuid(generateUuid())
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

            trainsList.add(train);
        }

        return trainsList;
    }

    @Override
    public List<TrainModel> convertToTrainModel(List<Train> trainsList) {
        List<TrainModel> trainModelsList = new ArrayList<>();

        for (Train train : trainsList) {
            TrainModel trainModel = new TrainModel()
                    .setUuid(train.getUuid())
                    .setSerialNumber(train.getSerialNumber())
                    .setStatus(train.getStatus())
                    .setDistance(train.getDistance())
                    .setDaysInWork(train.getDaysInWork())
                    .setCycle(train.getCycle())
                    .setSeat(train.getSeat())
                    .setInterval(train.getInterval())
                    .setStartDate(train.getStartDate())
                    .setFromMoscowToPiter(train.getFromMoscowToPiter())
                    .setFromPiterToMoscow(train.getFromPiterToMoscow());

            trainModelsList.add(trainModel);
        }

        return trainModelsList;
    }

    @Override
    public List<Integer> distributePeopleRandomly(Integer peopleCount, int interval) {
        List<Integer> distributePeople = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < interval; i++) {
            int num = i == interval - 1 ? peopleCount : random.nextInt(peopleCount - interval + i + 1);
            distributePeople.add(num);
            peopleCount -= num;
        }
        return distributePeople;
    }

    public List<Integer> findTrainNumbers(List<Integer> fromMoscowToPiter, List<Integer> fromPiterToMoscow) {
        List<Integer> trainNums = new ArrayList<>();

        for (int i = 0; i < fromMoscowToPiter.size(); i++) {
            trainNums.add((int)  Math.ceil((double) Math.max(fromMoscowToPiter.get(i), fromPiterToMoscow.get(i)) / DEFAULT_SEAT));
        }
        return trainNums;
    }

    @Override
    public String generateUuid()     {
        return UUID.randomUUID().toString();
    }

    @Override
    public String generateSerialNumber() {
        Random random = new Random();

        return String.join("-", SERIAL_PREFIX, String.valueOf(random.nextInt(99999) + 10000));
    }
}
