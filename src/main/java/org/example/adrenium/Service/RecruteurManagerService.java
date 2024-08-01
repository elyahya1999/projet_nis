package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Recruteur;
import org.example.adrenium.Dao.Repositories.RecruteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecruteurManagerService implements RecruteurManager{

    @Autowired
    RecruteurRepository recruteurRepository;

    @Override
    public Optional<Recruteur> getRecruteurById(int id) {
        return recruteurRepository.findById(id);
    }

    @Override
    public List<Recruteur> getAllRecruteur() {
        return recruteurRepository.findAll();
    }

    @Override
    public Recruteur addRecruteur(Recruteur recruteur) {
        return recruteurRepository.save(recruteur);
    }

    @Override
    public Recruteur updateRecruteur(Recruteur recruteur) {
        return recruteurRepository.save(recruteur);
    }

    @Override
    public boolean deleteRecruteur(int id) {
        try{
            recruteurRepository.deleteById(id);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public Page<Recruteur> searchRecruteur(String keyword, int page, int size) {
        return recruteurRepository.findByNomContains(keyword, PageRequest.of(page, size));

    }
}
