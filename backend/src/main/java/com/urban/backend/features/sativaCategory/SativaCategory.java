package com.urban.backend.features.sativaCategory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "sativa_categories", schema = "jodo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SativaCategory {
    @Id
    private Long id;
    private String name;
    private String img;
}
