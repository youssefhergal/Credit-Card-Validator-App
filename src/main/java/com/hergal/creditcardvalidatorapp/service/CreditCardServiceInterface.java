package com.hergal.creditcardvalidatorapp.service;

import com.hergal.creditcardvalidatorapp.model.CreditCard;

public interface CreditCardServiceInterface {



    CreditCard getCreditCard(Long id);

    CreditCard saveCreditCard(CreditCard creditCard);

     boolean isCreditCardValid(String creditCardNumber);

    boolean isCreditCardExistsWithDetails(CreditCard creditCard);

    CreditCard saveCreditCardDetails(CreditCard creditCard);
}
