package com.urban.backend.features.resetCode;

import com.urban.backend.features.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reset_codes", schema = "jodo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;
    private Instant codeExpiration;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
