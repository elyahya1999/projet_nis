package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Candidat;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CandidatManager {
    Optional<Candidat> getCandidatById(int id);
    List<Candidat> getAllCandidat();
    Candidat addCandidat(Candidat candidat);
    Candidat updateCandidat(Candidat candidat);
    boolean deleteCandidat(int id);

    Page<Candidat> searchCandidat(String keyword, int page, int size);
}
