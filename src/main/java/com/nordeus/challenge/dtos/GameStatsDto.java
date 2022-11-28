package com.nordeus.challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameStatsDto {

    private Long dailyUsers;
    private Long logins;
    private Long transactions;
    private Double transactionsAmount;

}
