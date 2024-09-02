package org.example.adrenium.Dao.Repositories;

import org.example.adrenium.Dao.Entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>{
    Page<Message> findByNameContainingOrEmailContainingOrSubjectContainingOrMessagefContaining(
            String name, String email, String subject, String messagef, Pageable pageable);
}
