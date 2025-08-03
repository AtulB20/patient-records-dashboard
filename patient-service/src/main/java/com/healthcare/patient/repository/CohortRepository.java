package com.healthcare.patient.repository;

import com.healthcare.patient.model.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CohortRepository extends JpaRepository<Cohort, Long>, JpaSpecificationExecutor<Cohort> {
  List<Cohort> findByNameContainingIgnoreCase(String name);

  List<Cohort> findByCreatedBy(Long createdBy);

  Optional<Cohort> findByName(String name);

  boolean existsByName(String name);
}
