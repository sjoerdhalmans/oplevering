package com.productio.production.models;

public class InventoryOrder {

    public InventoryOrder() {
    }

    public InventoryOrder(String type, long itemId, long quantity) {
        this.type = type;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    private String type;

    private long itemId;

    private long quantity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
