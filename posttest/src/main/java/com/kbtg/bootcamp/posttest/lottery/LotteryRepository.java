package com.kbtg.bootcamp.posttest.lottery;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Long> {
    @Query("SELECT l FROM Lottery l")
    List<Lottery> findAllAvailableLotteries();

    List<Lottery> findByTicketNumber(String ticketNumber);
}
