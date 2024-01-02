package com.hergal.creditcardvalidatorapp.controller;




import com.hergal.creditcardvalidatorapp.model.CreditCard;
import com.hergal.creditcardvalidatorapp.service.CreditCardServiceInterface;
import com.hergal.creditcardvalidatorapp.util.LuhnAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("/creditcards")
@Component
public class CreditCardController {

    private final CreditCardServiceInterface creditCardService;

    @Autowired
    public CreditCardController(CreditCardServiceInterface creditCardService) {
        this.creditCardService = creditCardService;
    }

    @POST
    @Path("/verifierClient")
    @Produces(MediaType.TEXT_PLAIN)
    public Response verifyClient(@FormParam("cardNumber") String cardNumber) {
        boolean isValid = creditCardService.isCreditCardValid(cardNumber);

        String message;
        if (isValid) {
            message = "La carte est valide";
        } else {
            message = "La carte n'est pas valide";
        }

        return Response.ok(message).build();
    }




    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreditCard(@PathParam("id") Long id) {
        CreditCard creditCard = creditCardService.getCreditCard(id);

        if (creditCard != null) {
            return Response.ok(creditCard).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/creer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCreditCard(CreditCard creditCard) {
        System.out.println("testt");
        System.out.println(creditCard.toString());

        String generatedCardNumber = LuhnAlgorithm.generateCreditCardNumber();
        creditCard.setNumber(generatedCardNumber);

        // Set the expiration date (adjust the logic as needed)
        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = currentDate.plusYears(5);
        creditCard.setExpiryDate(String.valueOf(expirationDate));
        CreditCard createdCard = creditCardService.saveCreditCard(creditCard);
        System.out.println(creditCard.toString());
        return Response.status(Response.Status.CREATED).entity(createdCard).build();
    }


    @GET
    @Path("/validate/{cardNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean validateCreditCard(@PathParam("cardNumber") String cardNumber) {
        return creditCardService.isCreditCardValid(cardNumber);
    }

    @GET
    @Path("/viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllCreditCards() {
        List<CreditCard> creditCards = creditCardService.getAllCreditCards();
        return Response.ok(creditCards).build();
    }




}
