package com.nordeus.challenge.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String user_id;
    private String transaction_currency;
    private float transaction_amount;
    private float transaction_amount_usd;
    private LocalDateTime created_at;

    public Transaction(String user_id, String transaction_currency, float transaction_amount, float transaction_amount_usd, LocalDateTime created_at) {
        this.user_id = user_id;
        this.transaction_currency = transaction_currency;
        this.transaction_amount = transaction_amount;
        this.transaction_amount_usd = transaction_amount_usd;
        this.created_at = created_at;
    }
}
