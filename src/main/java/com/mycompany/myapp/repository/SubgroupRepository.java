package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Subgroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Subgroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubgroupRepository extends JpaRepository<Subgroup, Long> {
}
