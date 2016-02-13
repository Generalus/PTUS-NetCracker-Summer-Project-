package org.aksoftware.edu.udm;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface UdAttribute extends UdEntity {

    String getName();

    void setName(String name);

    String getAbbreviation();

    void setAbbreviation(String abbr);

    UdAttributeType getAttributeType();

    void setAttributeType(UdAttributeType type);

    BigInteger getAttributeTypeId();

    //void setAttributeTypeId(BigInteger id);

    boolean isSystem();

    void setSystem(boolean isSystem);

    void setParameters(Set<Parameter> parameters);

    Parameter getParameter(BigInteger id);

    Set<? extends Parameter> getParameters();

    void addParameter(Parameter parameter);

    //[Абакумов] Пока без параметров атрибута и тегов

    //TODO Добавить сеттеры

}
