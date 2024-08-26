package com.emazon.stock_v1.infraestructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50,
            unique = true)
    private String name;
    @Column(length = 90,
            nullable = false)
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<ItemEntity> items = new HashSet<>();

    public CategoryEntity(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
