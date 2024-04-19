package com.train.train.controllers;

import com.train.train.forms.TrainCreateForm;
import com.train.train.services.train.TrainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class TrainController {
    private final TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/simulation")
    public ModelAndView form() {
        return new ModelAndView("simulation-form")
                .addObject("trainCreateForm", new TrainCreateForm());
    }

    @PostMapping("/simulation")
    public ModelAndView createTrains(@ModelAttribute TrainCreateForm createForm) {
        int passengersMoscowToPiter = createForm.getFromMoscowToPiter();
        int passengersPiterToMoscow = createForm.getFromPiterToMoscow();

        int difference = Math.abs(passengersMoscowToPiter - passengersPiterToMoscow);
        String message = "";

        if (difference > 100) {
            message = "Необходимо поднять стоимость билетов из-за большой разницы в количестве пассажиров";
        }

        ModelAndView modelAndView = new ModelAndView("simulation-form");
        modelAndView.addObject("allTrains", trainService.exec(createForm.getMaxTrains(), createForm.getStartDate(), passengersMoscowToPiter, passengersPiterToMoscow));
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
