package com.intellekta.messenger;

import com.intellekta.messenger.repository.MessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.intellekta.messenger")
public class MessengerApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MessengerApplication.class, args);
        MessageRepository repository = context.getBean(MessageRepository.class);
        repository.deleteAll();
    }
}
