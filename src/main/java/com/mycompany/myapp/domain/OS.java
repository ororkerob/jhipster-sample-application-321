package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A OS.
 */
@Entity
@Table(name = "os")
public class OS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private String version;

    @Column(name = "end_date")
    private Instant endDate;

    @OneToMany(mappedBy = "oS")
    private Set<OsGroup> osGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public OS name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public OS version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public OS endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Set<OsGroup> getOsGroups() {
        return osGroups;
    }

    public OS osGroups(Set<OsGroup> osGroups) {
        this.osGroups = osGroups;
        return this;
    }

    public OS addOsGroups(OsGroup osGroup) {
        this.osGroups.add(osGroup);
        osGroup.setOS(this);
        return this;
    }

    public OS removeOsGroups(OsGroup osGroup) {
        this.osGroups.remove(osGroup);
        osGroup.setOS(null);
        return this;
    }

    public void setOsGroups(Set<OsGroup> osGroups) {
        this.osGroups = osGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OS)) {
            return false;
        }
        return id != null && id.equals(((OS) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OS{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", version='" + getVersion() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
