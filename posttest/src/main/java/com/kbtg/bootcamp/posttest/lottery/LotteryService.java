package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.TicketNotFoundException;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Lottery> getAllLotteryNumbers() {
        return lotteryRepository.findAll();
    }

    @Transactional
    public String purchaseLotteryTicket(String userId, String ticketNumber) {
        validateTicketNumberFormat(ticketNumber);

        Lottery ticket = fetchLotteryByTicketNumber(ticketNumber);

        UserTicket userTicket = createUserTicket(userId, ticket);
        userTicketRepository.save(userTicket);

        lotteryRepository.deleteById(Long.valueOf(ticket.getTicketId()));

        return userTicket.getTransactionId().toString();
    }

    private void validateTicketNumberFormat(String ticketNumber) {
        if (ticketNumber == null || !ticketNumber.matches("\\d{6}")) {
            throw new TicketNotFoundException("Invalid ticket number format. It must be a 6-digit number.");
        }
    }

    private Lottery fetchLotteryByTicketNumber(String ticketNumber) {
        return lotteryRepository.findByTicketNumber(ticketNumber)
                .stream()
                .findFirst()
                .orElseThrow(() -> new TicketNotFoundException("Ticket number: " + ticketNumber + " not found."));
    }

    private UserTicket createUserTicket(String userId, Lottery ticket) {
        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setTicketNumber(ticket.getTicketNumber());
        userTicket.setPrice(ticket.getPrice());
        userTicket.setTransactionType("BUY");
        return userTicket;
    }

    public Map<String, Object> getUserLotteriesInfo(String userId) {
        List<UserTicket> userTickets = userTicketRepository.findByUserId(userId);

        List<String> ticketNumbers = userTickets.stream()
                .map(UserTicket::getTicketNumber)
                .collect(Collectors.toList());

        int totalCost = userTickets.stream()
                .mapToInt(UserTicket::getPrice)
                .sum();

        Map<String, Object> response = new HashMap<>();
        response.put("tickets", ticketNumbers);
        response.put("count", userTickets.size());
        response.put("cost", totalCost);

        return response;
    }
}
