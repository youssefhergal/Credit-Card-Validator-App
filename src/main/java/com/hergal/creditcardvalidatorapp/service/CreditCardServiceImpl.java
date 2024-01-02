package com.hergal.creditcardvalidatorapp.service;


import com.hergal.creditcardvalidatorapp.model.CreditCard;
import com.hergal.creditcardvalidatorapp.repository.CreditCardRepository;
import com.hergal.creditcardvalidatorapp.util.LuhnAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardServiceInterface {

    private final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }


    @Override
    public CreditCard getCreditCard(Long id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    @Override
    public CreditCard saveCreditCard(CreditCard creditCard) {
        String newCreditCardNumber = LuhnAlgorithm.generateCreditCardNumber();

        // Create a CreditCard object with some dummy data
        CreditCard newCreditCard = new CreditCard();
        newCreditCard.setNumber(newCreditCardNumber);
        newCreditCard.setExpiryDate(String.valueOf(LocalDate.now().plusYears(5)));
        newCreditCard.setType(creditCard.getType());
        newCreditCard.setNom(creditCard.getNom());
        newCreditCard.setControlNumber(creditCard.getControlNumber());
        newCreditCard.setPrenom(creditCard.getPrenom());

        return creditCardRepository.save(newCreditCard);
    }
    @Override
    public boolean isCreditCardValid(String creditCardNumber) {
        return LuhnAlgorithm.validateCreditCardNumber(creditCardNumber);
    }


    @Override
    public CreditCard saveCreditCardDetails(CreditCard creditCard) {
        String newCreditCardNumber = LuhnAlgorithm.generateCreditCardNumber();
        creditCard.setNumber(newCreditCardNumber);
        return creditCardRepository.save(creditCard);
    }



    @Override
    public List<CreditCard> getAllCreditCards() {
        return  creditCardRepository.findAll();
    }

    @Override
    public boolean isCreditCardPresent(String cardNumber, String firstName, String lastName, String expiryDate) {

        return creditCardRepository.existsByNumberAndNomAndPrenomAndExpiryDate(cardNumber, lastName, firstName, expiryDate);
    }


}
