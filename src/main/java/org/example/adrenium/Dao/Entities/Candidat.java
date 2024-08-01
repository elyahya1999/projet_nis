package org.example.adrenium.Dao.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Candidat extends Utilisateur {
    private double telephone;
    private String adresse;
    private enum domaine_expertise{
        info,
        compta
    };
    @OneToMany(mappedBy = "candidat")
    private Collection<Candidature> candidatures;


}
