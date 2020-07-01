package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Subgroup;
import com.mycompany.myapp.repository.SubgroupRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Subgroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubgroupResource {

    private final Logger log = LoggerFactory.getLogger(SubgroupResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplication321Subgroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubgroupRepository subgroupRepository;

    public SubgroupResource(SubgroupRepository subgroupRepository) {
        this.subgroupRepository = subgroupRepository;
    }

    /**
     * {@code POST  /subgroups} : Create a new subgroup.
     *
     * @param subgroup the subgroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subgroup, or with status {@code 400 (Bad Request)} if the subgroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subgroups")
    public ResponseEntity<Subgroup> createSubgroup(@RequestBody Subgroup subgroup) throws URISyntaxException {
        log.debug("REST request to save Subgroup : {}", subgroup);
        if (subgroup.getId() != null) {
            throw new BadRequestAlertException("A new subgroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subgroup result = subgroupRepository.save(subgroup);
        return ResponseEntity.created(new URI("/api/subgroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subgroups} : Updates an existing subgroup.
     *
     * @param subgroup the subgroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subgroup,
     * or with status {@code 400 (Bad Request)} if the subgroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subgroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subgroups")
    public ResponseEntity<Subgroup> updateSubgroup(@RequestBody Subgroup subgroup) throws URISyntaxException {
        log.debug("REST request to update Subgroup : {}", subgroup);
        if (subgroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Subgroup result = subgroupRepository.save(subgroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subgroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subgroups} : get all the subgroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subgroups in body.
     */
    @GetMapping("/subgroups")
    public List<Subgroup> getAllSubgroups() {
        log.debug("REST request to get all Subgroups");
        return subgroupRepository.findAll();
    }

    /**
     * {@code GET  /subgroups/:id} : get the "id" subgroup.
     *
     * @param id the id of the subgroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subgroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subgroups/{id}")
    public ResponseEntity<Subgroup> getSubgroup(@PathVariable Long id) {
        log.debug("REST request to get Subgroup : {}", id);
        Optional<Subgroup> subgroup = subgroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subgroup);
    }

    /**
     * {@code DELETE  /subgroups/:id} : delete the "id" subgroup.
     *
     * @param id the id of the subgroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subgroups/{id}")
    public ResponseEntity<Void> deleteSubgroup(@PathVariable Long id) {
        log.debug("REST request to delete Subgroup : {}", id);
        subgroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
