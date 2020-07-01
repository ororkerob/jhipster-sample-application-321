package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Applet;
import com.mycompany.myapp.repository.AppletRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Applet}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AppletResource {

    private final Logger log = LoggerFactory.getLogger(AppletResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplication321Applet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppletRepository appletRepository;

    public AppletResource(AppletRepository appletRepository) {
        this.appletRepository = appletRepository;
    }

    /**
     * {@code POST  /applets} : Create a new applet.
     *
     * @param applet the applet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applet, or with status {@code 400 (Bad Request)} if the applet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applets")
    public ResponseEntity<Applet> createApplet(@RequestBody Applet applet) throws URISyntaxException {
        log.debug("REST request to save Applet : {}", applet);
        if (applet.getId() != null) {
            throw new BadRequestAlertException("A new applet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Applet result = appletRepository.save(applet);
        return ResponseEntity.created(new URI("/api/applets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applets} : Updates an existing applet.
     *
     * @param applet the applet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applet,
     * or with status {@code 400 (Bad Request)} if the applet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applets")
    public ResponseEntity<Applet> updateApplet(@RequestBody Applet applet) throws URISyntaxException {
        log.debug("REST request to update Applet : {}", applet);
        if (applet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Applet result = appletRepository.save(applet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /applets} : get all the applets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applets in body.
     */
    @GetMapping("/applets")
    public List<Applet> getAllApplets() {
        log.debug("REST request to get all Applets");
        return appletRepository.findAll();
    }

    /**
     * {@code GET  /applets/:id} : get the "id" applet.
     *
     * @param id the id of the applet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applets/{id}")
    public ResponseEntity<Applet> getApplet(@PathVariable Long id) {
        log.debug("REST request to get Applet : {}", id);
        Optional<Applet> applet = appletRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(applet);
    }

    /**
     * {@code DELETE  /applets/:id} : delete the "id" applet.
     *
     * @param id the id of the applet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applets/{id}")
    public ResponseEntity<Void> deleteApplet(@PathVariable Long id) {
        log.debug("REST request to delete Applet : {}", id);
        appletRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
