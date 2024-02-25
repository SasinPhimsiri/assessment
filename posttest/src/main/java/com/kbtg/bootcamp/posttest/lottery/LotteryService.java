package com.kbtg.bootcamp.posttest.lottery;
import com.kbtg.bootcamp.posttest.exception.TicketNotFoundException;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;


@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;

    private final UserTicketRepository userTicketRepository;

    public LotteryService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
    }

    public Lottery createLottery(LotteryRequest request) {
        Lottery lottery = new Lottery();
        lottery.setTicketNumber(request.ticket());
        lottery.setPrice(request.price());
        lottery.setAmount(request.amount());
        return lotteryRepository.save(lottery);
    }

    public List<Lottery> getAllLotteryNumbers(){
        return lotteryRepository.findAll();
    }

    @Transactional
    public Serializable buyLottery(String userId, String ticketNumber) {

        // Validated ticketNumber
        validateTicketNumber(ticketNumber);

        Lottery ticket = lotteryRepository.findByTicketNumber(ticketNumber)
                .stream()
                .findFirst()
                .orElseThrow(() -> new TicketNotFoundException("Ticket number: " + ticketNumber + " not found. Please try a different ticket number."));

        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setTicketNumber(ticketNumber);
        userTicket.setTransactionType("BUY");
        userTicketRepository.save(userTicket);

        // Delete the selected ticket
        lotteryRepository.deleteById(ticket.getTicketId().longValue());

        return String.valueOf(userTicket.getTransactionId());
    }

    private UserTicket createUserTicket(String userId, String ticketNumber) {
        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setTicketNumber(ticketNumber);
        userTicket.setTransactionType("BUY");
        userTicketRepository.save(userTicket);
        return userTicket;
    }

    private void validateTicketNumber(String ticketNumber) {
        if (ticketNumber == null || !ticketNumber.matches("\\d{6}")) {
            throw new TicketNotFoundException("Invalid ticket number. It must be a 6-digit number.");
        }
    }
}
