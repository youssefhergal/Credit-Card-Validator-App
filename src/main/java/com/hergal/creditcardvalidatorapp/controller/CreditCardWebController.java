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
        System.out.println("etete");
        return "ajouter_card";
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

        return "ajouter_card";
    }


    @GetMapping("/viewAll")
    public String viewAllCreditCards(Model model) {
        List<CreditCard> creditCards = creditCardService.getAllCreditCards();
        model.addAttribute("creditCards", creditCards);
        return "afficher_cards"; // Nom de la vue à afficher
    }

    @GetMapping("/verifier")
    public String showVerifyCardForm(Model model) {
        return "chercher_card";
    }
    @PostMapping("/creditcards/verifierClient")
    public String verifyClient(
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("expiryDate") String expiryDate,
            Model model) {

        boolean isCardPresent = creditCardService.isCreditCardPresent(cardNumber, firstName, lastName, expiryDate);

        if (isCardPresent) {
            model.addAttribute("message", "La carte se trouve dans la base de données");
        } else {
            model.addAttribute("message", "La carte n'est pas présente dans la base de données");
        }

        return "chercher_card";
    }



}
