package com.nordeus.challenge.service;

import com.nordeus.challenge.model.*;

import com.nordeus.challenge.repository.LoginRepository;
import com.nordeus.challenge.repository.TransactionRepository;
import com.nordeus.challenge.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor

public class EventService {
    @Autowired
    private final ClientRepository userRepository;

    public final LoginRepository loginRepository;

    public final LoginService loginService;
    public final ClientService clientService;

    public final TransactionService transactionService;
    public final TransactionRepository transactionRepo;


//    public EventService(UserRepository userRepository, LoginRepository loginRepository, LoginService loginService) {
//        this.userRepository = userRepository;
//        this.loginRepository = loginRepository;
//        this.loginService = loginService;
//    }

//    public void insertEvent(Event event, LinkedList<Rate> rates) {
//        if (event.getEvent_type().equals("registration")) {
//
//            String user_id = event.getEvent_data().getUser_id();
//            String name = event.getEvent_data().getName();
//            String country = event.getEvent_data().getCountry();
//            String device_os = event.getEvent_data().getDevice_os();
//            long test_timestamp = event.getEvent_timestamp() * 1000L;
//            LocalDateTime created_at =
//                    LocalDateTime.ofInstant(
//                            Instant.ofEpochMilli(test_timestamp),
//                            TimeZone.getDefault().toZoneId()
//                    );
//
//            if (user_id != null || name != null || country != null || device_os != null)
//
//                try {
//                    userRepository.save(new Client(user_id, name, country, device_os, created_at));
//                } catch (Exception e) {
//                    System.out.println("Error");
//                    e.printStackTrace();
//                }
//        }
//        if (event.getEvent_type().equals("login")) {
//
//            try {
//                loginService.addLogin(event);
//            } catch (Exception e) {
//                System.out.println("Error");
//                e.printStackTrace();
//            }
//
//        }
//        if (event.getEvent_type().equals("transaction")) {
//            try {
//                transactionService.insert(event, rates);
//
//            } catch (Exception e) {
//                System.out.println("Error");
//                e.printStackTrace();
//            }
//        }
//
//
//    }

        public void insertEvents(List<Event> events, List<Rate> rates) {

            // Keep lists in memory
            LinkedList<Client> clients = new LinkedList<>();
            LinkedList<Login> logins = new LinkedList<>();
            LinkedList<Transaction> transactions = new LinkedList<>();

            for (Event event : events) {
                if (event.getEvent_type().equals("registration")) {
                    Client client = clientService.createClient(event);
                    if (client != null) {
                        clients.add(client);
                    }
                }
                else if (event.getEvent_type().equals("login")) {
                    Login login = loginService.createLogin(event);
                    if (login != null) {
                        logins.add(login);
                    }
                }
                else if (event.getEvent_type().equals("transaction")) {
                    Transaction transaction = transactionService.createTransaction(event, rates);
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                }
            }

            //Batch inserts
            clientService.saveList(clients);
            loginService.saveList(logins);
            transactionService.saveList(transactions);

            System.out.println("Finished loading");
        }

    public void deleteAll() {
        clientService.deleteAll();
        loginService.deleteAll();
        transactionService.deleteAll();
    }

}


//
//        if (event.getEvent_type().equals("transaction")) {
//            try {
//                eventsRepository.save(event);
//            } catch (Exception e) {
//                System.out.println("Error");
//                e.printStackTrace();
//            }
//        }
//
//
//    }


// ... i ostale podatke , kao sto je country, name itd
// provera da li postoji u bazi, ako ne postoji insert  u Users tabelu
//        } else if (event.getEvent_type().equals("login")) {
//            String user_id = event.getEvent_data().getUser_id();
//            long timestamp = event.getEvent_timestamp();
//            //  da li postoji u bazi, ako ne postoji ignorises
//            // ako postoji insert u tabeli Login za tog user_id i vreme
//
//        }


