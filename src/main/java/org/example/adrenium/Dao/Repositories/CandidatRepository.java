package org.example.adrenium.Dao.Repositories;

import org.example.adrenium.Dao.Entities.Candidat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatRepository extends JpaRepository<Candidat, Integer> {
    Page<Candidat> findByNomContains(String keyword, PageRequest of);
}
