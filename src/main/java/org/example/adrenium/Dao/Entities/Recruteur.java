package org.example.adrenium.Dao.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recruteur extends Utilisateur{
   // private List<Offre> offrelist;
   // private List<Blog> bloglist ;
    @OneToMany(mappedBy = "recruteur")
    private Collection<Blog> blogs;
    @OneToMany(mappedBy = "recruteur")
    private Collection<Offre> offre;


}
