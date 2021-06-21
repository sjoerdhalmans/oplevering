package com.productio.production.models;

import java.util.List;

public class ItemDTO {

    public ItemDTO(long price, String name, String shortDescription, String currency) {
        this.price = price;
        this.name = name;
        this.shortDescription = shortDescription;
        this.currency = currency;
    }

    public ItemDTO() {

    }

    private List<Blueprint> requiredMaterials;

    private long price;

    private String name;

    private String shortDescription;

    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public List<Blueprint> getRequiredMaterials() {
        return requiredMaterials;
    }

    public void setRequiredMaterials(List<Blueprint> requiredMaterials) {
        this.requiredMaterials = requiredMaterials;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
