package org.aksoftware.edu.udm.jpa;

import org.aksoftware.edu.udm.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "UD_OBJECT_TYPE")
@NamedQueries({
        @NamedQuery(name = "obj_type_by_abbr", query = "SELECT obj_type FROM UdObjectTypeJPA obj_type where obj_type.abbr = ?1")
})

public class UdObjectTypeJPA implements UdObjectType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OBJECT_TYPE_ID")
    private BigInteger id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ABBRIVIATION")
    private String abbr;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="PARENT_TYPE_ID", nullable=true)
    private UdObjectTypeJPA parent;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="UD_OBJECT_TYPE_ATTRIBUTES",
            joinColumns=@JoinColumn(name="OBJECT_TYPE_ID"),
            inverseJoinColumns=@JoinColumn(name="ATTR_ID"))
    private Set<UdAttributeJPA> attributes;


    public UdObjectTypeJPA(String name, String abbr, UdObjectTypeJPA parent, Set<UdAttributeJPA> attributes) {
        this.name = name;
        this.abbr = abbr;
        this.parent = parent;
        this.attributes = attributes;
    }

    public UdObjectTypeJPA() {
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbr;
    }

    public void setAbbreviation(String abbr) {
        this.abbr = abbr;
    }

    public Set<BigInteger> getAttributeIds() {
        Set<BigInteger> set = new HashSet<>();
        for(UdAttribute attr : attributes)
            set.add(attr.getId());
        return set;
    }

    public UdObjectType getParent() {
        return parent;
    }

    public void setParent(UdObjectType parent) {
        this.parent = (UdObjectTypeJPA)parent;
    }

    public Set<? extends UdAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<UdAttribute> attributes) {
        this.attributes = new HashSet<>();
        for (UdAttribute a: attributes)
            this.attributes.add((UdAttributeJPA)a);
    }

    public BigInteger getParentId() {
        if(parent == null)
            return null;
        return parent.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UdObjectTypeJPA that = (UdObjectTypeJPA) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
