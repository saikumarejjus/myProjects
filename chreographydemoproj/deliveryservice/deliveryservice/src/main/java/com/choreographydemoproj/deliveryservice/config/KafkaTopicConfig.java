package com.choreographydemoproj.deliveryservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.name.delivery-order}")
    String topicNameOrder ;

    @Value("${spring.kafka.topic.name.delivery-payment}")
    String topicNameDelivery ;
    @Bean
    @Primary
    public NewTopic addTopic()
    {
        return TopicBuilder.name(topicNameOrder)
                .build();
    }


    @Bean
    public NewTopic topic2() {

        return TopicBuilder.name(topicNameDelivery)
                .partitions(1)
                .replicas(1)
                .build();
    }

}
