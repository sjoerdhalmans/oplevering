package com.productio.production.logic;

import com.productio.production.dal.BlueprintRepo;
import com.productio.production.models.Blueprint;
import com.productio.production.models.BlueprintDTO;
import com.productio.production.models.ProductionLine;
import com.productio.production.models.ProductionLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlueprintLogic {
    @Autowired
    BlueprintRepo blueprintRepo;

    public List<Blueprint> getAll() {
        return blueprintRepo.findAll();
    }

    public void createBlueprint(BlueprintDTO blueprintDTO) {
        Blueprint blueprint = new Blueprint(blueprintDTO.getItem(), blueprintDTO.getMaterialId(), blueprintDTO.getQuantity());

        blueprintRepo.save(blueprint);
    }
}
