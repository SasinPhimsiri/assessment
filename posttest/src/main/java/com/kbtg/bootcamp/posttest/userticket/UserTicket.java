package com.kbtg.bootcamp.posttest.userticket;
import jakarta.persistence.*;

@Entity
@Table(name = "user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "ticket_number", nullable = false)
    private String ticketNumber;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "transaction_type", nullable = false, length = 4)
    private String transactionType;

    public UserTicket(Integer transactionId, String ticketNumber, String userId, Integer price, String transactionType) {
        this.transactionId = transactionId;
        this.ticketNumber = ticketNumber;
        this.userId = userId;
        this.price = price;
        this.transactionType = transactionType;
    }

    public UserTicket() {

    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}