package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OsGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OsGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OsGroupRepository extends JpaRepository<OsGroup, Long> {
}
