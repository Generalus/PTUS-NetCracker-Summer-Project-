package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.Parameter;
import org.aksoftware.edu.udm.UdAttribute;
import org.aksoftware.edu.udm.UdAttributeType;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;


public class UdAttributeJDBC implements UdAttribute {

    private final Statement st;
    private final String sqlCommand;
    private BigInteger id;
    private BigInteger attrTypeId;
    private String abbreviation;
    private String name;
    private boolean isSystem;
    private UdAttributeTypeJDBC udAttributeType;

    public UdAttributeJDBC(Statement st, String sqlCommand) throws SQLException {
        this.st = st;
        this.sqlCommand = sqlCommand;
        update();
    }

    public void update() throws SQLException {
        try(ResultSet rs = st.executeQuery(sqlCommand)) {
            while (rs.next()) {
                id = rs.getBigDecimal("ATTR_ID").toBigInteger();
                attrTypeId = rs.getBigDecimal("ATTR_TYPE_ID").toBigInteger();
                abbreviation = rs.getString("ABBRIVIATION");
                name = rs.getString("NAME");
                //TODO ATTR_TAGS, ATTR_PARAMS
                isSystem = rs.getBoolean("IS_SYSTEM");// Считает ли tinyint(1)?
            }
            udAttributeType = new UdAttributeTypeJDBC(st, "select *" +
                    " from udm.UD_ATTRIBUTE_TYPE where ATTR_TYPE_ID = " + attrTypeId);
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

    public UdAttributeType getAttributeType() {
        return udAttributeType;
    }

    public BigInteger getAttributeTypeId() {
        return attrTypeId;
    }

    public boolean isSystem() {
        return isSystem;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setAbbreviation(String abbr) {

    }

    @Override
    public void setAttributeType(UdAttributeType type) {

    }

    @Override
    public void setSystem(boolean isSystem) {

    }

    @Override
    public void setParameters(Set<Parameter> parameters) {

    }

    @Override
    public Parameter getParameter(BigInteger id) {
        return null;
    }

    @Override
    public Set<? extends Parameter> getParameters() {
        return null;
    }

    @Override
    public void addParameter(Parameter parameter) {

    }
}
