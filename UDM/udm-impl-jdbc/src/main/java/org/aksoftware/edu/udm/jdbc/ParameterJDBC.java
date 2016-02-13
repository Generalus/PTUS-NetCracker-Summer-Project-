package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.*;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ParameterJDBC implements Parameter, Storable {

    private boolean isLoaded;
    private BigInteger id;
    private BigInteger attrId, objectId;
    private UdAttribute attribute;
    private static final String LOAD = "select * from ABSTRACT_PROPERTY where PROPERTY_ID = ?";
    //private static final String INSERT = "insert into udm.ABSTRACT_PROPERTY values PROPERTY_ID = ?";
    private UdObject object;

    public ParameterJDBC(BigInteger id) {
        this.id = id;
        isLoaded = false;
    }

    public ParameterJDBC() {
        isLoaded = true;
    }

    private void update() {
        if(isLoaded)
            return;
        try {
            PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(LOAD);
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                attrId = rs.getBigDecimal("ATTR_ID").toBigInteger();
                objectId = rs.getBigDecimal("OBJECT_ID").toBigInteger();
            }
            if(attrId != null)
                attribute = new UdAttributeJDBC(attrId);
            if(objectId != null)
                object = new UdObjectJDBC(objectId);
            rs.close();
            isLoaded = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        //TODO:
    }

    public BigInteger getId() {
        return id;
    }

    public UdAttribute getAttribute() {
        update();
        return attribute;
    }

    public BigInteger getAttributeId() {
        update();
        return attribute.getId();
    }

    public UdAttributeType getAttributeType() {
        update();
        return attribute.getAttributeType();
    }

    public BigInteger getAttributeTypeId() {
        update();
        return attribute.getAttributeTypeId();
    }

    public void setValue(Object value) {
        getHandler().setValue(value);
    }

    public Object getValue() {
        update();
        return getHandler().getValue();
    }

    public AttributeTypeHandler getHandler() {
        update();
        return null;//TODO:
    }


    @Override
    public void setAttribute(UdAttribute attr) {
        this.attribute = attr;
    }

    @Override
    public UdObject getObject() {
        update();
        return object;
    }

    @Override
    public void setObject(UdObject object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParameterJDBC that = (ParameterJDBC) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
