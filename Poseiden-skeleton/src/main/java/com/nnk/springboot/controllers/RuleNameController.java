package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.rulename.RuleNameCreateDTO;
import com.nnk.springboot.dto.rulename.RuleNameUpdateDTO;
import com.nnk.springboot.services.interfaces.RuleNameService;
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
 * Controller for RuleName CRUD operations.
 */
@Controller
public class RuleNameController {
    private final RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /** Displays the list of all rule names. */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.findAll(PageRequest.of(0, 1000), null).getContent());
        return "ruleName/list";
    }

    /** Displays the form to add a new rule name. */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model) {
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    /** Validates and saves a new rule name. */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            RuleNameCreateDTO dto = RuleNameCreateDTO.builder()
                    .name(ruleName.getName())
                    .description(ruleName.getDescription())
                    .json(ruleName.getJson())
                    .template(ruleName.getTemplate())
                    .sqlStr(ruleName.getSqlStr())
                    .sqlPart(ruleName.getSqlPart())
                    .enabled(ruleName.getEnabled())
                    .build();
            ruleNameService.create(dto);
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    /** Displays the form to update an existing rule name. */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") @NonNull Long id, Model model) {
        model.addAttribute("ruleName", ruleNameService.getForUpdateForm(id));
        return "ruleName/update";
    }

    /** Updates an existing rule name. */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") @NonNull Long id, @Valid RuleName ruleName,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        RuleNameUpdateDTO dto = RuleNameUpdateDTO.builder()
                .name(ruleName.getName())
                .description(ruleName.getDescription())
                .json(ruleName.getJson())
                .template(ruleName.getTemplate())
                .sqlStr(ruleName.getSqlStr())
                .sqlPart(ruleName.getSqlPart())
                .enabled(ruleName.getEnabled())
                .build();
        ruleNameService.update(id, dto);
        return "redirect:/ruleName/list";
    }

    /** Deletes a rule name by its ID. */
    @PostMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") @NonNull Long id) {
        ruleNameService.delete(id);
        return "redirect:/ruleName/list";
    }
}
