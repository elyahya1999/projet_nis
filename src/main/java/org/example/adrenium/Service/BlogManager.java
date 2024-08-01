package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BlogManager {
    Optional<Blog> getBlogById(int id);
    List<Blog> getAllBlogs();
    Blog addBlog(Blog blog);
    Blog updateBlog(Blog blog);
    boolean deleteBlog(int id);
    Page<Blog> searchBlog(String keyword, int page, int size);
}
