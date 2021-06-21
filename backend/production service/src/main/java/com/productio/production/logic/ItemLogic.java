package com.productio.production.logic;

import com.productio.production.dal.ItemRepo;
import com.productio.production.models.Item;
import com.productio.production.models.ItemDTO;
import com.productio.production.models.ProductionLine;
import com.productio.production.models.ProductionLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemLogic {
    @Autowired
    ItemRepo productionItemRepo;

    public List<Item> getAll() {
        return productionItemRepo.findAll();
    }

    public void createItem(ItemDTO itemDTO) {
        Item item = new Item(
                itemDTO.getPrice(),
                itemDTO.getName(),
                itemDTO.getShortDescription(),
                itemDTO.getCurrency());

        productionItemRepo.save(item);
    }
}
