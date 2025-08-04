package com.healthcare.patient.service;

import com.healthcare.patient.model.CohortFilter;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.model.PatientVisit;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public interface PatientSpecification {

  public default Specification<Patient> createSpecificationFromCriteria(CohortFilter criteria) {
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      if (criteria.getName() != null) {
        predicates.add(criteriaBuilder.like(root.get("first_name"), "%" + criteria.getName() + "%"));
      }

      if (criteria.getEmail() != null) {
        predicates.add(criteriaBuilder.like(root.get("email"), "%" + criteria.getEmail() + "%"));
      }

      if (criteria.getPhone() != null) {
        predicates.add(criteriaBuilder.like(root.get("phone_number"), "%" + criteria.getPhone() + "%"));
      }

      if (criteria.getDobFrom() != null) {
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), criteria.getDobFrom()));
      }

      if (criteria.getDobTo() != null) {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateOfBirth"), criteria.getDobTo()));
      }

      if (criteria.getGender() != null) {
        predicates.add(criteriaBuilder.equal(root.get("gender"), criteria.getGender()));
      }

      if (criteria.getStatuses() != null && !criteria.getStatuses().isEmpty()) {
        predicates.add(criteriaBuilder.in(root.get("status")).value(criteria.getStatuses()));
      }

      if (criteria.getHasPendingAppointments() != null) {
        Subquery<Long> pendingVisitsSubquery = query.subquery(Long.class);
        Root<PatientVisit> visitRoot = pendingVisitsSubquery.from(PatientVisit.class);
        pendingVisitsSubquery.select(visitRoot.get("patient").get("id"));
        pendingVisitsSubquery.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(visitRoot.get("patient").get("id"), root.get("id")),
                criteriaBuilder.equal(visitRoot.get("status"), PatientVisit.VisitStatus.PENDING)
            )
        );

        if (criteria.getHasPendingAppointments()) {
          predicates.add(criteriaBuilder.exists(pendingVisitsSubquery));
        } else {
          predicates.add(criteriaBuilder.not(criteriaBuilder.exists(pendingVisitsSubquery)));
        }
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };

  }

}
