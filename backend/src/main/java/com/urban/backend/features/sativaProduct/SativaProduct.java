package com.urban.backend.features.sativaProduct;

import com.urban.backend.features.sativaCategory.SativaCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "sativa_products", schema = "jodo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SativaProduct {
    @Id
    private Long id;
    private String title;
    private Double price;
    private String image;
    private String productUrl;

    @ManyToMany
    @JoinTable(
            name = "sativa_product_categories",
            schema = "jodo",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<SativaCategory> categories = new HashSet<>();
}
