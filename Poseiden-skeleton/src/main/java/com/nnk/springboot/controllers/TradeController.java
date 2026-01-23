package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeCreateDTO;
import com.nnk.springboot.dto.TradeUpdateDTO;
import com.nnk.springboot.services.interfaces.TradeService;
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

@Controller
public class TradeController {
    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.findAll(PageRequest.of(0, 1000), null, null, null).getContent());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            TradeCreateDTO dto = TradeCreateDTO.builder()
                    .account(trade.getAccount())
                    .type(trade.getType())
                    .buyQuantity(trade.getBuyQuantity())
                    .sellQuantity(trade.getSellQuantity())
                    .tradeDate(trade.getTradeDate())
                    .build();
            tradeService.create(dto);
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") @NonNull Long id, Model model) {
        var tradeResponse = tradeService.findById(id);
        Trade trade = Trade.builder()
                .id(tradeResponse.getId())
                .account(tradeResponse.getAccount())
                .type(tradeResponse.getType())
                .buyQuantity(tradeResponse.getBuyQuantity())
                .sellQuantity(tradeResponse.getSellQuantity())
                .tradeDate(tradeResponse.getTradeDate())
                .build();
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") @NonNull Long id, @Valid Trade trade,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        TradeUpdateDTO dto = TradeUpdateDTO.builder()
                .account(trade.getAccount())
                .type(trade.getType())
                .buyQuantity(trade.getBuyQuantity())
                .sellQuantity(trade.getSellQuantity())
                .tradeDate(trade.getTradeDate())
                .build();
        tradeService.update(id, dto);
        return "redirect:/trade/list";
    }

    @PostMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") @NonNull Long id, Model model) {
        tradeService.delete(id);
        return "redirect:/trade/list";
    }
}
