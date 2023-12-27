package com.hergal.creditcardvalidatorapp.repository;


import com.hergal.creditcardvalidatorapp.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    boolean existsByNumberAndExpiryDateAndControlNumberAndType(String number, String expiryDate, int controlNumber, String type);
}

