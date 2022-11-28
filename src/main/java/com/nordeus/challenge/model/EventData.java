package com.nordeus.challenge.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EventData {

    private String user_id;
    private String name;
    private String country;
    private String device_os;
    private float transaction_amount;
    private String transaction_currency;

}
