package com.example.tall.ahmad.structurecovid.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Redezvous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rv_id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_rv;
    private int resultat;
    private boolean objet;
    private int etat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utilisateur users;

    @ManyToOne
    @JoinColumn(name = "struct_id")
    private Structure structures;

    @Column(name = "rfd_next_rv_url")
    private String next_rv;

}
