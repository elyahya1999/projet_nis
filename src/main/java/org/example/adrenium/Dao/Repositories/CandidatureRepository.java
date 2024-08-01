package org.example.adrenium.Dao.Repositories;

import org.example.adrenium.Dao.Entities.Candidature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureRepository extends JpaRepository<Candidature, Integer> {
    Page<Candidature> findByCandidatContains(String keyword, PageRequest of);
}
