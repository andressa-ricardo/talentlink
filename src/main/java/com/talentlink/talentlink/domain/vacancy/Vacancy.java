package com.talentlink.talentlink.domain.vacancy;

import com.talentlink.talentlink.domain.company.Company;
import com.talentlink.talentlink.domain.enums.WorkModel;
import com.talentlink.talentlink.domain.enums.ContractType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "vacancies")
@Table(name = "vacancies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    @Column(columnDefinition = "TEXT")
    private String steps;

    @Column(columnDefinition = "TEXT")
    private String others;

    @Column(unique = true)
    private String link;

    private String language;

    @Enumerated(EnumType.STRING)
    private WorkModel workModel;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    private LocalDate postingDate;

    private LocalDate expirationDate;

    private boolean active;
}
