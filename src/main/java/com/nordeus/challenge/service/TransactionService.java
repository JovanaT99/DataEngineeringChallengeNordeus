package com.nordeus.challenge.service;

import com.nordeus.challenge.model.Event;
import com.nordeus.challenge.model.Rate;
import com.nordeus.challenge.model.Transaction;
import com.nordeus.challenge.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor
public class TransactionService {


    public final TransactionRepository transactionRepository;

    public Transaction createTransaction(Event event, List<Rate> rates) {
        String user_id = event.getEvent_data().getUser_id();
        String transaction_currency = event.getEvent_data().getTransaction_currency();
        float transaction_amount = event.getEvent_data().getTransaction_amount();

        long timestamp = event.getEvent_timestamp() * 1000L;
        LocalDateTime created_at =
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId()
                );

        Rate temp = null;

        for (Rate rate : rates) {
            if (Objects.equals(rate.getCurrency(), transaction_currency)) {
                temp = rate;
            }
        }

        if (transaction_amount != 0.99f
                && transaction_amount != 1.99f
                && transaction_amount != 2.99f
                && transaction_amount != 3.99f
                && transaction_amount != 4.99f
        ) {
            return null;
        }

        if (temp == null || user_id == null || transaction_currency == null || transaction_amount == 0) {
            return null;
        }

        float amount_usd = transaction_amount * temp.getRate_to_usd();
        return new Transaction(user_id, transaction_currency, transaction_amount, amount_usd, created_at);
    }


    public void saveList(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }

    public void deleteAll() {
        transactionRepository.truncate();
    }


    public Long countTransactionByUserId(String user_id) {
        return transactionRepository.countTransactionByUserId(user_id);
    }

    public Long countTransactionByUserIdAndDate(String user_id, Date date) {

        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));

        return transactionRepository.countTransactionByUserIdAndDate(user_id, start_date, end_date);

    }

    public Double sumTransactionByUserId(String user_id) {
        Optional<Double> amount = transactionRepository.sumTransactionByUserId(user_id);
        return amount.isPresent() ? amount.get() : 0;
    }

    public Double sumTransactionByUserIdAndDate(String user_id, Date date) {

        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));

        Optional<Double> amount = transactionRepository.sumTransactionByUserIdAndDate(user_id, start_date, end_date);
        return amount.isPresent() ? amount.get() : 0;
    }

    public Long countTransaction() {
        return transactionRepository.countTransaction();

    }

    public Long countTransactionByDate(Date date) {
        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));

        System.out.println(start_date);

        return transactionRepository.countTransactionByDate(start_date, end_date);
    }

    public Long countTransactionByCountry(String country) {
        return transactionRepository.countTransactionByCountry(country);
    }

    public Long countTransactionByCountryAndDate(String country, Date date) {

        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));
        return transactionRepository.countTransactionByCountryAndDate(country, start_date, end_date);
    }

    public Double sumTransaction() {
        Optional<Double> amount = transactionRepository.sumTransaction();
        return amount.isPresent() ? amount.get() : 0;

    }

    public Double sumTransactionByDate(Date date) {
        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));

        System.out.println(start_date);

        Optional<Double> amount = transactionRepository.sumTransactionByDate(start_date, end_date);
        return amount.isPresent() ? amount.get() : 0;
    }

    public Double sumTransactionByCountry(String country) {
        Optional<Double> amount = transactionRepository.sumTransactionByCountry(country);
        return amount.isPresent() ? amount.get() : 0;
    }

    public Double sumTransactionByCountryAndDate(String country, Date date) {

        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));
        Optional<Double> amount = transactionRepository.sumTransactionByCountryAndDate(country, start_date, end_date);
        return amount.isPresent() ? amount.get() : 0;
    }


}
