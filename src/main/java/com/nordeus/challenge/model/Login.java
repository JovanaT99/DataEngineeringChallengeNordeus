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
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name="user_id")
    private String userId;
    @Column(name="created_at")
    private LocalDateTime createdAt;

    public Login(String userId, LocalDateTime createdAt) {
        this.userId = userId;
        this.createdAt = createdAt;
    }
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Client client;

}
