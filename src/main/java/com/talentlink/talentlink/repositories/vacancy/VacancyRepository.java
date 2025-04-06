package com.talentlink.talentlink.repositories.vacancy;

import com.talentlink.talentlink.domain.company.Company;
import com.talentlink.talentlink.domain.vacancy.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VacancyRepository extends JpaRepository<Vacancy, UUID> {
    List<Vacancy> findByCompany(Company company);

}
