package ua.kusakabe.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic transactionTopic(){
        return new NewTopic("transactions", 1, (short) 1);
    }

}
