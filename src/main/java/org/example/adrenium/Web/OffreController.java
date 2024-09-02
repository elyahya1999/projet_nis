package org.example.adrenium.Web;
import jakarta.validation.Valid;
import org.example.adrenium.Dao.Entities.Offre;
import org.example.adrenium.Service.OffreManager;
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
public class OffreController {

    @Autowired
    private OffreManager offreManager;

    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("/error")
    public String error() {
        return "404-2";
    }
    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "about-us";
    }
//    @GetMapping("/blog-f")
//    public String blog_f() {
//        return "blog-fullwidth-v1";
//    }
    @GetMapping("/blog-fu")
    public String blog_fu() {
        return "blog-fullwidth-v2";
    }
    @GetMapping("/blog-l")
    public String blog_l() {
        return "blog-left-sidebar-v1";
    }
    @GetMapping("/blog-le")
    public String blog_le() {
        return "blog-left-sidebar-v2";
    }
    @GetMapping("/blog-r")
    public String blog_r() {
        return "blog-right-sidebar-v2";
    }
    @GetMapping("/blog-m")
    public String blog_m() {
        return "blog-masonry-4col";
    }
    @GetMapping("/blog-p")
    public String blog_p() {
        return "blog-post-right-sidebar";
    }
    @GetMapping("/button")
    public String buttons() {
        return "buttons";
    }
//    @GetMapping("/contact")
//    public String contact() {
//        return "contact-1";
//    }
    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }
    @GetMapping("/fa")
    public String fa() {
        return "footer4";
    }
    @GetMapping("/header")
    public String header() {
        return "header4";
    }
    @GetMapping("/job")
    public String job() {
        return "pricing-tables";
    }
    @GetMapping("/privacy")
    public String privacy() {
        return "privacy-policy";
    }
    @GetMapping("/resume")
    public String resume() {
        return "resume";
    }
//    @GetMapping("/jobP")
//    public String jobP() {
//        return "search-jobs-1";
//    }







    @GetMapping("/jobP")
public String home(Model model,
                   @RequestParam(name = "page", defaultValue = "0") int page,
                   @RequestParam(name = "size", defaultValue = "2") int size,
                   @RequestParam(name = "keyword", defaultValue = "") String keyword) {
    Page<Offre> pageOffres = offreManager.searchOffre(keyword, page, size);
    model.addAttribute("listOffres", pageOffres.getContent());
    model.addAttribute("pages", new int[pageOffres.getTotalPages()]);
    model.addAttribute("currentPage", page);
    model.addAttribute("keyword", keyword);
    return "search-jobs-1";
}


    @GetMapping("/offres")
    public String offres(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Offre> pageOffres = offreManager.searchOffre(keyword, page, size);
        model.addAttribute("listOffres", pageOffres.getContent());
        model.addAttribute("pages", new int[pageOffres.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        // Ajout de la liste des offres au modèle
        model.addAttribute("offres", pageOffres.getContent());

        return "Offres";
    }

    @PostMapping("/ajouterOffre")
    public String ajouterOffre(Model model,
                               @RequestParam(name = "title") String title,
                               @RequestParam(name = "entreprise") String entreprise,
                               @RequestParam(name = "description") String description,
                               @RequestParam(name = "image") String image,
                               @RequestParam(name = "localisation") String localisation,
                               @RequestParam(name = "typeContrat") String typeContrat,
                               @RequestParam(name = "competencesRequises") String competencesRequises,
                               @RequestParam(name = "datePublication") @DateTimeFormat(pattern = "yyyy-MM-dd") Date datePublication,
                               @RequestParam(name = "dateExpiration") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateExpiration) {
        Offre offre = new Offre();
        offre.setTitle(title);
        offre.setEntreprise(entreprise);
        offre.setDescription(description);
        offre.setImage(image);
        offre.setLocalisation(localisation);
        offre.setTypeContrat(typeContrat);
        offre.setCompetencesRequises(competencesRequises);
        offre.setDatePublication(datePublication);
        offre.setDateExpiration(dateExpiration);

        offreManager.addOffre(offre);

        return "redirect:/offres";
    }

    @PostMapping("/ajouterOnce")
    public String ajouterOffre(Model model,
                               @Valid Offre offre,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "ajouterOffre";
        }
        offreManager.addOffre(offre);
        return "redirect:/offres";
    }

    @GetMapping("/ajouterOffre")
    public String ajouterOffre(Model model) {
        model.addAttribute("offre", new Offre());
        return "ajouterOffre";
    }

    @GetMapping("/deleteOffre")
    public String deleteOffre(Model model, @RequestParam(name = "id") Integer id) {
        try {
            if (offreManager.deleteOffre(id)) {
                return "redirect:/offres";
            } else {
                model.addAttribute("errorMessage", "L'offre avec l'ID " + id + " n'a pas pu être supprimée.");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Une erreur s'est produite lors de la suppression de l'offre : " + e.getMessage());
            return "error";
        }
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/editOffre")
    public String editOffre(Model model, @RequestParam("id") int id) {
        Optional<Offre> offre = offreManager.getOffreById(id);
        if (offre.isPresent()) {
            model.addAttribute("offreToBeUpdated", offre.get());
            return "updateOffre";
        } else {
            return "error";
        }
    }

    @PostMapping("/updateOffre")
    public String updateOffre(@Valid @ModelAttribute("offreToBeUpdated") Offre offre, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateOffre";
        }
        offreManager.updateOffre(offre);
        return "redirect:/offres";
    }
    @GetMapping("/offre/{id}")
    public String getOffreDetails(@PathVariable("id") int id, Model model) {
        Optional<Offre> offre = offreManager.getOffreById(id);
        if (offre.isPresent()) {
            model.addAttribute("offre", offre.get());
            return "offreDetails";
        } else {
            return "error";
        }
    }

}