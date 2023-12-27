package com.hergal.creditcardvalidatorapp.controller;




import com.hergal.creditcardvalidatorapp.model.CreditCard;
import com.hergal.creditcardvalidatorapp.service.CreditCardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/creditcards")
@Component
public class CreditCardController {

    private final CreditCardServiceInterface creditCardService;

    @Autowired
    public CreditCardController(CreditCardServiceInterface creditCardService) {
        this.creditCardService = creditCardService;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCreditCard(CreditCard creditCard) {
        CreditCard createdCard = creditCardService.saveCreditCard(creditCard);
        return Response.status(Response.Status.CREATED).entity(createdCard).build();
    }

    @GET
    @Path("/validate/{cardNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateCreditCard(@PathParam("cardNumber") String cardNumber) {
        boolean isValid = creditCardService.isCreditCardValid(cardNumber);

        if (isValid) {
            return Response.ok("Valid credit card").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid credit card").build();
        }

    }



}
