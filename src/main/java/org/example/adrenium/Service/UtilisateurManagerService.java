package org.example.adrenium.Service;


import org.example.adrenium.Dao.Entities.Utilisateur;
import org.example.adrenium.Dao.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UtilisateurManagerService implements UtilisateurManager{

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
    public Optional<Utilisateur> getUtilisateurById(int id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public boolean deleteUtilisateur(int id) {
        try{
            utilisateurRepository.deleteById(id);
            return true;
        }catch (Exception exception){
            return false;
        }
    }
    public Page<Utilisateur> searchUtilisateur(String keyword, int page, int size){
        return utilisateurRepository.findByNomContains(keyword, PageRequest.of(page, size));
    }

}



