package org.example.adrenium.Web;

import jakarta.validation.Valid;
import org.example.adrenium.Dao.Entities.Blog;
import org.example.adrenium.Service.BlogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private BlogManager blogManager;



    @GetMapping("/blogs")
    public String blogs(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Blog> pageBlogs = blogManager.searchBlog(keyword, page, size);
        model.addAttribute("listBlogs", pageBlogs.getContent());
        model.addAttribute("pages", new int[pageBlogs.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        // Ajout de la liste des blogs au modèle
        model.addAttribute("blogs", pageBlogs.getContent());

        return "Blogs";
    }

    @PostMapping("/ajouterBlog")
    public String ajouterBlog(Model model,
                              @RequestParam(name = "title") String title,
                              @RequestParam(name = "author") String author,
                              @RequestParam(name = "content") String content,
                              @RequestParam(name = "image") String image,
                              @RequestParam(name = "datePublication") @DateTimeFormat(pattern = "yyyy-MM-dd") Date datePublication) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setAuteur(author);
        blog.setContenu(content);
        blog.setImage(image);
        blog.setCreationDate(datePublication);

        blogManager.addBlog(blog);

        return "redirect:/blogs";
    }

    @PostMapping("/ajouterOnceBlog")
    public String ajouterBlog(Model model,
                              @Valid Blog blog,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "ajouterBlog";
        }
        blogManager.addBlog(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/ajouterBlog")
    public String ajouterBlog(Model model) {
        model.addAttribute("blog", new Blog());
        return "ajouterBlog";
    }

    @GetMapping("/deleteBlog")
    public String deleteBlog(Model model, @RequestParam(name = "id") Integer id) {
        if (blogManager.deleteBlog(id)) {
            return "redirect:/blogs";
        } else {
            return "error";
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/editBlog")
    public String editBlog(Model model, @RequestParam("id") int id) {
        Optional<Blog> blog = blogManager.getBlogById(id);
        if (blog.isPresent()) {
            model.addAttribute("blogToBeUpdated", blog.get());
            return "updateBlog";
        } else {
            return "error";
        }
    }

    @PostMapping("/updateBlog")
    public String updateBlog(@Valid @ModelAttribute("blogToBeUpdated") Blog blog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateBlog";
        }
        blogManager.updateBlog(blog);
        return "redirect:/blogs";
    }
    @GetMapping("/blog-f")
    public String blog_f(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Blog> pageBlogs = blogManager.searchBlog(keyword, page, size);
        model.addAttribute("listBlogs", pageBlogs.getContent());
        model.addAttribute("pages", new int[pageBlogs.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "blog-fullwidth-v1";
    }
    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable("id") int id, Model model) {
        Optional<Blog> blog = blogManager.getBlogById(id);
        if (blog.isPresent()) {
            model.addAttribute("blog", blog.get());
            return "blog-details";
        } else {
            return "error";
        }
    }


}