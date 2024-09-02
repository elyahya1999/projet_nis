package org.example.adrenium.Dao.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data@NoArgsConstructor@AllArgsConstructor
@Entity
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String entreprise;
    private String description;
    private String image;
    private String localisation;
    private String typeContrat;
    private String competencesRequises;
    private Date datePublication;
    private Date dateExpiration;
    @OneToMany(mappedBy = "offre")
    private Collection<Candidature> candidatures;
    @ManyToOne
    private Recruteur recruteur;

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", entreprise='" + entreprise + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", localisation='" + localisation + '\'' +
                ", typeContrat='" + typeContrat + '\'' +
                ", competencesRequises='" + competencesRequises + '\'' +
                ", datePublication=" + datePublication +
                ", dateExpiration=" + dateExpiration +
                ", candidatures=" + candidatures +
                ", recruteur=" + recruteur +
                '}';
    }
    public String getTitre() {
        return title;
    }
}
