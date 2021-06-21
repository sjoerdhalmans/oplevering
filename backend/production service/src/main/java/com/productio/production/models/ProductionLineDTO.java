package com.productio.production.models;

public class ProductionLineDTO {

    public ProductionLineDTO(Item producedItem, long quantityPerMinute) {
        this.producedItem = producedItem;
        this.quantityPerMinute = quantityPerMinute;
    }

    public ProductionLineDTO() {

    }

    private Item producedItem;

    private long quantityPerMinute;

    public Item getProducedItem() {
        return producedItem;
    }

    public void setProducedItem(Item producedItem) {
        this.producedItem = producedItem;
    }

    public long getQuantityPerMinute() {
        return quantityPerMinute;
    }

    public void setQuantityPerMinute(long quantityPerMinute) {
        this.quantityPerMinute = quantityPerMinute;
    }
}
