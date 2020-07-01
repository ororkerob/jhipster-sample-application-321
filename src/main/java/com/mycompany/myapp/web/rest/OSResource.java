package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.OS;
import com.mycompany.myapp.repository.OSRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.OS}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OSResource {

    private final Logger log = LoggerFactory.getLogger(OSResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplication321Os";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OSRepository oSRepository;

    public OSResource(OSRepository oSRepository) {
        this.oSRepository = oSRepository;
    }

    /**
     * {@code POST  /os} : Create a new oS.
     *
     * @param oS the oS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oS, or with status {@code 400 (Bad Request)} if the oS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/os")
    public ResponseEntity<OS> createOS(@RequestBody OS oS) throws URISyntaxException {
        log.debug("REST request to save OS : {}", oS);
        if (oS.getId() != null) {
            throw new BadRequestAlertException("A new oS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OS result = oSRepository.save(oS);
        return ResponseEntity.created(new URI("/api/os/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /os} : Updates an existing oS.
     *
     * @param oS the oS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oS,
     * or with status {@code 400 (Bad Request)} if the oS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/os")
    public ResponseEntity<OS> updateOS(@RequestBody OS oS) throws URISyntaxException {
        log.debug("REST request to update OS : {}", oS);
        if (oS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OS result = oSRepository.save(oS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, oS.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /os} : get all the oS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oS in body.
     */
    @GetMapping("/os")
    public List<OS> getAllOS() {
        log.debug("REST request to get all OS");
        return oSRepository.findAll();
    }

    /**
     * {@code GET  /os/:id} : get the "id" oS.
     *
     * @param id the id of the oS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/os/{id}")
    public ResponseEntity<OS> getOS(@PathVariable Long id) {
        log.debug("REST request to get OS : {}", id);
        Optional<OS> oS = oSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(oS);
    }

    /**
     * {@code DELETE  /os/:id} : delete the "id" oS.
     *
     * @param id the id of the oS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/os/{id}")
    public ResponseEntity<Void> deleteOS(@PathVariable Long id) {
        log.debug("REST request to delete OS : {}", id);
        oSRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
