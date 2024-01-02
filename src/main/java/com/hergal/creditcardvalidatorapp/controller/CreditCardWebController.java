package com.hergal.creditcardvalidatorapp.controller;

import com.hergal.creditcardvalidatorapp.model.CreditCard;
import com.hergal.creditcardvalidatorapp.service.CreditCardServiceInterface;
import com.hergal.creditcardvalidatorapp.util.LuhnAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
        return "valider_number";
    }

    @PostMapping("/creditcards/isvalidenumber")
    public String validateCreditCard(@RequestParam("cardNumber") String cardNumber, Model model) {
        boolean isValid = creditCardService.isCreditCardValid(cardNumber);
        if (isValid) {
            model.addAttribute("message", "Le numéro de carte est valide");
        } else {
            model.addAttribute("message", "Le numéro de carte n'est pas valide");
        }
        return "valider_number";
    }


    @GetMapping("/ajouter")
    public String showAddCardForm(Model model) {
        return "ajouter_card";
    }

    @PostMapping("/creditcards/saveCard")
    public String saveCreditCard(@ModelAttribute CreditCard creditCard, Model model) {
        System.out.println(creditCard.toString());
        String generatedCardNumber = LuhnAlgorithm.generateCreditCardNumber();
        creditCard.setNumber(generatedCardNumber);

        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = currentDate.plusYears(5);
        creditCard.setExpiryDate(String.valueOf(expirationDate));

        creditCardService.saveCreditCard(creditCard);
        System.out.println(creditCard.toString());

        model.addAttribute("message", "Carte enregistrée avec succès");

        return "ajouter_card";
    }


    @GetMapping("/viewAll")
    public String viewAllCreditCards(Model model) {
        List<CreditCard> creditCards = creditCardService.getAllCreditCards();
        model.addAttribute("creditCards", creditCards);
        return "afficher_cards";
    }

    @GetMapping("/verifier")
    public String showVerifyCardForm(Model model) {
        return "chercher_card";
    }

    @PostMapping("/creditcards/verifierClient")
    public String verifyClient(
            @RequestParam("cardNumber") String cardNumber,
            Model model) {

        // Sépare le nom et le prénom
//        String[] nameParts = fullName.split("\\s+");
//        String firstName = "";
//        String lastName = "";
//
//        if (nameParts.length > 0) {
//            lastName = nameParts[0];
//        }
//
//        if (nameParts.length > 1) {
//            firstName = nameParts[1];
//        }
//
//        // Concatène le mois et l'année pour former la date d'expiration complète
//        String expiryDate = expiryMonth + "/" + expiryYear;

        boolean isValid = creditCardService.isCreditCardValid(cardNumber);
        if (isValid) {
            model.addAttribute("message", "La carte est valide");
        } else {
            model.addAttribute("message", "La carte n'est pas valide");
        }

        return "chercher_card";
    }


}
