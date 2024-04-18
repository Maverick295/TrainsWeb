package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.entity.status.Status;
import com.train.train.models.TrainModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrainServiceImpl implements TrainService {
    private static final String SERIAL_PREFIX = "ВП";
    private static final Integer DEFAULT_DISTANCE = 0;
    private static final Integer DEFAULT_WORK_DAYS = 0;
    private static final String DEFAULT_CYCLE = "IS0";
    private static final Integer DEFAULT_SEAT = 450;
    private static final Integer DEFAULT_INTERVAL_MIN = 0;

    @Override
    public List<TrainModel> simulate(Integer maxTrains, LocalDateTime startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow) {
        List<Train> trainsList = createNewTrains(
                maxTrains,
                startDate,
                fromMoscowToPiter,
                fromPiterToMoscow
        );

        List<Integer> peopleFromMoscowToPiter = distributePeopleRandomly(fromMoscowToPiter, 4);
        List<Integer> peopleFromPiterToMoscow = distributePeopleRandomly(fromPiterToMoscow, 4);
        List<Integer> trainNums = findTrainNumbers(peopleFromMoscowToPiter, peopleFromPiterToMoscow);
        List<Double> intervalsCount = countIntervals(trainNums);

        List<String> statusList = setTrainStatus(trainNums, trainsList);
        for (int i = 0; i < trainsList.size(); i++) {
            trainsList.get(i).setStatus(statusList.get(i));
        }

        return convertToTrainModel(trainsList);
    }

    @Override
    public List<Train> createNewTrains(Integer maxTrains, LocalDateTime startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow) {
        List<Train> trainsList = new ArrayList<>();

        for (int i = 0; i < maxTrains - 1; i++) {
            Train train = new Train()
                    .setUuid(generateUuid())
                    .setSerialNumber(generateSerialNumber())
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
        return trainsList.stream()
                .map(train -> new TrainModel()
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
                        .setFromPiterToMoscow(train.getFromPiterToMoscow()))
                .collect(Collectors.toList());
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

    @Override
    public List<Integer> findTrainNumbers(List<Integer> fromMoscowToPiter, List<Integer> fromPiterToMoscow) {
        List<Integer> trainNums = new ArrayList<>();

        for (int i = 0; i < fromMoscowToPiter.size(); i++) {
            trainNums.add((int)  Math.ceil((double) Math.max(fromMoscowToPiter.get(i), fromPiterToMoscow.get(i)) / DEFAULT_SEAT));
        }

        return trainNums;
    }

    @Override
    public List<Double> countIntervals(List<Integer> trainNum) {
        List<Double> intervals = new ArrayList<>();

        for (int amount : trainNum) {
            double num = 360.0 / amount;
            double roundNum = Math.floor(num);
            if (num - roundNum < 0.5 && num - roundNum != 0) {
                roundNum += 0.5;
            } else if (num - roundNum == 0) {
                roundNum = num;
            } else {
                roundNum += 1;
            }

            intervals.add(roundNum);
        }

        return intervals;
    }

   @Override
   public List<String> setTrainStatus(List<Integer> trainNums, List<Train> trainsList) {
       List<String> statusList = new ArrayList<>();

       for (int i = 0; i < trainNums.get(0); i++) {
           statusList.add(String.valueOf(Status.IN_WORK));
       }

       for (int j = trainNums.get(0); j < trainsList.size(); j++) {
           statusList.add(String.valueOf(Status.IDLE));
       }

       return statusList;
   }

    public String generateUuid()     {
        return UUID.randomUUID().toString();
    }

    public String generateSerialNumber() {
        Random random = new Random();

        return String.join("-", SERIAL_PREFIX, String.valueOf(random.nextInt(90000) + 10000));
    }
}
