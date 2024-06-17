package com.freelance.lautarocutri.entity;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;

public class BooleanArrayConverter implements AttributeConverter<Boolean[], String> {
 @Override
 public String convertToDatabaseColumn(Boolean[] attribute) {
  if (attribute == null) {
   return null;
  }
  return Arrays.toString(attribute);
 }

 @Override
 public Boolean[] convertToEntityAttribute(String dbData) {
  if (dbData == null || dbData.isEmpty()) {
   return new Boolean[0];
  }
  String[] data = dbData.replace("[", "").replace("]", "").split(", ");
  Boolean[] result = new Boolean[data.length];
  for (int i = 0; i < data.length; i++) {
   result[i] = Boolean.valueOf(data[i]);
  }
  return result;
 }
}
