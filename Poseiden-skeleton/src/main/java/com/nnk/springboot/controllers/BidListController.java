package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidCreateDTO;
import com.nnk.springboot.dto.BidUpdateDTO;
import com.nnk.springboot.services.interfaces.BidService;
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
 * Controller for handling BidList related web requests.
 * Manages CRUD operations for bid lists through web interface.
 */
@Controller
public class BidListController {
    private final BidService bidService;

    public BidListController(BidService bidService) {
        this.bidService = bidService;
    }

    /**
     * Displays the list of all bids.
     *
     * @param model the model to add attributes to
     * @return the view name for the bid list page
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bids", bidService.findAll(PageRequest.of(0, 1000), null).getContent());
        return "bidList/list";
    }

    /**
     * Displays the form to add a new bid.
     *
     * @param bid the bid object to bind to the form
     * @return the view name for the add bid form
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Validates and saves a new bid.
     *
     * @param bid the bid to validate and save
     * @param result the binding result for validation errors
     * @param model the model to add attributes to
     * @return redirect to list page if successful, otherwise the add form
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BidCreateDTO dto = BidCreateDTO.builder()
                    .account(bid.getAccount())
                    .type(bid.getType())
                    .bidQuantity(bid.getBidQuantity())
                    .build();
            bidService.create(dto);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    /**
     * Displays the form to update an existing bid.
     *
     * @param id the ID of the bid to update
     * @param model the model to add attributes to
     * @return the view name for the update bid form
     * @throws IllegalArgumentException if the bid ID is invalid
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") @NonNull Long id, Model model) {
        var bidResponse = bidService.findById(id);
        BidList bid = BidList.builder()
                .id(bidResponse.getId())
                .account(bidResponse.getAccount())
                .type(bidResponse.getType())
                .bidQuantity(bidResponse.getBidQuantity())
                .build();
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    /**
     * Updates an existing bid.
     *
     * @param id the ID of the bid to update
     * @param bidList the bid data to update
     * @param result the binding result for validation errors
     * @param model the model to add attributes to
     * @return redirect to list page if successful, otherwise the update form
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") @NonNull Long id, @Valid BidList bidList,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        BidUpdateDTO dto = BidUpdateDTO.builder()
                .account(bidList.getAccount())
                .type(bidList.getType())
                .bidQuantity(bidList.getBidQuantity())
                .build();
        bidService.update(id, dto);
        return "redirect:/bidList/list";
    }

    /**
     * Deletes a bid by its ID.
     *
     * @param id the ID of the bid to delete
     * @param model the model to add attributes to
     * @return redirect to list page
     * @throws IllegalArgumentException if the bid ID is invalid
     */
    @PostMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") @NonNull Long id, Model model) {
        bidService.delete(id);
        return "redirect:/bidList/list";
    }
}
