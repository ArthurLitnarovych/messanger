package com.intellekta.messenger;

import com.intellekta.messenger.entity.Message;
import com.intellekta.messenger.repository.MessageRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        return "home";
    }

    @GetMapping("/messages")
    public String showMessagesPage(Model model, @RequestParam(value = "filterName", required = false) String filterName) {
        if (filterName != null && !filterName.isEmpty()) {
            List<Message> filteredMessages = messageRepository.findBySenderName(filterName);
            model.addAttribute("messages", filteredMessages);
        } else {
            List<Message> messages = messageRepository.findAllByOrderBySentAtDesc();
            model.addAttribute("messages", messages);
        }
        return "messages";
    }
    @PostMapping("/saveSenderName")
    public String saveSenderName(@RequestParam("senderName") String senderName, HttpSession session) {
        session.setAttribute("senderName", senderName);
        return "redirect:/";
    }


    @PostMapping("/send")
    public String addMessage(@RequestParam("text") String text, HttpSession session) {
        String senderName = (String) session.getAttribute("senderName");

        Message message = new Message();
        message.setText(text);
        message.setSenderName(senderName);
        message.setSentAt(new Date());
        messageRepository.save(message);
        return "redirect:/messages";
    }

    @GetMapping("/clearSession")
    public String clearSession(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // Очистка сессии
        return "redirect:/";
    }
}


