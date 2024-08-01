package org.example.adrenium.Dao.Repositories;

import org.example.adrenium.Dao.Entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Page<Utilisateur> findByNomContains(String keyword, PageRequest of);
}
