package com.productio.production.controllers;

import com.productio.production.logic.ItemLogic;
import com.productio.production.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
    @Autowired
    ItemLogic itemLogic;

    //these are added straight in the database
    @GetMapping(value = "/getAllItems")
    public List<Item> getAll() {
        return itemLogic.getAll();
    }
}
