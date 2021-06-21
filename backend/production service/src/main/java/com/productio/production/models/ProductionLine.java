package com.productio.production.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "productionlines")
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ProductionLine {

    public ProductionLine(long id, @NotBlank boolean active, @NotBlank Item producedItem, @NotBlank long quantityPerMinute) {
        this.id = id;
        this.active = active;
        this.producedItem = producedItem;
        this.quantityPerMinute = quantityPerMinute;
    }

    public ProductionLine(@NotBlank boolean active, @NotBlank Item producedItem, @NotBlank long quantityPerMinute) {
        this.active = active;
        this.producedItem = producedItem;
        this.quantityPerMinute = quantityPerMinute;
    }

    public ProductionLine() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produced_Item")
    private Item producedItem;

    @NotBlank
    private boolean active;

    @NotBlank
    private long quantityPerMinute;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

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
