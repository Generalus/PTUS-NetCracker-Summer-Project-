package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.*;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ParameterJDBC implements Parameter {

    private final Statement st;
    private final String sqlCommand;
    private BigInteger id;
    private UdAttribute attribute;
    private UdObject object;

    public ParameterJDBC(Statement st, String sqlCommand) throws SQLException {
        this.st = st;
        this.sqlCommand = sqlCommand;
        update();
    }

    public void update() throws SQLException {
        try(ResultSet rs = st.executeQuery(sqlCommand)) {
            BigInteger attrId = null;
            BigInteger objectId = null;
            while (rs.next()) {
                id = rs.getBigDecimal("PROPERTY_ID").toBigInteger();
                attrId = rs.getBigDecimal("ATTR_ID").toBigInteger();
                objectId = rs.getBigDecimal("OBJECT_ID").toBigInteger();
            }
            attribute = new UdAttributeJDBC(st, "select *" +
                    " from udm.UD_ATTRIBUTE where ATTR_ID = " + attrId);
            object = new UdObjectJDBC(st, "select *" +
                    " from udm.UD_OBJECT where OBJECT_ID = " + objectId);
        }
    }

    public BigInteger getId() {
        return id;
    }

    public UdAttribute getAttribute() {
        return attribute;
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
    public void setAttribute(UdAttribute attr) {
        this.attribute = attr;
    }

    @Override
    public UdObject getObject() {
        return null;
    }

    @Override
    public void setObject(UdObject object) {

    }
}
