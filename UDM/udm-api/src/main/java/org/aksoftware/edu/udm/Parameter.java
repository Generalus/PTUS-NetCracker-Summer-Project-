package org.aksoftware.edu.udm;


import java.math.BigInteger;

public interface Parameter extends UdEntity {

    UdAttribute getAttribute();

    void setAttribute(UdAttribute attr);

    BigInteger getAttributeId();

    //void setAttributeId(BigInteger id);

    UdAttributeType getAttributeType();

    BigInteger getAttributeTypeId();

    UdObject getObject();

    void setObject(UdObject object);

    //[Абакумов] Надо подумать, как связать это с AttributeTypeHandler
    void setValue(Object value);

    Object getValue();

    //[Абакумов] Как вариант, но пока обойдёмся бех хэндлеров
    AttributeTypeHandler getHandler();

}
