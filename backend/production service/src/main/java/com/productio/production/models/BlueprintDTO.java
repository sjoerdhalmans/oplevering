package com.productio.production.models;

public class BlueprintDTO {

    public BlueprintDTO(Item item, long materialId, long quantity) {
        this.item = item;
        this.materialId = materialId;
        this.quantity = quantity;
    }

    public BlueprintDTO() {

    }

    private Item item;

    private long materialId;

    private long quantity;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }
}
