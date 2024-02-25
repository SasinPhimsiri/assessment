package com.kbtg.bootcamp.posttest.lottery;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public Map<String, List<String>> getAllLotteryNumbers() {
        List<String> ticketNumbers = lotteryService.getAllLotteryNumbers()
                .stream()
                .map(Lottery::getTicketNumber)
                .collect(Collectors.toList());

        Map<String, List<String>> response = new HashMap<>();
        response.put("tickets", ticketNumbers);
        return response;
    }
}
