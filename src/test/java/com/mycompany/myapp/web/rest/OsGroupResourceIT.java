package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplication321App;
import com.mycompany.myapp.domain.OsGroup;
import com.mycompany.myapp.repository.OsGroupRepository;

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
 * Integration tests for the {@link OsGroupResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication321App.class)
@AutoConfigureMockMvc
@WithMockUser
public class OsGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private OsGroupRepository osGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOsGroupMockMvc;

    private OsGroup osGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OsGroup createEntity(EntityManager em) {
        OsGroup osGroup = new OsGroup()
            .name(DEFAULT_NAME);
        return osGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OsGroup createUpdatedEntity(EntityManager em) {
        OsGroup osGroup = new OsGroup()
            .name(UPDATED_NAME);
        return osGroup;
    }

    @BeforeEach
    public void initTest() {
        osGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createOsGroup() throws Exception {
        int databaseSizeBeforeCreate = osGroupRepository.findAll().size();
        // Create the OsGroup
        restOsGroupMockMvc.perform(post("/api/os-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(osGroup)))
            .andExpect(status().isCreated());

        // Validate the OsGroup in the database
        List<OsGroup> osGroupList = osGroupRepository.findAll();
        assertThat(osGroupList).hasSize(databaseSizeBeforeCreate + 1);
        OsGroup testOsGroup = osGroupList.get(osGroupList.size() - 1);
        assertThat(testOsGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOsGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = osGroupRepository.findAll().size();

        // Create the OsGroup with an existing ID
        osGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOsGroupMockMvc.perform(post("/api/os-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(osGroup)))
            .andExpect(status().isBadRequest());

        // Validate the OsGroup in the database
        List<OsGroup> osGroupList = osGroupRepository.findAll();
        assertThat(osGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOsGroups() throws Exception {
        // Initialize the database
        osGroupRepository.saveAndFlush(osGroup);

        // Get all the osGroupList
        restOsGroupMockMvc.perform(get("/api/os-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(osGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getOsGroup() throws Exception {
        // Initialize the database
        osGroupRepository.saveAndFlush(osGroup);

        // Get the osGroup
        restOsGroupMockMvc.perform(get("/api/os-groups/{id}", osGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(osGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingOsGroup() throws Exception {
        // Get the osGroup
        restOsGroupMockMvc.perform(get("/api/os-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOsGroup() throws Exception {
        // Initialize the database
        osGroupRepository.saveAndFlush(osGroup);

        int databaseSizeBeforeUpdate = osGroupRepository.findAll().size();

        // Update the osGroup
        OsGroup updatedOsGroup = osGroupRepository.findById(osGroup.getId()).get();
        // Disconnect from session so that the updates on updatedOsGroup are not directly saved in db
        em.detach(updatedOsGroup);
        updatedOsGroup
            .name(UPDATED_NAME);

        restOsGroupMockMvc.perform(put("/api/os-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOsGroup)))
            .andExpect(status().isOk());

        // Validate the OsGroup in the database
        List<OsGroup> osGroupList = osGroupRepository.findAll();
        assertThat(osGroupList).hasSize(databaseSizeBeforeUpdate);
        OsGroup testOsGroup = osGroupList.get(osGroupList.size() - 1);
        assertThat(testOsGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOsGroup() throws Exception {
        int databaseSizeBeforeUpdate = osGroupRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOsGroupMockMvc.perform(put("/api/os-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(osGroup)))
            .andExpect(status().isBadRequest());

        // Validate the OsGroup in the database
        List<OsGroup> osGroupList = osGroupRepository.findAll();
        assertThat(osGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOsGroup() throws Exception {
        // Initialize the database
        osGroupRepository.saveAndFlush(osGroup);

        int databaseSizeBeforeDelete = osGroupRepository.findAll().size();

        // Delete the osGroup
        restOsGroupMockMvc.perform(delete("/api/os-groups/{id}", osGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OsGroup> osGroupList = osGroupRepository.findAll();
        assertThat(osGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
