package com.kbtg.bootcamp.posttest.lottery;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record LotteryRequest(
        @NotBlank(message = "Ticket number cannot be blank")
        @Pattern(regexp = "\\d{6}", message = "Ticket number must be exactly 6 digits")
        String ticket
        ,
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be greater than 0")
        Integer price
        ,
        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be greater than 0")
        Integer amount){
}
