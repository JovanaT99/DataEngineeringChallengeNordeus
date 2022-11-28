package com.nordeus.challenge.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table

public class Client {

    @Id
    private String user_id;
    private String name;
    private String country;
    private String device_os;
    private LocalDateTime created_at;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
//    private List<Login> loginList;

}
