package com.emazon.stock_v1.infrastructure.out.jpa.repository;

import com.emazon.stock_v1.infrastructure.out.jpa.entity.ItemEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByName(String name);

    List<ItemEntity> findAll(Sort sort);

    List<ItemEntity> findByCategoriesId(Long categoriesId);

    List<ItemEntity> findByCategoriesId(Long categoriesId, Sort sort);

    List<ItemEntity> findByBrandName(String brandName);

    List<ItemEntity> findByBrandName(String brandName, Sort sort);

    @Query("update ItemEntity i set i.quantity = :quantity where i.id = :id")
    @Modifying
    void updateQuantity(@Param("id") Long itemId, @Param("quantity") Long quantity);
}
