package org.example.adrenium.Dao.Repositories;

import org.example.adrenium.Dao.Entities.Recruteur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruteurRepository extends JpaRepository<Recruteur, Integer> {
    Page<Recruteur> findByNomContains(String keyword, PageRequest of);
}
