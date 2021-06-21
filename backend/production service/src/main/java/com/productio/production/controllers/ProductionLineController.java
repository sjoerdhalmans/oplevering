package com.productio.production.controllers;

import com.productio.production.logic.ProductionLineLogic;
import com.productio.production.models.ProductionLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductionLineController {
    @Autowired
    ProductionLineLogic productionLineLogic;

    //these are added straight in the database
    @GetMapping(value = "/getAllProductionLines")
    public List<ProductionLine> getAll() {
        return productionLineLogic.getAll();
    }
}
