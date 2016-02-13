package org.aksoftware.edu.udm.jpa;

import org.aksoftware.edu.udm.*;
import org.aksoftware.edu.udm.Parameter;

import javax.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "ABSTRACT_PROPERTY")
public class ParameterJPA implements Parameter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROPERTY_ID")
    private BigInteger id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ATTR_ID", nullable=false)
    private UdAttributeJPA attribute;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="OBJECT_ID", nullable=false)
    private UdObjectJPA object;


    public ParameterJPA(UdAttributeJPA attribute, UdObjectJPA object) {
        this.attribute = attribute;
        this.object = object;
    }

    public ParameterJPA() {
    }

    public BigInteger getId() {
        return id;
    }

    public UdObject getObject() {
        return object;
    }

    public void setObject(UdObject object) {
        this.object = (UdObjectJPA)object;
    }

    public UdAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(UdAttribute attr) {
        attribute = (UdAttributeJPA)attr;
    }

    public BigInteger getAttributeId() {
        return attribute.getId();
    }

    public UdAttributeType getAttributeType() {
        return attribute.getAttributeType();
    }

    public BigInteger getAttributeTypeId() {
        return attribute.getAttributeTypeId();
    }

    public void setValue(Object value) {
        getHandler().setValue(value);
    }

    public Object getValue() {
        return getHandler().getValue();
    }

    public AttributeTypeHandler getHandler() {
        return null;//TODO:
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParameterJPA that = (ParameterJPA) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
