package org.aksoftware.edu.udm;


import java.math.BigInteger;

public interface User extends UdEntity {

    String getLogin();

    void setLogin(String login);

    String getAbbr();

    void setAbbr(String abbr);

    String getPassword();

    void setPassword(String password);

    BigInteger getObjectID();

    void setObjectID(BigInteger objectID);

    Integer getUserCategory();

    void setUserCategory(Integer userCategory);
}
