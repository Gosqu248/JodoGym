package com.urban.backend.features.sativaProduct;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


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

    @ElementCollection
    @CollectionTable(name = "sativa_product_category", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "category_name")
    private List<String> categories = new ArrayList<>();
}
