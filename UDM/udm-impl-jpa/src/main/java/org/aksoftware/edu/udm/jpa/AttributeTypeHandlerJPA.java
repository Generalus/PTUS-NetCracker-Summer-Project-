package org.aksoftware.edu.udm.jpa;

import org.aksoftware.edu.udm.AttributeTypeHandler;

import javax.persistence.*;
import java.math.BigInteger;

//@Entity
//@Table(name = "ATTRIBUTE_TYPE_HANDLER")  //  TODO [Никита]: создать такую таблицу в БД, или придумать что-то еще
public class AttributeTypeHandlerJPA<T> implements AttributeTypeHandler<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HANDLER_ID")
    private BigInteger id;

    @Column(name = "VALUE")
    private T value;

    public AttributeTypeHandlerJPA(T value) {
        this.value = value;
    }

    public AttributeTypeHandlerJPA() {
    }

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeTypeHandlerJPA<?> that = (AttributeTypeHandlerJPA<?>) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
