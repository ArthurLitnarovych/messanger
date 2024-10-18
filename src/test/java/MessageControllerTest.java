package com.intellekta.messenger;

import com.intellekta.messenger.entity.Message;
import com.intellekta.messenger.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    void shouldReturnHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("home"));
    }

    @Test
    void shouldReturnMessagesPageWithAllMessages() throws Exception {
        Message message = new Message();
        message.setText("Hello");
        message.setSenderName("John");
        message.setSentAt(new Date());

        when(messageRepository.findAllByOrderBySentAtDesc()).thenReturn(List.of(message));

        mockMvc.perform(MockMvcRequestBuilders.get("/messages"))
               .andExpect(status().isOk())
               .andExpect(view().name("messages"))
               .andExpect(model().attributeExists("messages"));
    }

    @Test
    void shouldFilterMessagesBySenderName() throws Exception {
        Message message = new Message();
        message.setText("Filtered message");
        message.setSenderName("Alice");
        message.setSentAt(new Date());

        when(messageRepository.findBySenderName("Alice")).thenReturn(List.of(message));

        mockMvc.perform(MockMvcRequestBuilders.get("/messages?filterName=Alice"))
               .andExpect(status().isOk())
               .andExpect(view().name("messages"))
               .andExpect(model().attributeExists("messages"));
    }

    @Test
    void shouldSaveSenderNameInSession() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/saveSenderName")
                        .param("senderName", "John"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/"));
    }

    @Test
    void shouldAddMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/send")
                        .param("text", "Hello from John")
                        .sessionAttr("senderName", "John"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/messages"));

        Mockito.verify(messageRepository, Mockito.times(1)).save(Mockito.any(Message.class));
    }

    @Test
    void shouldClearSession() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/clearSession"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/"));
    }
}
