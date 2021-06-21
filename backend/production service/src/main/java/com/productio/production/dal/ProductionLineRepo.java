package com.productio.production.dal;

import com.productio.production.models.ProductionLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionLineRepo extends JpaRepository<ProductionLine, Long> {
}
