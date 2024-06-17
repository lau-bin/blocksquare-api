package com.freelance.lautarocutri.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Solution extends PanacheEntity {
 @OneToOne
 private Problem problem;
 @OneToMany
 private SolutionStep[] solutionStep;
}
