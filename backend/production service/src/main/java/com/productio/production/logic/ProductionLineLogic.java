package com.productio.production.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.productio.production.dal.ProductionLineRepo;
import com.productio.production.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductionLineLogic {
    @Autowired
    ProductionLineRepo productionLineRepo;

    public List<ProductionLine> getAll() {
        return productionLineRepo.findAll();
    }

    public void createProductionLine(ProductionLineDTO productionLineDTO) {
        ProductionLine productionLine = new ProductionLine(false, productionLineDTO.getProducedItem(), productionLineDTO.getQuantityPerMinute());

        productionLineRepo.save(productionLine);
    }

    public void updateProductionLine(ProductionLine productionLine) {
        productionLineRepo.save(productionLine);
    }

    public void deleteProductionLine(Long productionLineId) {
        productionLineRepo.deleteById(productionLineId);
    }

    public List<ProductionLine> getActiveProductionLines() {
        List<ProductionLine> allLines = productionLineRepo.findAll();
        List<ProductionLine> activeLines = new ArrayList<>();

        for (ProductionLine line : allLines) {
            if (line.isActive()) {
                activeLines.add(line);
            }
        }

        return activeLines;
    }
    
    public List<InventoryOrder> calculateProductionCost() throws JsonProcessingException {
        List<ProductionLine> productionLines = getActiveProductionLines();
        List<InventoryOrder> requiredResources = new ArrayList<>();
        List<MaterialDTO> materials = getMaterials();

        for (ProductionLine productionLine : productionLines) {
            Boolean stocked = true;
            Item item = productionLine.getProducedItem();
            List<InventoryOrder> productionResources = new ArrayList<>();

            for (Blueprint resource : item.getRequiredMaterials()) {
                InventoryOrder inventoryOrder = new InventoryOrder("Material", resource.getMaterialId(), resource.getQuantity() * productionLine.getQuantityPerMinute());

                Boolean available = checkMaterialAvailability(inventoryOrder, materials);

                if (available) {
                    System.out.println("Requesting " + inventoryOrder.getQuantity() + " of ID " + inventoryOrder.getItemId());

                    productionResources.add(inventoryOrder);
                }
                else {
                    stocked = false;
                }
            }

            if (stocked) {
                requiredResources = joinLists(requiredResources, productionResources);
            }
            else {
                deactivateProductionLine(productionLine);
            }
        }

        return requiredResources;
    }

    private void deactivateProductionLine(ProductionLine productionLine) {
        productionLine.setActive(false);
        updateProductionLine(productionLine);
    }

    private Boolean checkMaterialAvailability(InventoryOrder inventoryOrder, List<MaterialDTO> materials) {
        Boolean available = false;
        MaterialDTO storedMaterial = findMaterial(inventoryOrder.getItemId(), materials);

        if (inventoryOrder.getQuantity() <= storedMaterial.getQuantity()) {
            available = true;
        }

        return available;
    }

    private MaterialDTO findMaterial(Long materialId, List<MaterialDTO> materials) {
        MaterialDTO foundMaterial = new MaterialDTO();

        for (MaterialDTO material : materials) {
            if (material.getItemId() == materialId) {
                foundMaterial = material;
            }
        }

        return foundMaterial;
    }

    private List<MaterialDTO> getMaterials() throws JsonProcessingException {
        String uri = "http://localhost:3500/getstoredmaterials";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.reader().forType(new TypeReference<List<MaterialDTO>>(){});
        return objectReader.readValue(result);
    }

    public static <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }

}
