package com.train.train.services.train;

import com.train.train.entity.Train;
import com.train.train.entity.status.Status;
import com.train.train.models.TrainModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrainServiceImpl implements TrainService {
    private static final String SERIAL_PREFIX = "ВП";
    private static final Integer DEFAULT_DISTANCE = 0;
    private static final Integer DEFAULT_WORK_DAYS = 0;
    private static final String DEFAULT_CYCLE = "IS0";
    private static final Integer DEFAULT_SEAT = 450;
    private static final Integer INTERVAL_SPLIT = 4;
    private static final Integer DEFAULT_INTERVAL_MIN = 0;
    private Map<Integer, List<LocalDateTime>> timeMapStart;
    private Map<Integer, List<LocalDateTime>> timeMapEnd;
    private List<Train> trainsList;

    @Override
    public List<Train> exec(Integer maxTrains, LocalDateTime startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow) {
        timeMapStart = new HashMap<>();
        timeMapEnd = new HashMap<>();

        createNewTrains(maxTrains, startDate, fromMoscowToPiter, fromPiterToMoscow);

        List<Integer> peopleFromMoscowToPiter = distributePeopleRandomly(fromMoscowToPiter, 4);
        List<Integer> peopleFromPiterToMoscow = distributePeopleRandomly(fromPiterToMoscow, 4);
        List<Integer> trainNums = findTrainNumbers(peopleFromMoscowToPiter, peopleFromPiterToMoscow);
        List<Double> intervals = countIntervals(trainNums);

        for (int i = 0; i < INTERVAL_SPLIT; ++i) {
            timeMapStart.put(i, new ArrayList<>());
            timeMapEnd.put(i, new ArrayList<>());
        }

        log.info(startDate.toString());

        LocalDateTime time1111 = LocalDateTime.parse(startDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        updateTime(time1111, time1111.plusHours(6), intervals.get(0).intValue(), 0);
        updateTime(time1111.plusHours(6), time1111.plusHours(12), intervals.get(1).intValue(), 1);
        updateTime(time1111.plusHours(12), time1111.plusHours(18), intervals.get(2).intValue(), 2);
        updateTime(time1111.plusHours(18), time1111.plusHours(24), intervals.get(3).intValue(), 3);


        Integer trainsInWorkPerDays = trainNums.get(0);

        // интервалы меняются 0-6 | 6-12 (ситуация 1)

        log.info("situation 1");

        for (LocalDateTime timeStart : timeMapStart.get(1)) {
            List<LocalDateTime> timeEndList = timeMapEnd.get(0);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(1).intValue() + 2)) <= 0;
                if (checked) {
                    timeMapStart.get(0).get(i).plusHours(7);
                    timeMapEnd.get(0).get(i).plusHours(7);
                    break;
                    // timeEndList.get(i).plusHours(7);
                } else {
                    log.info(timeStart + " " + timeEndList.get(i));
                    // log.info();
                    ++trainsInWorkPerDays;
                    break;
                    // тут нужно добавить логику нового поезда в работу
                }
            }
        }

        // интервалы меняются 12-18 | 0-6 6-12 (ситуация 2)

        for (LocalDateTime timeStart : timeMapStart.get(2)) {
            List<LocalDateTime> timeEndList = timeMapEnd.get(0);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(2).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(0).get(i).plusHours(7);
                    timeMapEnd.get(0).get(i).plusHours(7);

                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
            }
            timeEndList = timeMapEnd.get(1);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(2).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(1).get(i).plusHours(7);
                    timeMapEnd.get(1).get(i).plusHours(7);

                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
            }
        }

        // интервалы меняются 18-00 | 0-6 6-12 12-18 (ситуация 3)

        for (LocalDateTime timeStart : timeMapStart.get(3)) {
            List<LocalDateTime> timeEndList = timeMapEnd.get(0);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(3).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(0).get(i).plusHours(7);
                    timeMapEnd.get(0).get(i).plusHours(7);

                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
            }
            timeEndList = timeMapEnd.get(1);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(3).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(1).get(i).plusHours(7);
                    timeMapEnd.get(1).get(i).plusHours(7);

                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
            }
            timeEndList = timeMapEnd.get(2);
            for (int i = 0; i < timeEndList.size(); ++i) {
                Duration duration = Duration.between(timeStart, timeEndList.get(i));
                boolean checked = duration.compareTo(Duration.ofMinutes(intervals.get(3).intValue())) <= 0;
                if (checked) {
                    timeMapStart.get(2).get(i).plusHours(7);
                    timeMapEnd.get(2).get(i).plusHours(7);

                } else {
                    // тут нужно добавить логику нового поезда в работу
                    ++trainsInWorkPerDays;
                    break;
                }
                break;
            }
        }

        log.info("reset status for trains");

        List<String> statusList = setTrainStatus(trainNums, trainsInWorkPerDays);

        for (int i = 0; i < trainsList.size(); i++) {
            trainsList.get(i).setStatus(statusList.get(i));
        }

        // LOGS
        // ----------------------------


        log.info("TRAINS IN WORK PER DAYS: ");
        log.info(trainsInWorkPerDays.toString());

        log.info("START: ");

        for (Integer key : timeMapStart.keySet()) {
            List<LocalDateTime> value = timeMapStart.get(key);
            log.info("Key: " + key + ", Value: " + value);
        }

        log.info("END: ");

        for (Integer key : timeMapEnd.keySet()) {
            List<LocalDateTime> value = timeMapEnd.get(key);
            log.info("Key: " + key + ", Value: " + value);
        }
        
        log.info(peopleFromMoscowToPiter.toString());
        log.info(peopleFromPiterToMoscow.toString());
        log.info(trainNums.toString());
        log.info(intervals.toString());
        log.info(statusList.toString());

        log.info(trainsList.toString());

        // ----------------------------

        return trainsList;
    }

    @Override
    public void createNewTrains(Integer maxTrains, LocalDateTime startDate, Integer fromMoscowToPiter, Integer fromPiterToMoscow) {
        trainsList = new ArrayList<>();

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
            trainNums.add((int) Math.ceil((double) Math.max(fromMoscowToPiter.get(i), fromPiterToMoscow.get(i)) / DEFAULT_SEAT));
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
   public List<String> setTrainStatus(List<Integer> trainNums, Integer trainsInWorkPerDays) {
       List<String> statusList = new ArrayList<>();

       for (int i = 0; i < trainsInWorkPerDays; i++) {
           statusList.add(String.valueOf(Status.IN_WORK));
       }

       for (int j = trainsInWorkPerDays; j < trainsList.size(); j++) {
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

    public void updateTime(LocalDateTime startTime, LocalDateTime endTime, int intervalMin, int key) {
        LocalDateTime current = startTime;

        int x = 360;
        int splitTime = x / intervalMin;

        for (int i = 0; i < splitTime; ++i) {
            timeMapStart.get(key).add(current);
            timeMapEnd.get(key).add(current.plusHours(7));
            current = current.plusMinutes(intervalMin);
        }
    }
}
