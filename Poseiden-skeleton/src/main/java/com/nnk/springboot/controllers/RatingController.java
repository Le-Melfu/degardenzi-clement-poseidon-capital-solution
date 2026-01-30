package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingCreateDTO;
import com.nnk.springboot.dto.rating.RatingUpdateDTO;
import com.nnk.springboot.services.interfaces.RatingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * Controller for Rating CRUD operations.
 */
@Controller
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /** Displays the list of all ratings. */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAll(PageRequest.of(0, 1000), null).getContent());
        return "rating/list";
    }

    /** Displays the form to add a new rating. */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /** Validates and saves a new rating. */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            RatingCreateDTO dto = RatingCreateDTO.builder()
                    .moodysRating(rating.getMoodysRating())
                    .sandPRating(rating.getSandPRating())
                    .fitchRating(rating.getFitchRating())
                    .orderNumber(rating.getOrderNumber())
                    .build();
            ratingService.create(dto);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    /** Displays the form to update an existing rating. */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") @NonNull Long id, Model model) {
        model.addAttribute("rating", ratingService.getForUpdateForm(id));
        return "rating/update";
    }

    /** Updates an existing rating. */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") @NonNull Long id, @Valid Rating rating,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        RatingUpdateDTO dto = RatingUpdateDTO.builder()
                .moodysRating(rating.getMoodysRating())
                .sandPRating(rating.getSandPRating())
                .fitchRating(rating.getFitchRating())
                .orderNumber(rating.getOrderNumber())
                .build();
        ratingService.update(id, dto);
        return "redirect:/rating/list";
    }

    /** Deletes a rating by its ID. */
    @PostMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") @NonNull Long id) {
        ratingService.delete(id);
        return "redirect:/rating/list";
    }
}
