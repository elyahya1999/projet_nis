package org.example.adrenium.Service;


import org.example.adrenium.Dao.Entities.Candidat;
import org.example.adrenium.Dao.Repositories.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CandidatManagerService implements CandidatManager {

    @Autowired
    CandidatRepository candidatRepository;

    @Override
    public Optional<Candidat> getCandidatById(int id) {
        return candidatRepository.findById(id);
    }

    @Override
    public List<Candidat> getAllCandidat() {
        return candidatRepository.findAll();
    }

    @Override
    public Candidat addCandidat(Candidat candidat) {
        return candidatRepository.save(candidat);
    }

    @Override
    public Candidat updateCandidat(Candidat candidat) {
        return candidatRepository.save(candidat);
    }

    @Override
    public boolean deleteCandidat(int id) {

            try{
                candidatRepository.deleteById(id);
                return true;
            }catch (Exception exception){
                return false;
            }
        }

    @Override
    public Page<Candidat> searchCandidat(String keyword, int page, int size) {
        return candidatRepository.findByNomContains(keyword, PageRequest.of(page, size));

    }
}
