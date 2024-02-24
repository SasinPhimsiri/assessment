package com.kbtg.bootcamp.posttest.userticket;
import com.kbtg.bootcamp.posttest.user.User;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id", nullable = false)
    private Lottery lottery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "transaction_type", nullable = false, length = 4)
    private String transactionType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public UserTicket() {
        this.datetime = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UserTicket(Lottery lottery, User user, BigDecimal price, String transactionType) {
        this.lottery = lottery;
        this.user = user;
        this.price = price;
        this.transactionType = transactionType;
        this.datetime = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public Long getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    // Setters for createdAt and updatedAt, if needed
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}