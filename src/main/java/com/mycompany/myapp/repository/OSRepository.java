package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OS;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OSRepository extends JpaRepository<OS, Long> {
}
