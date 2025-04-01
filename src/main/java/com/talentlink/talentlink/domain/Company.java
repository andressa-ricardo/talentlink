package com.talentlink.talentlink.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter

public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String email;
    private String cnpj;
    private String password;
    private String googleId;
    private String site;
    private String logo;
    private String linkedin;

    public Company(String name, String email, String cnpj, String password, String linkedin){
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
        this.password = password;
        this.linkedin = linkedin;
    }
}

