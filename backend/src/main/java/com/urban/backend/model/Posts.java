package com.urban.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "posts", schema = "jodo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] photo;

}
