package org.example.adrenium.Dao.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity@Data@NoArgsConstructor@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String contenu;
    private Date creationDate;
    private String auteur;
    private String image;
    @ManyToOne
    private Recruteur recruteur;
}
