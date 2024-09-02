package org.example.adrenium.Web;

import org.example.adrenium.Dao.Entities.Message;
import org.example.adrenium.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("message", new Message());
        return "contact-1";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@ModelAttribute("message") Message message) {
        // Ajoutez une vérification des données reçues pour voir si elles sont correctes
        System.out.println("Received Message: " + message.toString());

        messageService.saveMessage(message);
        return "redirect:/contact";
    }

    @GetMapping("/messages")
    public String viewMessages(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               Model model) {
        Page<Message> messagePage = messageService.findMessages(keyword, page, 5);
        model.addAttribute("messages", messagePage.getContent());
        model.addAttribute("pages", messagePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "messages";
    }
    @GetMapping("/message/supprimer/{id}")
    public String supprimerMessage(@PathVariable("id") Long id, Model model) {
        try {
            boolean isDeleted = messageService.deleteMessage(id);
            if (isDeleted) {
                return "redirect:/messages"; // Redirige vers la liste des messages
            } else {
                model.addAttribute("errorMessage", "Le message n'a pas pu être supprimé.");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de la suppression du message: " + e.getMessage());
            return "error";
        }
    }
}
