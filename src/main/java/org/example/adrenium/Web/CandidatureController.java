package org.example.adrenium.Web;

import org.example.adrenium.Dao.Entities.Candidature;
import org.example.adrenium.Dao.Entities.Offre;
import org.example.adrenium.Service.CandidatureManager;
import org.example.adrenium.Service.OffreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class CandidatureController {

    @Autowired
    private OffreManager offreManager;

    @Autowired
    private CandidatureManager candidatureManager;
    // CandidatureController.java
//    @GetMapping("/candidaturesP")
//    public String candidatures(Model model,
//                               @RequestParam(name = "page", defaultValue = "0") int page,
//                               @RequestParam(name = "size", defaultValue = "5") int size,
//                               @RequestParam(name = "keyword", defaultValue = "") String keyword) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Candidature> pageCandidatures;
//
//        // Vérifiez si un mot-clé est présent
//        if (keyword.isEmpty()) {
//            pageCandidatures = candidatureManager.findAllCandidatures(pageable);
//        } else {
//            pageCandidatures = candidatureManager.searchCandidature(keyword,page,size);  // Méthode modifiée pour utiliser le champ 'nom'
//        }
//
//        model.addAttribute("candidatures", pageCandidatures.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", pageCandidatures.getTotalPages());
//        model.addAttribute("keyword", keyword);
//        return "candidatureList";
//    }
    @GetMapping("/candidaturesP")
    public String candidatures(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "5") int size,
                               @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Candidature> pageCandidatures;

        if (keyword.isEmpty()) {
            pageCandidatures = candidatureManager.findAllCandidatures(pageable);
        } else {
            pageCandidatures = candidatureManager.searchCandidature(keyword, page, size);
        }

        model.addAttribute("candidatures", pageCandidatures.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageCandidatures.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "candidatureList";
    }




    @GetMapping("/candidature/form/{offreId}")
    public String showCandidatureForm(@PathVariable("offreId") int offreId, Model model) {
        Optional<Offre> offre = offreManager.getOffreById(offreId);
        if (offre.isPresent()) {
            model.addAttribute("offreId", offreId);
            return "candidatureForm";
        } else {
            return "error";
        }
    }

    @PostMapping("/candidature/submit")
    @ResponseBody
    public ResponseEntity<Map<String, String>> submitCandidature(@RequestParam("offreId") int offreId,
                                                                 @RequestParam("cv") MultipartFile cvFile,
                                                                 @RequestParam("lettreDeMotivation") MultipartFile lettreDeMotivationFile) {
        if (cvFile.isEmpty() || lettreDeMotivationFile.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Both files are required");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Offre> offre = offreManager.getOffreById(offreId);
        if (offre.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Offre not found");
            return ResponseEntity.badRequest().body(response);
        }

        Candidature candidature = new Candidature();
        candidature.setOffre(offre.get());

        try {
            String cvFileName = saveFile(cvFile);
            String lettreDeMotivationFileName = saveFile(lettreDeMotivationFile);
            candidature.setCv(cvFileName);
            candidature.setLettreDeMotivation(lettreDeMotivationFileName);
        } catch (IOException e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Error saving files");
            return ResponseEntity.status(500).body(response);
        }

        candidature.setDateCandidature(new Date());
        // Sauvegardez la candidature dans la base de données
        candidatureManager.addCandidature(candidature);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Candidature submitted successfully");
        return ResponseEntity.ok(response);
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }
        String fileName = file.getOriginalFilename();
        String filePath = "C:/Users/yahya/OneDrive/Documents/tes/"; // Assurez-vous que ce chemin est correct
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs(); // Crée le répertoire s'il n'existe pas
        }
        File dest = new File(filePath + fileName);
        file.transferTo(dest);
        return fileName;
    }

    @GetMapping("/candidatures")
    public String showCandidatures(Model model) {
        // Supposons que vous avez un service qui récupère les candidatures
        List<Candidature> candidatures = candidatureManager.getAllCandidature();
        model.addAttribute("candidatures", candidatures);
        return "candidatureList";
    }
    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("C:/Users/yahya/OneDrive/Documents/tes/").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/candidature/supprimer/{id}")
    public String supprimerCandidature(@PathVariable("id") int id, Model model) {
        try {
            boolean isDeleted = candidatureManager.deleteCandidature(id);
            if (isDeleted) {
                return "redirect:/candidaturesP"; // Redirige vers la liste des candidatures
            } else {
                model.addAttribute("errorMessage", "La candidature n'a pas pu être supprimée.");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la suppression de la candidature: " + e.getMessage());
            return "error";
        }
    }
}
