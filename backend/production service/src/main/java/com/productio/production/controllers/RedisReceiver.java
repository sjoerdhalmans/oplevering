package com.productio.production.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.productio.production.logic.BlueprintLogic;
import com.productio.production.logic.ItemLogic;
import com.productio.production.logic.ProductionLineLogic;
import com.productio.production.models.BlueprintDTO;
import com.productio.production.models.ItemDTO;
import com.productio.production.models.ProductionLine;
import com.productio.production.models.ProductionLineDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisReceiver {
    @Autowired
    ProductionLineLogic productionLineLogic;

    @Autowired
    ItemLogic itemLogic;

    @Autowired
    BlueprintLogic blueprintLogic;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisReceiver.class);

    public void createItem(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ItemDTO itemDTO = objectMapper.readValue(body, ItemDTO.class);

        itemLogic.createItem(itemDTO);
    }

    public void createBlueprint(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        BlueprintDTO blueprintDTO = objectMapper.readValue(body, BlueprintDTO.class);

        blueprintLogic.createBlueprint(blueprintDTO);
    }

    public void updateProductionLine(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductionLine productionLine = objectMapper.readValue(body, ProductionLine.class);

        productionLineLogic.updateProductionLine(productionLine);
    }

    public void createProductionLine(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductionLineDTO productionLineDTO = objectMapper.readValue(body, ProductionLineDTO.class);

        productionLineLogic.createProductionLine(productionLineDTO);
    }

    public void deleteProductionLine(String body) {
        this.productionLineLogic.deleteProductionLine(Long.parseLong(body));
    }
}