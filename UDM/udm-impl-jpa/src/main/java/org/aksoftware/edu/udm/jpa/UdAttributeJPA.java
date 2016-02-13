package org.aksoftware.edu.udm.jpa;

import org.aksoftware.edu.udm.Parameter;
import org.aksoftware.edu.udm.UdAttribute;
import org.aksoftware.edu.udm.UdAttributeType;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "UD_ATTRIBUTE")
@NamedQueries({
        @NamedQuery(name = "attr_by_abbr", query = "SELECT attr FROM UdAttributeJPA attr where attr.abbr = ?1")
})
public class UdAttributeJPA implements UdAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTR_ID")
    private BigInteger id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ABBRIVIATION")
    private String abbr;

    @ManyToOne
    @JoinColumn(name="ATTR_TYPE_ID", nullable=false)
    private UdAttributeTypeJPA type;

    @Column(name = "IS_SYSTEM")
    private boolean system;

    @OneToMany(mappedBy = "attribute", fetch = FetchType.EAGER)
    private Set<ParameterJPA> parameters;


    public UdAttributeJPA(String name, String abbr, UdAttributeTypeJPA type, boolean system, Set<ParameterJPA> parameters) {
        this.name = name;
        this.abbr = abbr;
        this.type = type;
        this.system = system;
        this.parameters = parameters;
    }

    public UdAttributeJPA() {
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

    public UdAttributeType getAttributeType() {
        return type;
    }

    public void setAttributeType(UdAttributeType type) {
        this.type = (UdAttributeTypeJPA)type;
    }

    public BigInteger getAttributeTypeId() {
        return type.getId();
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = new HashSet<>();
        for (Parameter p: parameters)
            this.parameters.add((ParameterJPA)p);
    }

    public Parameter getParameter(BigInteger id) {
        for(Parameter par : parameters)
            if(par.getId().equals(id))
                return par;
        return null;
    }

    public void addParameter(Parameter parameter) {
        parameters.add((ParameterJPA)parameter);
    }

    public Set<? extends Parameter> getParameters() {
        return parameters;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean isSystem) {
        system = isSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UdAttributeJPA that = (UdAttributeJPA) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
