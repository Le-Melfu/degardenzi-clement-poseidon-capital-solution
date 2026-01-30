package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvepoint.CurvePointCreateDTO;
import com.nnk.springboot.dto.curvepoint.CurvePointUpdateDTO;
import com.nnk.springboot.services.interfaces.CurvePointService;
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
 * Controller for CurvePoint CRUD operations.
 */
@Controller
public class CurveController {
    private final CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /** Displays the list of all curve points. */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAll(PageRequest.of(0, 1000), null, null).getContent());
        return "curvePoint/list";
    }

    /** Displays the form to add a new curve point. */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /** Validates and saves a new curve point. */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            CurvePointCreateDTO dto = CurvePointCreateDTO.builder()
                    .curveId(curvePoint.getCurveId())
                    .asOfDate(curvePoint.getAsOfDate())
                    .term(curvePoint.getTerm())
                    .value(curvePoint.getValue())
                    .build();
            curvePointService.create(dto);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    /** Displays the form to update an existing curve point. */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") @NonNull Long id, Model model) {
        model.addAttribute("curvePoint", curvePointService.getForUpdateForm(id));
        return "curvePoint/update";
    }

    /** Updates an existing curve point. */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") @NonNull Long id, @Valid CurvePoint curvePoint,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        CurvePointUpdateDTO dto = CurvePointUpdateDTO.builder()
                .curveId(curvePoint.getCurveId())
                .asOfDate(curvePoint.getAsOfDate())
                .term(curvePoint.getTerm())
                .value(curvePoint.getValue())
                .build();
        curvePointService.update(id, dto);
        return "redirect:/curvePoint/list";
    }

    /** Deletes a curve point by its ID. */
    @PostMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") @NonNull Long id) {
        curvePointService.delete(id);
        return "redirect:/curvePoint/list";
    }
}
