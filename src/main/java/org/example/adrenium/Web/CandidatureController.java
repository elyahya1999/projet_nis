package org.example.adrenium.Web;

import org.example.adrenium.Dao.Entities.Candidature;
import org.example.adrenium.Dao.Entities.Offre;
import org.example.adrenium.Service.OffreManager;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class CandidatureController {

    @Autowired
    private OffreManager offreManager;

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
        if (!offre.isPresent()) {
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
        // candidatureService.save(candidature);

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
        String filePath = "C:/Users/yahya/OneDrive/Documents/testar"; // Assurez-vous que ce chemin est correct
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs(); // Crée le répertoire s'il n'existe pas
        }
        File dest = new File(filePath + fileName);
        file.transferTo(dest);
        return fileName;
    }
}


