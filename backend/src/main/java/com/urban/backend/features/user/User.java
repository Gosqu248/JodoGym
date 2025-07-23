package com.urban.backend.features.user;

import com.urban.backend.enums.Role;
import com.urban.backend.features.activity.Activity;
import com.urban.backend.features.membership.Membership;
import com.urban.backend.features.resetCode.ResetCode;
import com.urban.backend.features.userInfo.UserInfo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "jodo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean isFirstLogin = true;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Membership membership;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ResetCode resetCode;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> activity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
