package com.talentlink.talentlink.domain.user;

import com.talentlink.talentlink.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    private String password;

    @Column(unique = true, length = 11)
    private String cpf;

    private boolean activated;

    private String avatar;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String googleId;

    public User() {
    }

    public User(String email, String encryptedPassword, Role role) {
        this.email = email;
        this.password = encryptedPassword;
        this.role = role;
        this.activated = true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.name()); // aqui ele vai retornar a role para o security
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activated;
    }
}
