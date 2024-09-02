package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Message;
import org.example.adrenium.Dao.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public Page<Message> getAllMessages(int page, int size) {
        return messageRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Message> findMessages(String keyword, int page, int size) {
        if (keyword != null && !keyword.isEmpty()) {
            return messageRepository.findByNameContainingOrEmailContainingOrSubjectContainingOrMessagefContaining(
                    keyword, keyword, keyword, keyword, PageRequest.of(page, size));
        } else {
            return messageRepository.findAll(PageRequest.of(page, size));
        }
    }
    public boolean deleteMessage(Long id) {
        try {
            messageRepository.deleteById(id);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
