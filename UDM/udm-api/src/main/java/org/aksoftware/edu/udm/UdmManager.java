package org.aksoftware.edu.udm;

import java.math.BigInteger;

public interface UdmManager {
    //[Абакумов] Никакого getAll. Он подвесит базу на длительное время
    //UdObject[] getAllUsers();

    void save(UdEntity... entities); // метод сохраняет сущность в базу данных

    void close();

    User getUser(String login);

    User getUser(BigInteger id);

    UdAttribute getAttribute(BigInteger id);

    UdAttribute getAttribute(String abbreviation);

    UdAttributeType getAttributeType(BigInteger id);

    UdAttributeType getAttributeType(String abbreviation);

    UdObjectType getObjectType(BigInteger id);

    UdObjectType getObjectType(String abbreviation);

    UdObject getObject(BigInteger id);



    //TODO Добавить операции ADD, SET и.т.д.

}
