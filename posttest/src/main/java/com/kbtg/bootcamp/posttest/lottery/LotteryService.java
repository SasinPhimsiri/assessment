package com.kbtg.bootcamp.posttest.lottery;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public Lottery createLottery(LotteryRequest request) {
        Lottery lottery = new Lottery();
        lottery.setTicket(request.ticket());
        lottery.setPrice(request.price());
        lottery.setAmount(request.amount());
        return lotteryRepository.save(lottery);
    }

    public List<Lottery> getAllLotteryNumbers(){
        return lotteryRepository.findAll();
    }

}
