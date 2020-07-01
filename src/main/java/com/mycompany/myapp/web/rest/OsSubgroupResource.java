package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.OsSubgroup;
import com.mycompany.myapp.repository.OsSubgroupRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.OsSubgroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OsSubgroupResource {

    private final Logger log = LoggerFactory.getLogger(OsSubgroupResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplication321OsSubgroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OsSubgroupRepository osSubgroupRepository;

    public OsSubgroupResource(OsSubgroupRepository osSubgroupRepository) {
        this.osSubgroupRepository = osSubgroupRepository;
    }

    /**
     * {@code POST  /os-subgroups} : Create a new osSubgroup.
     *
     * @param osSubgroup the osSubgroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new osSubgroup, or with status {@code 400 (Bad Request)} if the osSubgroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/os-subgroups")
    public ResponseEntity<OsSubgroup> createOsSubgroup(@RequestBody OsSubgroup osSubgroup) throws URISyntaxException {
        log.debug("REST request to save OsSubgroup : {}", osSubgroup);
        if (osSubgroup.getId() != null) {
            throw new BadRequestAlertException("A new osSubgroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OsSubgroup result = osSubgroupRepository.save(osSubgroup);
        return ResponseEntity.created(new URI("/api/os-subgroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /os-subgroups} : Updates an existing osSubgroup.
     *
     * @param osSubgroup the osSubgroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated osSubgroup,
     * or with status {@code 400 (Bad Request)} if the osSubgroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the osSubgroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/os-subgroups")
    public ResponseEntity<OsSubgroup> updateOsSubgroup(@RequestBody OsSubgroup osSubgroup) throws URISyntaxException {
        log.debug("REST request to update OsSubgroup : {}", osSubgroup);
        if (osSubgroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OsSubgroup result = osSubgroupRepository.save(osSubgroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, osSubgroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /os-subgroups} : get all the osSubgroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of osSubgroups in body.
     */
    @GetMapping("/os-subgroups")
    public List<OsSubgroup> getAllOsSubgroups() {
        log.debug("REST request to get all OsSubgroups");
        return osSubgroupRepository.findAll();
    }

    /**
     * {@code GET  /os-subgroups/:id} : get the "id" osSubgroup.
     *
     * @param id the id of the osSubgroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the osSubgroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/os-subgroups/{id}")
    public ResponseEntity<OsSubgroup> getOsSubgroup(@PathVariable Long id) {
        log.debug("REST request to get OsSubgroup : {}", id);
        Optional<OsSubgroup> osSubgroup = osSubgroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(osSubgroup);
    }

    /**
     * {@code DELETE  /os-subgroups/:id} : delete the "id" osSubgroup.
     *
     * @param id the id of the osSubgroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/os-subgroups/{id}")
    public ResponseEntity<Void> deleteOsSubgroup(@PathVariable Long id) {
        log.debug("REST request to delete OsSubgroup : {}", id);
        osSubgroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
