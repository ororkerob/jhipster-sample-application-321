package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplication321App;
import com.mycompany.myapp.domain.Subgroup;
import com.mycompany.myapp.repository.SubgroupRepository;

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
 * Integration tests for the {@link SubgroupResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication321App.class)
@AutoConfigureMockMvc
@WithMockUser
public class SubgroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SubgroupRepository subgroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubgroupMockMvc;

    private Subgroup subgroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subgroup createEntity(EntityManager em) {
        Subgroup subgroup = new Subgroup()
            .name(DEFAULT_NAME);
        return subgroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subgroup createUpdatedEntity(EntityManager em) {
        Subgroup subgroup = new Subgroup()
            .name(UPDATED_NAME);
        return subgroup;
    }

    @BeforeEach
    public void initTest() {
        subgroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubgroup() throws Exception {
        int databaseSizeBeforeCreate = subgroupRepository.findAll().size();
        // Create the Subgroup
        restSubgroupMockMvc.perform(post("/api/subgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subgroup)))
            .andExpect(status().isCreated());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeCreate + 1);
        Subgroup testSubgroup = subgroupList.get(subgroupList.size() - 1);
        assertThat(testSubgroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSubgroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subgroupRepository.findAll().size();

        // Create the Subgroup with an existing ID
        subgroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubgroupMockMvc.perform(post("/api/subgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subgroup)))
            .andExpect(status().isBadRequest());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubgroups() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        // Get all the subgroupList
        restSubgroupMockMvc.perform(get("/api/subgroups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subgroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSubgroup() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        // Get the subgroup
        restSubgroupMockMvc.perform(get("/api/subgroups/{id}", subgroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subgroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingSubgroup() throws Exception {
        // Get the subgroup
        restSubgroupMockMvc.perform(get("/api/subgroups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubgroup() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();

        // Update the subgroup
        Subgroup updatedSubgroup = subgroupRepository.findById(subgroup.getId()).get();
        // Disconnect from session so that the updates on updatedSubgroup are not directly saved in db
        em.detach(updatedSubgroup);
        updatedSubgroup
            .name(UPDATED_NAME);

        restSubgroupMockMvc.perform(put("/api/subgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubgroup)))
            .andExpect(status().isOk());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
        Subgroup testSubgroup = subgroupList.get(subgroupList.size() - 1);
        assertThat(testSubgroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubgroup() throws Exception {
        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubgroupMockMvc.perform(put("/api/subgroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subgroup)))
            .andExpect(status().isBadRequest());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubgroup() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        int databaseSizeBeforeDelete = subgroupRepository.findAll().size();

        // Delete the subgroup
        restSubgroupMockMvc.perform(delete("/api/subgroups/{id}", subgroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
