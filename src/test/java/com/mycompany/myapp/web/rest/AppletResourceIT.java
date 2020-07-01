package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplication321App;
import com.mycompany.myapp.domain.Applet;
import com.mycompany.myapp.repository.AppletRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppletResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication321App.class)
@AutoConfigureMockMvc
@WithMockUser
public class AppletResourceIT {

    private static final String DEFAULT_AID = "AAAAAAAAAA";
    private static final String UPDATED_AID = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_NAME = "BBBBBBBBBB";

    @Autowired
    private AppletRepository appletRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppletMockMvc;

    private Applet applet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applet createEntity(EntityManager em) {
        Applet applet = new Applet()
            .aid(DEFAULT_AID)
            .version(DEFAULT_VERSION)
            .packageName(DEFAULT_PACKAGE_NAME);
        return applet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applet createUpdatedEntity(EntityManager em) {
        Applet applet = new Applet()
            .aid(UPDATED_AID)
            .version(UPDATED_VERSION)
            .packageName(UPDATED_PACKAGE_NAME);
        return applet;
    }

    @BeforeEach
    public void initTest() {
        applet = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplet() throws Exception {
        int databaseSizeBeforeCreate = appletRepository.findAll().size();
        // Create the Applet
        restAppletMockMvc.perform(post("/api/applets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applet)))
            .andExpect(status().isCreated());

        // Validate the Applet in the database
        List<Applet> appletList = appletRepository.findAll();
        assertThat(appletList).hasSize(databaseSizeBeforeCreate + 1);
        Applet testApplet = appletList.get(appletList.size() - 1);
        assertThat(testApplet.getAid()).isEqualTo(DEFAULT_AID);
        assertThat(testApplet.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testApplet.getPackageName()).isEqualTo(DEFAULT_PACKAGE_NAME);
    }

    @Test
    @Transactional
    public void createAppletWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appletRepository.findAll().size();

        // Create the Applet with an existing ID
        applet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppletMockMvc.perform(post("/api/applets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applet)))
            .andExpect(status().isBadRequest());

        // Validate the Applet in the database
        List<Applet> appletList = appletRepository.findAll();
        assertThat(appletList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApplets() throws Exception {
        // Initialize the database
        appletRepository.saveAndFlush(applet);

        // Get all the appletList
        restAppletMockMvc.perform(get("/api/applets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applet.getId().intValue())))
            .andExpect(jsonPath("$.[*].aid").value(hasItem(DEFAULT_AID)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].packageName").value(hasItem(DEFAULT_PACKAGE_NAME)));
    }
    
    @Test
    @Transactional
    public void getApplet() throws Exception {
        // Initialize the database
        appletRepository.saveAndFlush(applet);

        // Get the applet
        restAppletMockMvc.perform(get("/api/applets/{id}", applet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applet.getId().intValue()))
            .andExpect(jsonPath("$.aid").value(DEFAULT_AID))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.packageName").value(DEFAULT_PACKAGE_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingApplet() throws Exception {
        // Get the applet
        restAppletMockMvc.perform(get("/api/applets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplet() throws Exception {
        // Initialize the database
        appletRepository.saveAndFlush(applet);

        int databaseSizeBeforeUpdate = appletRepository.findAll().size();

        // Update the applet
        Applet updatedApplet = appletRepository.findById(applet.getId()).get();
        // Disconnect from session so that the updates on updatedApplet are not directly saved in db
        em.detach(updatedApplet);
        updatedApplet
            .aid(UPDATED_AID)
            .version(UPDATED_VERSION)
            .packageName(UPDATED_PACKAGE_NAME);

        restAppletMockMvc.perform(put("/api/applets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplet)))
            .andExpect(status().isOk());

        // Validate the Applet in the database
        List<Applet> appletList = appletRepository.findAll();
        assertThat(appletList).hasSize(databaseSizeBeforeUpdate);
        Applet testApplet = appletList.get(appletList.size() - 1);
        assertThat(testApplet.getAid()).isEqualTo(UPDATED_AID);
        assertThat(testApplet.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testApplet.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingApplet() throws Exception {
        int databaseSizeBeforeUpdate = appletRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppletMockMvc.perform(put("/api/applets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applet)))
            .andExpect(status().isBadRequest());

        // Validate the Applet in the database
        List<Applet> appletList = appletRepository.findAll();
        assertThat(appletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplet() throws Exception {
        // Initialize the database
        appletRepository.saveAndFlush(applet);

        int databaseSizeBeforeDelete = appletRepository.findAll().size();

        // Delete the applet
        restAppletMockMvc.perform(delete("/api/applets/{id}", applet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Applet> appletList = appletRepository.findAll();
        assertThat(appletList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
