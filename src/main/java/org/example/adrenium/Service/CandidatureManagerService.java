package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Candidature;
import org.example.adrenium.Dao.Repositories.CandidatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class CandidatureManagerService implements CandidatureManager{

    @Autowired
    CandidatureRepository candidatureRepository;

    @Override
    public Optional<Candidature> getCandidatureById(int id) {
        return candidatureRepository.findById(id);
    }

    @Override
    public List<Candidature> getAllCandidature() {
        return candidatureRepository.findAll();
    }

    @Override
    public Candidature addCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Override
    public Candidature updateCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Override
    public boolean deleteCandidature(int id) {

            try{
                candidatureRepository.deleteById(id);
                return true;
            }catch (Exception exception){
                return false;
            }
    }


    @Override
    public Page<Candidature> searchCandidature(String keyword, int page, int size) {
        return candidatureRepository.findByOffre_TitleContainingIgnoreCase(keyword, PageRequest.of(page, size));

    }

    @Override
    public Page<Candidature> findAllCandidatures(Pageable pageable) {
        return candidatureRepository.findAll(pageable);
    }
}
