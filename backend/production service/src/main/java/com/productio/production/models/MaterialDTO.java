package com.productio.production.models;

public class MaterialDTO {
    public MaterialDTO() {

    }

    public MaterialDTO(String type, Long quantity, long itemId, long id) {
        this.type = type;
        this.quantity = quantity;
        this.itemId = itemId;
        this.id = id;
    }

    private String type;

    private Long quantity;

    private long itemId;

    private long id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
