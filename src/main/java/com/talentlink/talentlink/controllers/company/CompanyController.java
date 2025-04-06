package com.talentlink.talentlink.controllers.company;

import com.talentlink.talentlink.dtos.company.CompanyRegisterDTO;
import com.talentlink.talentlink.domain.AuthenticationDTO;
import com.talentlink.talentlink.domain.company.Company;
import com.talentlink.talentlink.infra.security.TokenService;
import com.talentlink.talentlink.repositories.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/company")
public class CompanyController {

    @Autowired
    private AuthenticationManager companyAuthenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = companyAuthenticationManager.authenticate(usernamePassword);

        var company = companyRepository.findByEmail(data.email());
        if (company == null) {
            return ResponseEntity.badRequest().body("Empresa não encontrada!");
        }

        String token = tokenService.generateToken(company);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CompanyRegisterDTO data) {
        if (companyRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado!");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        Company newCompany = new Company(
                data.name(),
                data.email(),
                data.cnpj(),
                data.linkedin(),
                encryptedPassword,
                data.role()
        );
        companyRepository.save(newCompany);

        String token = tokenService.generateToken(newCompany);
        return ResponseEntity.ok().body(newCompany);
    }
}
