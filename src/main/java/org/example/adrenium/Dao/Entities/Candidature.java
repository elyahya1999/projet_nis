package org.example.adrenium.Dao.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity@NoArgsConstructor@AllArgsConstructor
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date dateCandidature;
    private String cv;
    private String lettreDeMotivation;
    @ManyToOne
    private Candidat candidat;
    @ManyToOne
    private Offre offre;
}
