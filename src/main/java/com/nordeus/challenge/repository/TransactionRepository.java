package com.nordeus.challenge.repository;

import com.nordeus.challenge.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {


    @Query("select count(u) from Transaction u where u.user_id = ?1")
    Long countTransactionByUserId(String user_id);

    @Query("select count(u) from Transaction u where u.user_id = ?1 and u.created_at between ?2 and ?3 ")
    Long countTransactionByUserIdAndDate(String user_id, LocalDateTime start_date, LocalDateTime end_date);

    @Query("select sum(u.transaction_amount_usd) from Transaction u where u.user_id = ?1")
    Optional<Double> sumTransactionByUserId(String user_id);
    @Query("select sum(u.transaction_amount_usd) from Transaction u where u.user_id = ?1 and u.created_at between ?2 and ?3 ")
    Optional<Double> sumTransactionByUserIdAndDate(String user_id, LocalDateTime start_date, LocalDateTime end_date);

    @Query("select count(u) from Transaction u")
    Long countTransaction();

    @Query("select count(u) from Transaction u  where u.created_at between ?1 and ?2")
    Long countTransactionByDate(LocalDateTime start_date, LocalDateTime end_date);

    @Query("select count(u) from Transaction u left join Client c on c.user_id=u.user_id where c.country=?1")
    Long countTransactionByCountry(String country);

    @Query("select count(u) from Transaction u left join Client c on c.user_id=u.user_id where c.country=?1 and u.created_at between ?2 and ?3")
    Long countTransactionByCountryAndDate(String country,LocalDateTime start_date, LocalDateTime end_date);


    @Query("select sum(u.transaction_amount_usd) from Transaction u")
    Optional<Double> sumTransaction();

    @Query("select sum(u.transaction_amount_usd) from Transaction u  where u.created_at between ?1 and ?2")
    Optional<Double> sumTransactionByDate(LocalDateTime start_date, LocalDateTime end_date);

    @Query("select sum(u.transaction_amount_usd) from Transaction u left join Client c on c.user_id=u.user_id where c.country=?1")
    Optional<Double> sumTransactionByCountry(String country);

    @Query("select sum(u.transaction_amount_usd) from Transaction u left join Client c on c.user_id=u.user_id where c.country=?1 and u.created_at between ?2 and ?3")
    Optional<Double> sumTransactionByCountryAndDate(String country,LocalDateTime start_date, LocalDateTime end_date);




    @Modifying
    @Transactional
    @Query(
            value = "truncate table transaction",
            nativeQuery = true
    )
    void truncate();
}
