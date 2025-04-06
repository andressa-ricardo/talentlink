package com.talentlink.talentlink.domain.company;

import com.talentlink.talentlink.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "companies")
@Getter
@Setter
@Table(name = "companies")
public class Company implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(unique = true, length = 50)
    private String cnpj;

    private String password;

    private String googleId;

    private String site;

    private String logo;

    private String linkedin;

    @Enumerated(EnumType.STRING)
    private Role role;



    public Company() {
    }

    public Company(String name, String email, String cnpj, String linkedin, String encryptedPassword, String role) {
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
        this.password = encryptedPassword;
        this.linkedin = linkedin;
        this.role = Role.COMPANY;
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
        return true;
    }
}
