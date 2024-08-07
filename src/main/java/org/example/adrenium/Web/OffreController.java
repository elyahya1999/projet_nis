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

//    @GetMapping("/home")
//    public String home() {
//        return "index";
//    }
@GetMapping("/index")
public String home(Model model,
                   @RequestParam(name = "page", defaultValue = "0") int page,
                   @RequestParam(name = "size", defaultValue = "5") int size,
                   @RequestParam(name = "keyword", defaultValue = "") String keyword) {
    Page<Offre> pageOffres = offreManager.searchOffre(keyword, page, size);
    model.addAttribute("listOffres", pageOffres.getContent());
    model.addAttribute("pages", new int[pageOffres.getTotalPages()]);
    model.addAttribute("currentPage", page);
    model.addAttribute("keyword", keyword);
    return "index";
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
        if (offreManager.deleteOffre(id)) {
            return "redirect:/offres";
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