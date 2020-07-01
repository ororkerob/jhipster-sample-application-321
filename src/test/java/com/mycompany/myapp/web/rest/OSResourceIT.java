package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplication321App;
import com.mycompany.myapp.domain.OS;
import com.mycompany.myapp.repository.OSRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OSResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication321App.class)
@AutoConfigureMockMvc
@WithMockUser
public class OSResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OSRepository oSRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOSMockMvc;

    private OS oS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OS createEntity(EntityManager em) {
        OS oS = new OS()
            .name(DEFAULT_NAME)
            .version(DEFAULT_VERSION)
            .endDate(DEFAULT_END_DATE);
        return oS;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OS createUpdatedEntity(EntityManager em) {
        OS oS = new OS()
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .endDate(UPDATED_END_DATE);
        return oS;
    }

    @BeforeEach
    public void initTest() {
        oS = createEntity(em);
    }

    @Test
    @Transactional
    public void createOS() throws Exception {
        int databaseSizeBeforeCreate = oSRepository.findAll().size();
        // Create the OS
        restOSMockMvc.perform(post("/api/os")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oS)))
            .andExpect(status().isCreated());

        // Validate the OS in the database
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeCreate + 1);
        OS testOS = oSList.get(oSList.size() - 1);
        assertThat(testOS.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOS.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testOS.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createOSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oSRepository.findAll().size();

        // Create the OS with an existing ID
        oS.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOSMockMvc.perform(post("/api/os")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oS)))
            .andExpect(status().isBadRequest());

        // Validate the OS in the database
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOS() throws Exception {
        // Initialize the database
        oSRepository.saveAndFlush(oS);

        // Get all the oSList
        restOSMockMvc.perform(get("/api/os?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oS.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOS() throws Exception {
        // Initialize the database
        oSRepository.saveAndFlush(oS);

        // Get the oS
        restOSMockMvc.perform(get("/api/os/{id}", oS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oS.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingOS() throws Exception {
        // Get the oS
        restOSMockMvc.perform(get("/api/os/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOS() throws Exception {
        // Initialize the database
        oSRepository.saveAndFlush(oS);

        int databaseSizeBeforeUpdate = oSRepository.findAll().size();

        // Update the oS
        OS updatedOS = oSRepository.findById(oS.getId()).get();
        // Disconnect from session so that the updates on updatedOS are not directly saved in db
        em.detach(updatedOS);
        updatedOS
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .endDate(UPDATED_END_DATE);

        restOSMockMvc.perform(put("/api/os")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOS)))
            .andExpect(status().isOk());

        // Validate the OS in the database
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeUpdate);
        OS testOS = oSList.get(oSList.size() - 1);
        assertThat(testOS.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOS.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testOS.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOS() throws Exception {
        int databaseSizeBeforeUpdate = oSRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOSMockMvc.perform(put("/api/os")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oS)))
            .andExpect(status().isBadRequest());

        // Validate the OS in the database
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOS() throws Exception {
        // Initialize the database
        oSRepository.saveAndFlush(oS);

        int databaseSizeBeforeDelete = oSRepository.findAll().size();

        // Delete the oS
        restOSMockMvc.perform(delete("/api/os/{id}", oS.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OS> oSList = oSRepository.findAll();
        assertThat(oSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
