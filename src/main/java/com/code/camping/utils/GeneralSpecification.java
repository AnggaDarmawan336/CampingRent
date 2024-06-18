package com.code.camping.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class GeneralSpecification<T, D> {
    public static <T, D> Specification<T> getSpecification(D searchDTO) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (var field : searchDTO.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(searchDTO);
                    if(value != null) {
                        if (value instanceof String) {
                            predicates.add(criteriaBuilder.like(root.get(field.getName()), "%" + value + "%"));
                        } else {
                            predicates.add(criteriaBuilder.equal(root.get(field.getName()), value));
                        }
                    }
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
            Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
            return criteriaBuilder.and(predicatesArray);
        });
    }
}
