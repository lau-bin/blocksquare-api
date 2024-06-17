package com.freelance.lautarocutri.entity;

import io.quarkus.arc.All;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem extends PanacheEntity {

 @Basic(optional = false)
 @Column(length = 64)
 @Convert(converter = BooleanArrayConverter.class)
 private Boolean[] blocks;
 @Basic(optional = false)
 private Integer width;
}
