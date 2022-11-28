package com.nordeus.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nordeus.challenge.dtos.GameStatsDto;
import com.nordeus.challenge.dtos.UserStatsDto;
import com.nordeus.challenge.model.Client;
import com.nordeus.challenge.model.Event;
import com.nordeus.challenge.model.Rate;
import com.nordeus.challenge.service.ClientService;
import com.nordeus.challenge.service.EventService;
import com.nordeus.challenge.service.LoginService;
import com.nordeus.challenge.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;


@RestController
@AllArgsConstructor
//@RequestMapping(path = "api/data/challenge")
public class Endpoint {

    // @Autowired
    private ResourceLoader resourceLoader;

    private final EventService eventService;
    private final ClientService clientService;
    private final LoginService loginService;

    private final TransactionService transactionService;

//    public Endpoint(EventService eventService, ClientService clientService, LoginService loginService) {
//        this.eventService = eventService;
//        this.clientService = clientService;
//        this.loginService = loginService;
//
//    }

    @GetMapping("/load")
    public void load() {
        Resource resourceEvent = this.resourceLoader.getResource("classpath:events.jsonl");
        Resource resourceRates = this.resourceLoader.getResource("classpath:rates.jsonl");

        LinkedList<Event> events = new LinkedList<>();
        LinkedList<Rate> rates = new LinkedList<>();

        // Load events.json into events linkedlist
        try {
            InputStream inputStream = resourceEvent.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            ObjectMapper mapper = new ObjectMapper();

            String line = reader.readLine();

            while (line != null) {
                Event event = mapper.readValue(line, Event.class);

                // Add event only if event_data and event_type are not null
                if (event.getEvent_data() != null && event.getEvent_type() != null) {
                    events.add(event);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Load rates.json into rates linkedlist
        try {
            InputStream inputStream = resourceRates.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            ObjectMapper mapper = new ObjectMapper();

            String line = reader.readLine();

            while (line != null) {
                Rate rate = mapper.readValue(line, Rate.class);
                rates.add(rate);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Sort events by event id
        Collections.sort(events, (arg0, arg1) -> {
            if (arg0.getEvent_id() == arg1.getEvent_id()) return 0;
            return arg0.getEvent_id() < arg1.getEvent_id() ? -1 : 1;
        });

//        for (Event event : events) {
//            if (event.getEvent_data() == null || event.getEvent_type() == null) {
//                System.out.println(event.getEvent_id() + " null");
//            } else {
//                eventService.insertEvent(event,rates);
//            }
//        }


        //truncate all tables
        eventService.deleteAll();

        //insert all events at once
        eventService.insertEvents(events, rates);

    }

    @GetMapping("/user-stats")
    public ResponseEntity<UserStatsDto> userStats(@RequestParam String user_id, @RequestParam(required = false) Date date) {

        // System.out.println(date);
        UserStatsDto dto = new UserStatsDto();
        Client client = clientService.findOneByUserId(user_id);

        if (client == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        dto.setCountry(client.getCountry());
        dto.setName(client.getName());
        if (date != null) {
            dto.setLogins(loginService.countLoginsByUserIdAndDate(user_id, date));
            dto.setTransactions(transactionService.countTransactionByUserIdAndDate(user_id, date));
            dto.setTransactionsAmount(transactionService.sumTransactionByUserIdAndDate(user_id, date));
            dto.setLastLoginDays(loginService.lastLoginDaysByDate(user_id,date));
        } else {
            dto.setLogins(loginService.countLoginsByUserId(user_id));
            dto.setTransactions(transactionService.countTransactionByUserId(user_id));
            dto.setTransactionsAmount(transactionService.sumTransactionByUserId(user_id));
            dto.setLastLoginDays(loginService.lastLoginDays(user_id));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/game-stats")
    public ResponseEntity<GameStatsDto> gameStats(@RequestParam(required = false) Date date, @RequestParam(required = false) String country) {

        GameStatsDto gameStatsDto = new GameStatsDto();
        if (date != null && country == null) {

            gameStatsDto.setLogins(loginService.countLoginsByDate(date));
            gameStatsDto.setTransactions(transactionService.countTransactionByDate(date));
            gameStatsDto.setTransactionsAmount(transactionService.sumTransactionByDate(date));
            gameStatsDto.setDailyUsers(loginService.countDailyActiveByDate(date));

        } else if (country != null && date == null) {

            gameStatsDto.setLogins(loginService.countLoginByCountry(country));
            gameStatsDto.setTransactions(transactionService.countTransactionByCountry(country));
            gameStatsDto.setTransactionsAmount(transactionService.sumTransactionByCountry(country));
            gameStatsDto.setDailyUsers(loginService.countDailyActiveByCountry(country));

        } else if (country == null) {

            gameStatsDto.setLogins(loginService.countLogin());
            gameStatsDto.setTransactions(transactionService.countTransaction());
            gameStatsDto.setTransactionsAmount(transactionService.sumTransaction());
            gameStatsDto.setDailyUsers(loginService.countDailyActiveUsers());
        } else {
            gameStatsDto.setLogins(loginService.countLoginByCountryAndDate(country, date));
            gameStatsDto.setTransactions(transactionService.countTransactionByCountryAndDate(country, date));
            gameStatsDto.setTransactionsAmount(transactionService.sumTransactionByCountryAndDate(country, date));
            gameStatsDto.setDailyUsers(loginService.countDailyActiveUsersByCountryAndDate(country, date));
        }

        return new ResponseEntity<>(gameStatsDto, HttpStatus.OK);

    }
}

