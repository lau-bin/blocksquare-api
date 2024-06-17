package com.freelance.lautarocutri.entity;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class IntegerArrayConverter implements AttributeConverter<int[], String> {
 @Override
 public String convertToDatabaseColumn(int[] attribute) {
  if (attribute == null) {
   return null;
  }
  return Arrays.stream(attribute)
    .mapToObj(String::valueOf)
    .collect(Collectors.joining(","));
 }

 @Override
 public int[] convertToEntityAttribute(String dbData) {
  if (dbData == null || dbData.isEmpty()) {
   return new int[0];
  }
  return Arrays.stream(dbData.split(","))
    .mapToInt(Integer::parseInt)
    .toArray();
 }
}
