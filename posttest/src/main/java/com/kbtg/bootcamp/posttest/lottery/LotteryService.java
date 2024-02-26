package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.TicketNotFoundException;
import com.kbtg.bootcamp.posttest.user.User;
import com.kbtg.bootcamp.posttest.user.UserRepository;
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
    private final UserRepository userRepository;

    public LotteryService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository, UserRepository userRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
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

        // Add transaction data
        UserTicket userTicket = createUserTicket(userId, ticket);
        userTicketRepository.save(userTicket);

        // Add user data
        User user = new User();
        user.setUserId(userTicket.getUserId());
        user.setTicketNumber(userTicket.getTicketNumber());
        user.setPrice(userTicket.getPrice());
        userRepository.save(user);

        // Delete lottery when user buy
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

    public Map<String, Object> getUserLotteriesInfo(String userId, String transactionType) {
        List<UserTicket> userTickets = userTicketRepository.findByUserId(userId).stream()
                .filter(userTicket -> transactionType.equals(userTicket.getTransactionType())) // Filter by transactionType
                .toList();

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

    @Transactional
    public String sellLotteryTicket(String userId, String ticketNumber) {
        validateTicketNumberFormat(ticketNumber);

        // Find the first ticket bought by the user with the specified ticket number
        UserTicket boughtTicket = userTicketRepository.findByUserId(userId).stream()
                .filter(userTicket -> "BUY".equals(userTicket.getTransactionType()) && ticketNumber.equals(userTicket.getTicketNumber()))
                .findFirst()
                .orElseThrow(() -> new TicketNotFoundException("No bought ticket found for ticket number: " + ticketNumber + " of user: " + userId));

        // Create a UserTicket with a "SELL" transaction type
        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setTicketNumber(ticketNumber);
        userTicket.setPrice(boughtTicket.getPrice()); // Use the price from the bought ticket
        userTicket.setTransactionType("SELL");
        userTicketRepository.save(userTicket);

        // Add the sold ticket back to the lottery pool
        LotteryRequest request = new LotteryRequest(ticketNumber, userTicket.getPrice(), 1);
        createLottery(request);

        // Find the first User entity associated with the bought ticket
        User user = (User) userRepository.findByUserIdAndTicketNumber(userId, ticketNumber)
                .stream()
                .findFirst()
                .orElseThrow(() -> new TicketNotFoundException("No User record found for ticket number: " + ticketNumber + " of user: " + userId));

        // Delete the found User entity
        userRepository.delete(user);


        return ticketNumber;
    }


}
