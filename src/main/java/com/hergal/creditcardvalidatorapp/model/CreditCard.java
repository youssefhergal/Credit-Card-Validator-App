package com.hergal.creditcardvalidatorapp.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credit_card_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String expiryDate;
    private int controlNumber;
    private String type;
    private String nom ;
    private String prenom ;


}
