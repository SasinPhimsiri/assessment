package com.kbtg.bootcamp.posttest.user;


import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final LotteryService lotteryService;

    public UserController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> buyLottery(@PathVariable("userId") String userId, @PathVariable("ticketId") String ticketId) {
        String id = lotteryService.purchaseLotteryTicket(userId, ticketId);
        return new ResponseEntity<>(Map.of("id", id), HttpStatus.OK);
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<Map<String, Object>> getLotteries(@PathVariable("userId") String userId) {
        Map<String, Object> userLotteriesInfo = lotteryService.getUserLotteriesInfo(userId);
        return new ResponseEntity<>(userLotteriesInfo, HttpStatus.OK);
    }

}