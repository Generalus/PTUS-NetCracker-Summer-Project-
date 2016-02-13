package org.aksoftware.edu.udm;


import java.math.BigInteger;

/**
 * Пока обойдёмся без них, в будущем добавим. Сейчас делаем только простые типы String, Number, итд.
 * @param <T>
 */
public interface AttributeTypeHandler<T> extends UdEntity {

    BigInteger getId();

    T getValue();

    void setValue(T value);

}
