package com.kbtg.bootcamp.posttest.admin;
import com.kbtg.bootcamp.posttest.exception.ApiExceptionHandler;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;

    public AdminController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/lotteries")
    public ResponseEntity<?> createLottery(@Validated @RequestBody LotteryRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ApiExceptionHandler.buildValidationErrorResponse(errors);
        }
        Lottery lottery = lotteryService.createLottery(request);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("ticket", lottery.getTicketNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

}
