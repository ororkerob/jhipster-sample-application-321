package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A OsGroup.
 */
@Entity
@Table(name = "os_group")
public class OsGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "osGroups", allowSetters = true)
    private OS oS;

    @OneToMany(mappedBy = "osGroup")
    private Set<Attribute> attributes = new HashSet<>();

    @OneToMany(mappedBy = "osGroup")
    private Set<OsSubgroup> osSubgroups = new HashSet<>();

    @OneToMany(mappedBy = "osGroup")
    private Set<Applet> applets = new HashSet<>();

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

    public OsGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OS getOS() {
        return oS;
    }

    public OsGroup oS(OS oS) {
        this.oS = oS;
        return this;
    }

    public void setOS(OS oS) {
        this.oS = oS;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public OsGroup attributes(Set<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public OsGroup addAttributes(Attribute attribute) {
        this.attributes.add(attribute);
        attribute.setOsGroup(this);
        return this;
    }

    public OsGroup removeAttributes(Attribute attribute) {
        this.attributes.remove(attribute);
        attribute.setOsGroup(null);
        return this;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Set<OsSubgroup> getOsSubgroups() {
        return osSubgroups;
    }

    public OsGroup osSubgroups(Set<OsSubgroup> osSubgroups) {
        this.osSubgroups = osSubgroups;
        return this;
    }

    public OsGroup addOsSubgroups(OsSubgroup osSubgroup) {
        this.osSubgroups.add(osSubgroup);
        osSubgroup.setOsGroup(this);
        return this;
    }

    public OsGroup removeOsSubgroups(OsSubgroup osSubgroup) {
        this.osSubgroups.remove(osSubgroup);
        osSubgroup.setOsGroup(null);
        return this;
    }

    public void setOsSubgroups(Set<OsSubgroup> osSubgroups) {
        this.osSubgroups = osSubgroups;
    }

    public Set<Applet> getApplets() {
        return applets;
    }

    public OsGroup applets(Set<Applet> applets) {
        this.applets = applets;
        return this;
    }

    public OsGroup addApplets(Applet applet) {
        this.applets.add(applet);
        applet.setOsGroup(this);
        return this;
    }

    public OsGroup removeApplets(Applet applet) {
        this.applets.remove(applet);
        applet.setOsGroup(null);
        return this;
    }

    public void setApplets(Set<Applet> applets) {
        this.applets = applets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OsGroup)) {
            return false;
        }
        return id != null && id.equals(((OsGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OsGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
