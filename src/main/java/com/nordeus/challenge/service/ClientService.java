package com.nordeus.challenge.service;

import com.nordeus.challenge.model.Client;
import com.nordeus.challenge.model.Event;
import com.nordeus.challenge.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class ClientService {

    public final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Event event) {
        String user_id = event.getEvent_data().getUser_id();
        String name = event.getEvent_data().getName();
        String country = event.getEvent_data().getCountry();
        String device_os = event.getEvent_data().getDevice_os();

        long timestamp = event.getEvent_timestamp() * 1000L;
        LocalDateTime created_at =
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId()
                );

        // If any information is null skip it
        if (user_id == null || name == null || country == null || device_os == null || timestamp == 0) {
            return null;
        }

        try {
            return new Client(user_id, name, country, device_os, created_at);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        // If exception triggered
        return null;

    }


    public void saveList(List<Client> clients) {
        clientRepository.saveAll(clients);
    }

    public void deleteAll() {
        clientRepository.truncate();
    }

    public Client findOneByUserId(String user_id) {
        Optional<Client> client = clientRepository.findOneByUserId(user_id);
        return client.isPresent() ? client.get() : null;
    }
}


