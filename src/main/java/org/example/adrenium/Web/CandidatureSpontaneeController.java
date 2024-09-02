package org.example.adrenium.Web;

import org.example.adrenium.Dao.Entities.CandidatureSpontanee;
import org.example.adrenium.Service.CandidatureSpontaneeService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Controller
@RequestMapping("/candidature-spontanee")
public class CandidatureSpontaneeController {
    @Autowired
    private CandidatureSpontaneeService service;

    /**
     * Affiche le formulaire de candidature spontanée.
     */
    @GetMapping("/resume")
    public String showCandidatureForm() {
        return "resume";
    }

    /**
     * Soumet une candidature spontanée et sauvegarde les informations.
     */
    @PostMapping("/submit")
    public String submitCandidature(@RequestParam("nom") String nom,
                                    @RequestParam("prenom") String prenom,
                                    @RequestParam("mail") String mail,
                                    @RequestParam("profession") String profession,
                                    @RequestParam("telephone") String telephone,
                                    @RequestParam("cv") MultipartFile cvFile,
                                    @RequestParam("lettreDeMotivation") MultipartFile lettreDeMotivationFile,
                                    @RequestParam("contenu") String contenu,
                                    Model model) {
        CandidatureSpontanee candidature = new CandidatureSpontanee();
        candidature.setNom(nom);
        candidature.setPrenom(prenom);
        candidature.setMail(mail);
        candidature.setProfession(profession);
        candidature.setTelephone(telephone);
        candidature.setContenu(contenu);
        candidature.setDateSoumission(new Date());

        try {
            // Sauvegarde des fichiers
            String cvFileName = saveFile(cvFile);
            String lettreDeMotivationFileName = saveFile(lettreDeMotivationFile);

            candidature.setCvFileName(cvFileName);
            candidature.setLettreMotivationFileName(lettreDeMotivationFileName);

            service.saveCandidature(candidature);

            model.addAttribute("candidature", candidature);
            return "candidature-spontanee";  // La vue pour afficher les informations
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Erreur lors de la sauvegarde des fichiers");
            return "error";
        }
    }

    /**
     * Sauvegarde un fichier sur le disque.
     */
    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        String filePath = "C:/Users/yahya/OneDrive/Documents/tes/";  // Assurez-vous que ce chemin est correct
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();  // Crée le répertoire s'il n'existe pas
        }
        File dest = new File(filePath + fileName);
        file.transferTo(dest);
        return fileName;
    }

    /**
     * Liste les candidatures avec recherche et pagination.
     */
    @GetMapping("/candidatures")
    public String listCandidatures(Model model,
                                   @RequestParam(value = "search", required = false) String searchTerm,
                                   @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
                                   @RequestParam(value = "size", defaultValue = "5",required = false) Integer size) {
        page = (page == null) ? 0 : page;
        size = (size == null) ? 5 : size;
        if (searchTerm == null) {
            searchTerm = "";  // Default to an empty string if null
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<CandidatureSpontanee> candidaturePage = service.searchCandidatures(searchTerm, pageable);

        model.addAttribute("candidatures", candidaturePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", candidaturePage.getTotalPages());
        model.addAttribute("searchTerm", searchTerm);

        return "candidature-list";  // La vue pour afficher les candidatures avec pagination et recherche
    }
    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            // Log the filename received from the request
            System.out.println("Received request to download file: " + filename);

            Path filePath = Paths.get("C:/Users/yahya/OneDrive/Documents/tes/").resolve(filename).normalize();

            // Log the full file path
            System.out.println("Full file path resolved to: " + filePath.toString());

            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists and is readable
            if (resource.exists() && resource.isReadable()) {
                System.out.println("File exists and is readable: " + resource.getFilename());

                // Return the file with proper headers for download
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // Log if the file is not found or not readable
                System.out.println("File not found or not readable: " + filePath.toString());
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            // Log the exception if there's an issue with the URL resolution
            System.out.println("MalformedURLException: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/supprimer/{id}")
    public String supprimerCandidature(@PathVariable("id") Long id, Model model) {
        try {
            boolean isDeleted = service.supprimerCandidature(id);
            if (isDeleted) {
                return "redirect:/candidature-spontanee/candidatures";
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
