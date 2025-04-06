package com.talentlink.talentlink.dtos.vacancy;

import com.talentlink.talentlink.domain.enums.ContractType;
import com.talentlink.talentlink.domain.enums.WorkModel;

import java.util.UUID;

public record VacancyRequestDTO(
        String name,
        UUID companyId,
        String description,
        String requirements,
        String benefits,
        String steps,
        String others,
        String link,
        String language,
        ContractType contractType,
        WorkModel workModel
) {}
