package com.nordeus.challenge.repository;

import com.nordeus.challenge.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {


//    @Query("SELECT u FROM Client u WHERE u.user_id = ?1")
//    Optional<Client> findbyId(String id);

    @Query("select count(u) from Login u where u.userId = ?1 ")
    Long countLoginsByUserId(String user_id);

    @Query("select count(u) from Login u where u.userId = ?1 and u.createdAt between ?2 and ?3 ")
    Long countLoginsByUserIdAndDate(String user_id, LocalDateTime start_date, LocalDateTime end_date);


    @Query("select count(u) from Login u")
    Long countLogin();

    @Query("select count(u) from Login u  where u.createdAt between ?1 and ?2")
    Long countLoginByDate(LocalDateTime start_date, LocalDateTime end_date);

    @Query("select count(u) from Login u left join Client c on c.user_id=u.userId where c.country=?1")
    Long countLoginByCountry(String country);

    @Query("select count(u) from Login u left join Client c on c.user_id=u.userId where c.country=?1 and u.createdAt between ?2 and ?3")
    Long countLoginByCountryAndDate(String country, LocalDateTime start_date, LocalDateTime end_date);


    @Query("SELECT count(DISTINCT u.userId) FROM Login  u left join Client c on c.user_id=u.userId")
    Long countDailyActiveUsers();

    @Query("SELECT count(DISTINCT u.userId) FROM Login  u left join Client c on c.user_id=u.userId where c.country=?1")
    Long countDailyActiveByCountry(String country);

    @Query("SELECT count(DISTINCT u.userId) FROM Login  u left join Client c on c.user_id=u.userId where u.createdAt between ?1 and ?2")
    Long countDailyActiveByDate(LocalDateTime start_date, LocalDateTime end_date);

    @Query("SELECT count(DISTINCT u.userId) FROM Login  u left join Client c on c.user_id=u.userId where c.country=?1  and u.createdAt between ?2 and ?3")
    Long countDailyActiveUsersByCountryAndDate(String country, LocalDateTime start_date, LocalDateTime end_date);

    Optional<Login> findFirstByUserIdOrderByCreatedAtDesc(String user_id);

    Optional<Login> findFirstByUserIdAndCreatedAtLessThanOrderByCreatedAtDesc(String userId,LocalDateTime start_date);

    @Modifying
    @Transactional
    @Query(
            value = "truncate table login",
            nativeQuery = true
    )
    void truncate();

}
