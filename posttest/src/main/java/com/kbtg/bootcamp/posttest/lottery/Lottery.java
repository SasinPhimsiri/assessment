package com.kbtg.bootcamp.posttest.lottery;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lottery")
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    @Column(name = "ticket_number", nullable = false, length = 6)
    private String ticket;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public Lottery() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Lottery(String ticket, Integer price, Integer amount) {
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public String getTicket() {
        return ticket;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
