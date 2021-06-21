package com.productio.production.redis;

import com.productio.production.controllers.RedisReceiver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            @Qualifier("createItemAdapter") MessageListenerAdapter createItemAdapter,
                                            @Qualifier("createBlueprintAdapter") MessageListenerAdapter createBlueprintAdapter,
                                            @Qualifier("createProductionLineAdapter") MessageListenerAdapter createProductionLineAdapter,
                                            @Qualifier("updateProductionLineAdapter") MessageListenerAdapter updateProductionLineAdapter,
                                            @Qualifier("deleteProductionLineAdapter") MessageListenerAdapter deleteProductionLineAdapter){

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(createItemAdapter, new PatternTopic("createItem"));
        container.addMessageListener(createBlueprintAdapter, new PatternTopic("createBlueprint"));
        container.addMessageListener(createProductionLineAdapter, new PatternTopic("createProductionLine"));
        container.addMessageListener(updateProductionLineAdapter, new PatternTopic("updateProductionLine"));
        container.addMessageListener(deleteProductionLineAdapter, new PatternTopic("deleteProductionLine"));

        return container;
    }

    @Bean("createItemAdapter")
    MessageListenerAdapter createOrderAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "createItem");
    }

    @Bean("createBlueprintAdapter")
    MessageListenerAdapter updateOrderAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "createBlueprint");
    }

    @Bean("createProductionLineAdapter")
    MessageListenerAdapter createProductionLineAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "createProductionLine");
    }

    @Bean("updateProductionLineAdapter")
    MessageListenerAdapter updateProductionLineAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "updateProductionLine");
    }

    @Bean("deleteProductionLineAdapter")
    MessageListenerAdapter deleteProductionLineAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "deleteProductionLine");
    }

    @Bean
    RedisReceiver receiver() {
        return new RedisReceiver();
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}