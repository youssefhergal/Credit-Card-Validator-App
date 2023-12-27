package com.hergal.creditcardvalidatorapp.controller;

import com.hergal.creditcardvalidatorapp.model.CreditCard;
import com.hergal.creditcardvalidatorapp.service.CreditCardServiceInterface;
import com.hergal.creditcardvalidatorapp.util.LuhnAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/api")
public class CreditCardWebController {

    private final CreditCardServiceInterface creditCardService;

    @Autowired
    public CreditCardWebController(CreditCardServiceInterface creditCardService) {
        this.creditCardService = creditCardService;
    }



    @GetMapping(path = "/isvalid")
    public String showForm() {
        return "navbar";
    }

    @PostMapping("/creditcards/isvalidenumber")
    public String validateCreditCard(@RequestParam("cardNumber") String cardNumber, Model model) {
        boolean isValid = creditCardService.isCreditCardValid(cardNumber);
        if (isValid) {
            model.addAttribute("message", "Le numéro de carte est valide");
        } else {
            model.addAttribute("message", "Le numéro de carte n'est pas valide");
        }
        return "navbar";
    }


    @GetMapping("/ajouter")
    public String showAddCardForm(Model model) {
        System.out.println("etete");
        return "form";
    }

    @PostMapping("/creditcards/saveCard")
    public String saveCreditCard(@ModelAttribute CreditCard creditCard, Model model) {
        System.out.println("testt");
        System.out.println(creditCard.toString());
        String generatedCardNumber = LuhnAlgorithm.generateCreditCardNumber();
        creditCard.setNumber(generatedCardNumber);

        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = currentDate.plusYears(5);
        creditCard.setExpiryDate(String.valueOf(expirationDate));

        creditCardService.saveCreditCard(creditCard);
        System.out.println(creditCard.toString());

        model.addAttribute("message", "Carte enregistrée avec succès");

        return "form";
    }




    @GetMapping("/creditcards/verifierClient")
    public String showCreditCardForm(Model model) {

        return null;
    }

}
