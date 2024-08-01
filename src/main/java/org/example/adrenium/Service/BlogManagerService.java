package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Blog;
import org.example.adrenium.Dao.Repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class BlogManagerService implements BlogManager{

    @Autowired
    BlogRepository blogRepository;

    @Override
    public Optional<Blog> getBlogById(int id) {
        return blogRepository.findById((long) id);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog addBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public boolean deleteBlog(int id) {

            try{
                blogRepository.deleteById((long) id);
                return true;
            }catch (Exception exception){
                return false;
            }
        }


    @Override
    public Page<Blog> searchBlog(String keyword, int page, int size) {
        return blogRepository.findByTitleContains(keyword, PageRequest.of(page, size));
    }
}
