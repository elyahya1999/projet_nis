package org.example.adrenium.Service;


import org.example.adrenium.Dao.Entities.Recruteur;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RecruteurManager {
    Optional<Recruteur> getRecruteurById(int id);
    List<Recruteur> getAllRecruteur();
    Recruteur addRecruteur(Recruteur recruteur);
    Recruteur updateRecruteur(Recruteur recruteur);
    boolean deleteRecruteur(int id);

    Page<Recruteur> searchRecruteur(String keyword, int page, int size);
}
