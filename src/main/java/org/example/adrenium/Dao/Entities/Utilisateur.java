package org.example.adrenium.Dao.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private enum role {
        Candidat,
        Recruteur
    }


}
