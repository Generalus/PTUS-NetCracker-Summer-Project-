package org.aksoftware.edu.udm;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface UdObjectType extends UdEntity {

    String getName();

    void setName(String name);

    String getAbbreviation();

    void setAbbreviation(String abbr);

    Set<BigInteger> getAttributeIds();

    Set<? extends UdAttribute> getAttributes();

    UdObjectType getParent();

    void setParent(UdObjectType parent);
    
    BigInteger getParentId();

    //void setParentId(BigInteger id);

}
