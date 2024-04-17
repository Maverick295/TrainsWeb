package com.train.train.controllers;

import com.train.train.forms.TrainCreateForm;
import com.train.train.services.train.TrainService;
import com.train.train.utils.RedirectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/simulation")
    public ModelAndView form() {
        return new ModelAndView("simulation-form")
                .addObject("trainCreateForm", new TrainCreateForm());
    }

    @PostMapping("/simulation")
    public ModelAndView createTrains(@ModelAttribute TrainCreateForm createForm) {
        trainService.createNewTrains(
                createForm.getMaxTrains(),
                createForm.getStartDate(),
                createForm.getFromMoscowToPiter(),
                createForm.getFromPiterToMoscow()
        );

        return new ModelAndView("simulation-form");
    }
}
