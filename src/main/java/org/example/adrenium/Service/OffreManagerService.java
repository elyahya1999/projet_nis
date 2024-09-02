package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Offre;
import org.example.adrenium.Dao.Repositories.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OffreManagerService implements OffreManager{

    @Autowired
    OffreRepository offreRepository;
    @Override
    public Optional<Offre> getOffreById(int id) {
        return offreRepository.findById(id);
    }

    @Override
    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    @Override
    public Offre addOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    @Override
    public Offre updateOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    @Override
    public boolean deleteOffre(int id) {
        try {
            Optional<Offre> offre = offreRepository.findById(id);
            if (offre.isPresent()) {
                offreRepository.deleteById(id);
                return true;
            } else {
                return false; // L'offre n'existe pas
            }
        } catch (Exception exception) {
            // Log de l'erreur pour une meilleure traçabilité
            System.err.println("Erreur lors de la suppression de l'offre avec l'ID " + id + ": " + exception.getMessage());
            return false;
        }
    }


    @Override
    public Page<Offre> getAllOffre2(int page, int size) {
        return  offreRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public Page<Offre> searchOffre(String keyword, int page, int size) {
        if (keyword == null || keyword.isEmpty()) {
            return offreRepository.findAll(PageRequest.of(page, size));
        } else {
            return offreRepository.findByTitleContains(keyword, PageRequest.of(page, size));
        }
    }


    @Override
    public List<Offre> getByKeyword(String keyword) {
        return null;
    }
}
