package com.talentlink.talentlink.repositories.company;

import com.talentlink.talentlink.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Company findByEmail(String email);
}
