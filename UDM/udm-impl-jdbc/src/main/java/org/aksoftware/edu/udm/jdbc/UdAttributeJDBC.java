package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.Parameter;
import org.aksoftware.edu.udm.UdAttribute;
import org.aksoftware.edu.udm.UdAttributeType;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


public class UdAttributeJDBC implements UdAttribute, Storable {

    private boolean isLoaded, paramsLoaded;
    private BigInteger id;
    private BigInteger attrTypeId;
    private String abbreviation;
    private String name;
    private boolean isSystem;
    private static final String LOAD = "select * from UD_ATTRIBUTE where ATTR_ID = ?";
    private static final String LOAD_PARAMS = "select PROPERTY_ID from ABSTRACT_PROPERTY where ATTR_ID = ?";
    private static final String INSERT = "insert into UD_ATTRIBUTE (ATTR_TYPE_ID, ABBRIVIATION," +
            "NAME, IS_SYSTEM) values (?, ?, ?, ?)";
    private static final String UPDATE = "update UD_ATTRIBUTE  set ATTR_TYPE_ID = ?, ABBRIVIATION = ?," +
            "NAME = ?, IS_SYSTEM = ? where ATTR_ID = ?";


    private UdAttributeType attributeType;
    private Set<Parameter> params;

    public UdAttributeJDBC(BigInteger id) {
        this.id = id;
        isLoaded = false;
        paramsLoaded = false;
    }

    public UdAttributeJDBC() {
        isLoaded = true;
        paramsLoaded = true;
    }

    private void update() {
        if(isLoaded)
            return;
        try {
            PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(LOAD);
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getBigDecimal("ATTR_ID").toBigInteger();
                attrTypeId = rs.getBigDecimal("ATTR_TYPE_ID").toBigInteger();
                abbreviation = rs.getString("ABBRIVIATION");
                name = rs.getString("NAME");
                //TODO ATTR_TAGS, ATTR_PARAMS
                isSystem = rs.getBoolean("IS_SYSTEM");// Считает ли tinyint(1)?
            }
            if(attrTypeId != null)
                attributeType = new UdAttributeTypeJDBC(attrTypeId);
            rs.close();
            isLoaded = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateParams() {
        if(paramsLoaded)
            return;
        try {
            PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(LOAD_PARAMS);
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            params = new HashSet<>(rs.getFetchSize());
            while (rs.next()) {
                BigInteger id = rs.getBigDecimal("PROPERTY_ID").toBigInteger();
                params.add(new ParameterJDBC(id));
            }
            rs.close();
            paramsLoaded = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        if(id == null) {
            try {
                PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, attributeType == null ? "null" : attributeType.getId().toString());
                st.setString(2, abbreviation);
                st.setString(3, name);
                st.setString(4, "" + isSystem);
                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getBigDecimal(1).toBigInteger();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if(isLoaded){
            try {
                PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(UPDATE);
                st.setString(1, attributeType == null ? "null" : attributeType.getId().toString());
                st.setString(2, abbreviation);
                st.setString(3, name);
                st.setString(4, "" + isSystem);
                st.setString(5, id.toString());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        update();
        return name;
    }

    public String getAbbreviation() {
        update();
        return abbreviation;
    }

    public UdAttributeType getAttributeType() {
        update();
        return attributeType;
    }

    public BigInteger getAttributeTypeId() {
        update();
        return attrTypeId;
    }

    public boolean isSystem() {
        update();
        return isSystem;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAbbreviation(String abbr) {
        this.abbreviation = abbr;
    }

    @Override
    public void setAttributeType(UdAttributeType type) {
        this.attributeType = type;
    }

    @Override
    public void setSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    @Override
    public void setParameters(Set<Parameter> parameters) {
        this.params = parameters;
    }

    @Override
    public Parameter getParameter(BigInteger id) {
        updateParams();
        for(Parameter par : params)
            if(par.getId().equals(id))
                return par;
        return null;
    }

    @Override
    public Set<? extends Parameter> getParameters() {
        updateParams();
        return params;
    }

    @Override
    public void addParameter(Parameter parameter) {
        params.add(parameter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UdAttributeJDBC that = (UdAttributeJDBC) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
