package com.productio.production.dal;

import com.productio.production.models.Blueprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlueprintRepo extends JpaRepository<Blueprint, Long> {
}
