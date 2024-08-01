package org.example.adrenium.Dao.Repositories;

import org.example.adrenium.Dao.Entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findByTitleContains(String keyword, PageRequest of);
}
