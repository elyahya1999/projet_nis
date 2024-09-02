package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Candidature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CandidatureManager {
    Optional<Candidature> getCandidatureById(int id);
    List<Candidature> getAllCandidature();
    Candidature addCandidature(Candidature candidature);
    Candidature updateCandidature(Candidature candidature);
    boolean deleteCandidature(int id);

    Page<Candidature> searchCandidature(String keyword, int page, int size);

    Page<Candidature> findAllCandidatures(Pageable pageable);
}
