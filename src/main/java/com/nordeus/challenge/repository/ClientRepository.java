package com.nordeus.challenge.repository;

import com.nordeus.challenge.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {



    @Query("SELECT u FROM Client u WHERE u.user_id = ?1")
    Optional<Client> findOneByUserId(String user_id);

    @Modifying
    @Transactional
    @Query(
            value = "truncate table client",
            nativeQuery = true
    )
    void truncate();
}
