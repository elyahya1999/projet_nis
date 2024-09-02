package org.example.adrenium.Dao.Repositories;

import org.example.adrenium.Dao.Entities.CandidatureSpontanee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidatureSpontaneeRepository extends JpaRepository<CandidatureSpontanee, Long>{
    @Query("SELECT c FROM CandidatureSpontanee c WHERE " +
            "LOWER(c.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.mail) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<CandidatureSpontanee> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}
