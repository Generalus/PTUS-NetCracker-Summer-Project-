package org.aksoftware.edu.udm;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UdObject extends UdEntity {

    String getName();

    void setName(String name);
    //Map<String, Parameter> getParameters();

    //void setParameters(Map<String, Parameter> parameters);

    UdObject getParent();

    void setParent(UdObject parent);

    BigInteger getParentId();

    //void setParentId(BigInteger id);

    UdObjectType getObjectType();

    void setObjectType(UdObjectType type);

    BigInteger getObjectTypeId();

    //void setObjectTypeId(BigInteger id);

    void setParameters(Set<Parameter> parameters);

    Parameter getParameter(BigInteger id);

    Set<? extends Parameter> getParameters();

    void addParameter(Parameter parameter);
}
