package com.example.tall.ahmad.structurecovid.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Structure implements Comparable<Structure>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long struct_id;
    private String nom;
    private String adresse;
    private int contact;
    private double latitude;
    private double longitude;
    private int nbre_test;
    private int nbre_vac;
    private int nbr_test_dispo;
    private int nbr_vaccin_dispo;

    @Column(name = "rfd_rv_list_url")
    private String struct_rv_list;


    @Override
    public int compareTo(Structure o) {
        if(this.nbr_vaccin_dispo < o.nbr_vaccin_dispo || this.nbr_test_dispo < o.nbr_test_dispo)
            return 1;
        else
            return -1;
    }
}
