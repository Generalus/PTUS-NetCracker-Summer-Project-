package org.aksoftware.edu.udm;


import java.math.BigInteger;
import java.util.Map;

public interface UdAttributeType extends UdEntity {

    String getName();

    void setName(String name);

    //[Абакумов] по-хорошему надо и для объектных типов сделать свои абривеатуры
    String getAbbreviation();

    void setAbbreviation(String abbr);

    //[Абакумов] Cинтаксис таков колонка ATTR_PARAMS: "aaa=bbb;eeet=qqq;ggggg=rrr;"
    Map<String, String> getAttributeTypeParameters();

    void setAttributeTypeParameters(Map<String, String> parameters);

    //Имя класса, который будет работать с этим атрибутом
    String getHandler();

    void setHandler(String handler);
}
