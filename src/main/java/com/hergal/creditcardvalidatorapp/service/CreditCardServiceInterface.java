package com.hergal.creditcardvalidatorapp.service;

import com.hergal.creditcardvalidatorapp.model.CreditCard;

import java.util.List;

public interface CreditCardServiceInterface {



    CreditCard getCreditCard(Long id);

    CreditCard saveCreditCard(CreditCard creditCard);

     boolean isCreditCardValid(String creditCardNumber);


    CreditCard saveCreditCardDetails(CreditCard creditCard);

    List<CreditCard> getAllCreditCards();


    boolean isCreditCardPresent(String cardNumber, String firstName, String lastName, String expiryDate);
}
