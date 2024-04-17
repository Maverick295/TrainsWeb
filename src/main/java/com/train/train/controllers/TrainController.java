package com.train.train.controllers;

import com.train.train.services.train.TrainService;
import com.train.train.utils.RedirectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TrainController {
    private final TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/view/trains")
    public ModelAndView allTrains(@PageableDefault Pageable pageable) {
        return new ModelAndView("trains")
                .addObject("trains", trainService.findAllTrains(pageable));
    }

    @PostMapping("/view/add")
    public ModelAndView addTrain() {
        trainService.saveTrain(trainService.createNewTrain());

        return RedirectUtil.redirect("/view/trains");
    }

    @GetMapping("/view/{uuid}")
    public ModelAndView train(@PathVariable String uuid) {
        return new ModelAndView("train-info")
                .addObject("train", trainService.findTrainByUuid(uuid));
    }
}
