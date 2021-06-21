package com.productio.production.logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.productio.production.ProductionApplication;
import com.productio.production.models.ProductionLine;
import com.productio.production.redis.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import com.productio.production.models.InventoryOrder;
import com.productio.production.redis.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {
    @Autowired
    ProductionLineLogic productionLineLogic;
    @Autowired
    private ApplicationContext context;

    @Scheduled(fixedRate = 60000)
    public void runProductionLines() throws JsonProcessingException, InterruptedException {
        System.out.println("Starting ProductionLine Calculation");
        List<InventoryOrder> neededMaterials = productionLineLogic.calculateProductionCost();

        StringRedisTemplate template = context.getBean(StringRedisTemplate.class);

        for (InventoryOrder material : neededMaterials) {
            removeMaterials(material, template);
        }

        for (ProductionLine productionLine : productionLineLogic.getActiveProductionLines()) {
            addItems(productionLine, template);
        }

        System.out.println("Handled " + neededMaterials.size() + " inventory request(s)");
        System.out.println("Handled " + productionLineLogic.getActiveProductionLines().size() + " fabrication request(s)");
        System.out.println("Finishing ProductionLine Calculation");
    }

    private void addItems(ProductionLine productionLine, StringRedisTemplate template) throws JsonProcessingException, InterruptedException {
        InventoryOrder inventoryOrder = new InventoryOrder(
                "Produced Item",
                productionLine.getProducedItem().getId(),
                productionLine.getQuantityPerMinute());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(inventoryOrder);

        template.convertAndSend("addInvItem", json);
        Thread.sleep(10);
    }

    private void removeMaterials(InventoryOrder material, StringRedisTemplate template) throws JsonProcessingException, InterruptedException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(material);

        template.convertAndSend("removeInvItem", json);
        Thread.sleep(10);
    }
}