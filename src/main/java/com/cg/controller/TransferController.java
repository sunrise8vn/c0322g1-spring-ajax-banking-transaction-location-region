package com.cg.controller;


import com.cg.model.Transfer;
import com.cg.model.dto.TransferHistoryDTO;
import com.cg.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping
    public ModelAndView showListTransferPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("transfer/list");

//        List<Transfer> transfers = transferService.findAll();

        List<TransferHistoryDTO> transfers = transferService.findAllHistories();

        modelAndView.addObject("transfers", transfers);

        return modelAndView;
    }
}
