package com.freelance.lautarocutri.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionStep extends PanacheEntity {
 @Basic(optional = false)
 @Column(length = 255)
 @Convert(converter = IntegerArrayConverter.class)
 private int[] steps;
}
