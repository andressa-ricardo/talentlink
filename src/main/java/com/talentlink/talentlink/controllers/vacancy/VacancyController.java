package com.talentlink.talentlink.controllers.vacancy;

import com.talentlink.talentlink.domain.company.Company;
import com.talentlink.talentlink.domain.vacancy.Vacancy;
import com.talentlink.talentlink.dtos.vacancy.VacancyRequestDTO;
import com.talentlink.talentlink.repositories.company.CompanyRepository;
import com.talentlink.talentlink.repositories.vacancy.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/company/vacancies")
public class VacancyController {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("register-company")
    public ResponseEntity<?> createVacancy(@RequestBody VacancyRequestDTO dto, Authentication authentication) {
        Company company = (Company) authentication.getPrincipal();

        Vacancy vacancy = new Vacancy();
        vacancy.setName(dto.name());
        vacancy.setDescription(dto.description());
        vacancy.setRequirements(dto.requirements());
        vacancy.setBenefits(dto.benefits());
        vacancy.setSteps(dto.steps());
        vacancy.setOthers(dto.others());
        vacancy.setLink(dto.link());
        vacancy.setLanguage(dto.language());
        vacancy.setContractType(dto.contractType());
        vacancy.setWorkModel(dto.workModel());
        vacancy.setCompany(company); // associa à empresa logada

        Vacancy saved = vacancyRepository.save(vacancy);
        System.out.println("Role: " + company.getAuthorities());

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVacancy(@PathVariable UUID id, @RequestBody VacancyRequestDTO dto, Authentication authentication) {
        Company company = (Company) authentication.getPrincipal();
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);

        if (optionalVacancy.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vacancy vacancy = optionalVacancy.get();

        // Verifica se a vaga pertence à empresa logada
        if (!vacancy.getCompany().getId().equals(company.getId())) {
            return ResponseEntity.status(403).body("Você não tem permissão para editar esta vaga.");
        }

        vacancy.setName(dto.name());
        vacancy.setDescription(dto.description());
        vacancy.setRequirements(dto.requirements());
        vacancy.setBenefits(dto.benefits());
        vacancy.setSteps(dto.steps());
        vacancy.setOthers(dto.others());
        vacancy.setLink(dto.link());
        vacancy.setLanguage(dto.language());
        vacancy.setContractType(dto.contractType());
        vacancy.setWorkModel(dto.workModel());

        Vacancy updated = vacancyRepository.save(vacancy);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies(Authentication authentication) {
        Company company = (Company) authentication.getPrincipal();
        List<Vacancy> vacancies = vacancyRepository.findByCompany(company);
        return ResponseEntity.ok(vacancies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVacancyById(@PathVariable UUID id, Authentication authentication) {
        Company company = (Company) authentication.getPrincipal();
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);

        if (optionalVacancy.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vacancy vacancy = optionalVacancy.get();

        if (!vacancy.getCompany().getId().equals(company.getId())) {
            return ResponseEntity.status(403).body("Você não tem permissão para visualizar esta vaga.");
        }

        return ResponseEntity.ok(vacancy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVacancy(@PathVariable UUID id, Authentication authentication) {
        Company company = (Company) authentication.getPrincipal();
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);

        if (optionalVacancy.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vacancy vacancy = optionalVacancy.get();

        if (!vacancy.getCompany().getId().equals(company.getId())) {
            return ResponseEntity.status(403).body("Você não tem permissão para excluir esta vaga.");
        }

        vacancyRepository.delete(vacancy);
        return ResponseEntity.ok().build();
    }
}
