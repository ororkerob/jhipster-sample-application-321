package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Applet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Applet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppletRepository extends JpaRepository<Applet, Long> {
}
