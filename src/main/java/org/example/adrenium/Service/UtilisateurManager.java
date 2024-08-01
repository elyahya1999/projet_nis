package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UtilisateurManager {
    Optional<Utilisateur> getUtilisateurById(int id);
    List<Utilisateur> getAllUtilisateur();
    Utilisateur addUtilisateur(Utilisateur utilisateur);
    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    boolean deleteUtilisateur(int id);

    Page<Utilisateur> searchUtilisateur(String keyword, int page, int size);

}
