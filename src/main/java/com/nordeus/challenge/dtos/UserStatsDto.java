package com.nordeus.challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsDto {
    private String country;
    private String name;
    private Long logins;
    private Long transactions;
    private double transactionsAmount;
    private Long lastLoginDays;
}
