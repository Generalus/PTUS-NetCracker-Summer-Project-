package org.aksoftware.edu.udm.jpa;

import org.aksoftware.edu.udm.Parameter;
import org.aksoftware.edu.udm.UdObject;
import org.aksoftware.edu.udm.UdObjectType;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "UD_OBJECT")
public class UdObjectJPA implements UdObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OBJECT_ID")
    private BigInteger id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="PARENT_OBJECT_ID", nullable=true)
    private UdObjectJPA parent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="OBJECT_TYPE_ID", nullable=false)
    private UdObjectTypeJPA type;

    @OneToMany(mappedBy = "object", fetch = FetchType.EAGER)
    private Set<ParameterJPA> parameters;


    public UdObjectJPA(String name, UdObjectJPA parent, UdObjectTypeJPA type, Set<ParameterJPA> parameters) {
        this.name = name;
        this.parent = parent;
        this.type = type;
        this.parameters = parameters;
    }

    public UdObjectJPA() {
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

    public UdObject getParent() {
        return parent;
    }

    public void setParent(UdObject parent) {
        this.parent = (UdObjectJPA)parent;
    }

    public BigInteger getParentId() {
        if(parent == null)
            return null;
        return parent.getId();
    }

    public UdObjectType getObjectType() {
        return type;
    }

    public void setObjectType(UdObjectType type) {
        this.type = (UdObjectTypeJPA)type;
    }

    public BigInteger getObjectTypeId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UdObjectJPA that = (UdObjectJPA) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
