package com.productio.logistics.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "receipts")
@EntityListeners(AuditingEntityListener.class)
public class OrderReceipt {

    public OrderReceipt(long price, String currency, @NotBlank Timestamp finished, @NotBlank String materialName, @NotBlank long quantity) {
        this.price = price;
        this.currency = currency;
        this.finished = finished;
        this.materialName = materialName;
        this.quantity = quantity;
    }

    public OrderReceipt() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private long id;

    @Column(name = "price")
    private long price;

    private String currency;

    @NotBlank
    private Timestamp finished;

    @NotBlank
    private String materialName;

    @NotBlank
    private long quantity;

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public Timestamp getFinished() {
        return finished;
    }

    public void setFinished(Timestamp finished) {
        this.finished = finished;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
