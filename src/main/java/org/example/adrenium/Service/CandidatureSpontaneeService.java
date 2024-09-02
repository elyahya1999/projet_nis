package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.CandidatureSpontanee;
import org.example.adrenium.Dao.Repositories.CandidatureSpontaneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatureSpontaneeService{
    @Autowired
    CandidatureSpontaneeRepository repository;
    public Page<CandidatureSpontanee> searchCandidatures(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return repository.findAll(pageable);
        }
        return repository.search(searchTerm, pageable);
    }

    public CandidatureSpontanee saveCandidature(CandidatureSpontanee candidature) {
        return repository.save(candidature);
    }


    public List<CandidatureSpontanee> getAllCandidatures() {
        return repository.findAll();
    }


    public CandidatureSpontanee getCandidatureById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public boolean supprimerCandidature(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
