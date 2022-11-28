package com.nordeus.challenge.service;

import com.nordeus.challenge.model.Event;
import com.nordeus.challenge.model.Login;
import com.nordeus.challenge.repository.LoginRepository;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class LoginService {

    public final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Login createLogin(Event event) {
        String user_id = event.getEvent_data().getUser_id();
        long timestamp = event.getEvent_timestamp() * 1000L;
        LocalDateTime created_at =
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId()
                );

//        boolean idExist = loginRepository.findbyId(event.getEvent_data().getUser_id())
//                .isPresent();
//        if (idExist) {
//            System.out.println("Already exist");
//        } else {
        return new Login(user_id, created_at);
    }

    public void saveList(List<Login> logins) {
        loginRepository.saveAll(logins);
    }

    public void deleteAll() {
        loginRepository.truncate();
    }

    public Long countLoginsByUserId(String user_id) {
        return loginRepository.countLoginsByUserId(user_id);
    }

    public Long countLoginsByUserIdAndDate(String user_id, Date date) {


        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));

        System.out.println(start_date);

        return loginRepository.countLoginsByUserIdAndDate(user_id, start_date, end_date);
    }

    public Long countLogin() {
        return loginRepository.countLogin();

    }

    public Long countLoginsByDate(Date date) {
        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));

        System.out.println(start_date);

        return loginRepository.countLoginByDate(start_date, end_date);
    }

    public Long countLoginByCountry(String country) {
        return loginRepository.countLoginByCountry(country);
    }

    public Long countLoginByCountryAndDate(String country, Date date) {

        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));
        return loginRepository.countLoginByCountryAndDate(country, start_date, end_date);
    }


    public Long countDailyActiveUsers() {
        return loginRepository.countDailyActiveUsers();

    }

    public Long countDailyActiveByDate(Date date) {
        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));

        System.out.println(start_date);

        return loginRepository.countDailyActiveByDate(start_date, end_date);
    }

    public Long countDailyActiveByCountry(String country) {
        return loginRepository.countDailyActiveByCountry(country);
    }

    public Long countDailyActiveUsersByCountryAndDate(String country, Date date) {

        LocalDateTime start_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(0, 0, 0, 0));

        LocalDateTime end_date = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.of(23, 59, 59, 999999999));
        return loginRepository.countDailyActiveUsersByCountryAndDate(country, start_date, end_date);
    }

    public Long lastLoginDays(String user_id) {
        Optional<Login> login = loginRepository.findFirstByUserIdOrderByCreatedAtDesc(user_id);
        long days = -1L;
        if (login.isPresent()) {

            LocalDateTime createdAt = login.get().getCreatedAt();
            LocalDateTime localDateTime = LocalDateTime.now();
            days = Duration.between(createdAt, localDateTime).toDays();
        }
        return days;
    }

    public Long lastLoginDaysByDate(String user_id,Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
        Optional<Login> login = loginRepository.findFirstByUserIdAndCreatedAtLessThanOrderByCreatedAtDesc(user_id,localDateTime);
        long days = -1L;
        if (login.isPresent()) {

            LocalDateTime createdAt = login.get().getCreatedAt();
           // LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
            days = Duration.between(createdAt, localDateTime).toDays();
        }
        return days;
    }




}


