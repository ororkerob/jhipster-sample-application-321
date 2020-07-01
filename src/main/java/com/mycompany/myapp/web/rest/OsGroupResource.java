package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.OsGroup;
import com.mycompany.myapp.repository.OsGroupRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.OsGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OsGroupResource {

    private final Logger log = LoggerFactory.getLogger(OsGroupResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplication321OsGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OsGroupRepository osGroupRepository;

    public OsGroupResource(OsGroupRepository osGroupRepository) {
        this.osGroupRepository = osGroupRepository;
    }

    /**
     * {@code POST  /os-groups} : Create a new osGroup.
     *
     * @param osGroup the osGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new osGroup, or with status {@code 400 (Bad Request)} if the osGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/os-groups")
    public ResponseEntity<OsGroup> createOsGroup(@RequestBody OsGroup osGroup) throws URISyntaxException {
        log.debug("REST request to save OsGroup : {}", osGroup);
        if (osGroup.getId() != null) {
            throw new BadRequestAlertException("A new osGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OsGroup result = osGroupRepository.save(osGroup);
        return ResponseEntity.created(new URI("/api/os-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /os-groups} : Updates an existing osGroup.
     *
     * @param osGroup the osGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated osGroup,
     * or with status {@code 400 (Bad Request)} if the osGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the osGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/os-groups")
    public ResponseEntity<OsGroup> updateOsGroup(@RequestBody OsGroup osGroup) throws URISyntaxException {
        log.debug("REST request to update OsGroup : {}", osGroup);
        if (osGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OsGroup result = osGroupRepository.save(osGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, osGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /os-groups} : get all the osGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of osGroups in body.
     */
    @GetMapping("/os-groups")
    public List<OsGroup> getAllOsGroups() {
        log.debug("REST request to get all OsGroups");
        return osGroupRepository.findAll();
    }

    /**
     * {@code GET  /os-groups/:id} : get the "id" osGroup.
     *
     * @param id the id of the osGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the osGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/os-groups/{id}")
    public ResponseEntity<OsGroup> getOsGroup(@PathVariable Long id) {
        log.debug("REST request to get OsGroup : {}", id);
        Optional<OsGroup> osGroup = osGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(osGroup);
    }

    /**
     * {@code DELETE  /os-groups/:id} : delete the "id" osGroup.
     *
     * @param id the id of the osGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/os-groups/{id}")
    public ResponseEntity<Void> deleteOsGroup(@PathVariable Long id) {
        log.debug("REST request to delete OsGroup : {}", id);
        osGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
