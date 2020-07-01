package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Applet.
 */
@Entity
@Table(name = "applet")
public class Applet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "aid")
    private String aid;

    @Column(name = "version")
    private String version;

    @Column(name = "package_name")
    private String packageName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public Applet aid(String aid) {
        this.aid = aid;
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getVersion() {
        return version;
    }

    public Applet version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackageName() {
        return packageName;
    }

    public Applet packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Applet)) {
            return false;
        }
        return id != null && id.equals(((Applet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Applet{" +
            "id=" + getId() +
            ", aid='" + getAid() + "'" +
            ", version='" + getVersion() + "'" +
            ", packageName='" + getPackageName() + "'" +
            "}";
    }
}
