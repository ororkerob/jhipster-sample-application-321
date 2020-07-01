package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplication321App;
import com.mycompany.myapp.domain.OsSubgroup;
import com.mycompany.myapp.repository.OsSubgroupRepository;

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
 * Integration tests for the {@link OsSubgroupResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication321App.class)
@AutoConfigureMockMvc
@WithMockUser
public class OsSubgroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private OsSubgroupRepository osSubgroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOsSubgroupMockMvc;

    private OsSubgroup osSubgroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OsSubgroup createEntity(EntityManager em) {
        OsSubgroup osSubgroup = new OsSubgroup()
            .name(DEFAULT_NAME);
        return osSubgroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OsSubgroup createUpdatedEntity(EntityManager em) {
        OsSubgroup osSubgroup = new OsSubgroup()
            .name(UPDATED_NAME);
        return osSubgroup;
    }

    @BeforeEach
    public void initTest() {
        osSubgroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createOsSubgroup() throws Exception {
        int databaseSizeBeforeCreate = osSubgroupRepository.findAll().size();
        // Create the OsSubgroup
        restOsSubgroupMockMvc.perform(post("/api/os-subgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(osSubgroup)))
            .andExpect(status().isCreated());

        // Validate the OsSubgroup in the database
        List<OsSubgroup> osSubgroupList = osSubgroupRepository.findAll();
        assertThat(osSubgroupList).hasSize(databaseSizeBeforeCreate + 1);
        OsSubgroup testOsSubgroup = osSubgroupList.get(osSubgroupList.size() - 1);
        assertThat(testOsSubgroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOsSubgroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = osSubgroupRepository.findAll().size();

        // Create the OsSubgroup with an existing ID
        osSubgroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOsSubgroupMockMvc.perform(post("/api/os-subgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(osSubgroup)))
            .andExpect(status().isBadRequest());

        // Validate the OsSubgroup in the database
        List<OsSubgroup> osSubgroupList = osSubgroupRepository.findAll();
        assertThat(osSubgroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOsSubgroups() throws Exception {
        // Initialize the database
        osSubgroupRepository.saveAndFlush(osSubgroup);

        // Get all the osSubgroupList
        restOsSubgroupMockMvc.perform(get("/api/os-subgroups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(osSubgroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getOsSubgroup() throws Exception {
        // Initialize the database
        osSubgroupRepository.saveAndFlush(osSubgroup);

        // Get the osSubgroup
        restOsSubgroupMockMvc.perform(get("/api/os-subgroups/{id}", osSubgroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(osSubgroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingOsSubgroup() throws Exception {
        // Get the osSubgroup
        restOsSubgroupMockMvc.perform(get("/api/os-subgroups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOsSubgroup() throws Exception {
        // Initialize the database
        osSubgroupRepository.saveAndFlush(osSubgroup);

        int databaseSizeBeforeUpdate = osSubgroupRepository.findAll().size();

        // Update the osSubgroup
        OsSubgroup updatedOsSubgroup = osSubgroupRepository.findById(osSubgroup.getId()).get();
        // Disconnect from session so that the updates on updatedOsSubgroup are not directly saved in db
        em.detach(updatedOsSubgroup);
        updatedOsSubgroup
            .name(UPDATED_NAME);

        restOsSubgroupMockMvc.perform(put("/api/os-subgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOsSubgroup)))
            .andExpect(status().isOk());

        // Validate the OsSubgroup in the database
        List<OsSubgroup> osSubgroupList = osSubgroupRepository.findAll();
        assertThat(osSubgroupList).hasSize(databaseSizeBeforeUpdate);
        OsSubgroup testOsSubgroup = osSubgroupList.get(osSubgroupList.size() - 1);
        assertThat(testOsSubgroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOsSubgroup() throws Exception {
        int databaseSizeBeforeUpdate = osSubgroupRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOsSubgroupMockMvc.perform(put("/api/os-subgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(osSubgroup)))
            .andExpect(status().isBadRequest());

        // Validate the OsSubgroup in the database
        List<OsSubgroup> osSubgroupList = osSubgroupRepository.findAll();
        assertThat(osSubgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOsSubgroup() throws Exception {
        // Initialize the database
        osSubgroupRepository.saveAndFlush(osSubgroup);

        int databaseSizeBeforeDelete = osSubgroupRepository.findAll().size();

        // Delete the osSubgroup
        restOsSubgroupMockMvc.perform(delete("/api/os-subgroups/{id}", osSubgroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OsSubgroup> osSubgroupList = osSubgroupRepository.findAll();
        assertThat(osSubgroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
