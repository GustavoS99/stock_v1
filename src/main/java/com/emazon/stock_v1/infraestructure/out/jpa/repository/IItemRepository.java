package com.emazon.stock_v1.infraestructure.out.jpa.repository;

import com.emazon.stock_v1.infraestructure.out.jpa.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByName(String name);
}
