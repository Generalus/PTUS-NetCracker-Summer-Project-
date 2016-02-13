package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.UdAttribute;
import org.aksoftware.edu.udm.UdObjectType;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UdObjectTypeJDBC implements UdObjectType {

    private final Statement st;
    private final String sqlCommand;
    private BigInteger id;
    private String name;
    private String abbreviation;
    private Set<UdAttribute> attributes;
    private BigInteger parentId;
    private Boolean isSystem;

    public UdObjectTypeJDBC(Statement st, String sqlCommand) throws SQLException {
        this.st = st;
        this.sqlCommand = sqlCommand;
        update();
    }

    public void update() throws SQLException {
        try(ResultSet rs = st.executeQuery(sqlCommand)) {
            while (rs.next()) {
                id = rs.getBigDecimal("OBJECT_TYPE_ID").toBigInteger();
                parentId = rs.getBigDecimal("PARENT_TYPE_ID").toBigInteger();
                name = rs.getString("NAME");
                abbreviation = rs.getString("ABBRIVIATION");
                isSystem = rs.getBoolean("IS_SYSTEM");
            }
            //TODO attributes initialization
        }
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Set<BigInteger> getAttributeIds() {
        Set<BigInteger> attributeIds = new HashSet<>();
        for (UdAttribute a: attributes) {
            attributeIds.add(a.getId());
        }
        return attributeIds;
    }

    public Set<UdAttribute> getAttributes() {
        return attributes;
    }

    public BigInteger getObjectTypeId() {
        return parentId;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setAbbreviation(String abbr) {

    }

    @Override
    public UdObjectType getParent() {
        return null;
    }

    @Override
    public void setParent(UdObjectType parent) {

    }

    @Override
    public BigInteger getParentId() {
        return null;
    }
}
