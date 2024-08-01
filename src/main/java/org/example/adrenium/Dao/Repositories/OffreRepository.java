package org.example.adrenium.Dao.Repositories;

import org.example.adrenium.Dao.Entities.Offre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffreRepository extends JpaRepository<Offre, Integer> {
    Page<Offre> findByTitleContains(String keyword, PageRequest of);
}
