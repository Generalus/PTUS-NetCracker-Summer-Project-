package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.UdAttribute;
import org.aksoftware.edu.udm.UdObjectType;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class UdObjectTypeJDBC implements UdObjectType, Storable {

    private boolean isLoaded, attrsLoaded;
    private BigInteger id;
    private String name;
    private String abbreviation;
    private Set<UdAttribute> attributes;
    private BigInteger parentId;
    private Boolean isSystem;
    private UdObjectType parent;
    private static final String LOAD = "select * from UD_OBJECT_TYPE where OBJECT_TYPE_ID = ?";
    private static final String LOAD_ATTRS = "select ATTR_ID from UD_OBJECT_TYPE_ATTRIBUTES where OBJECT_TYPE_ID = ?";
    private static final String INSERT = "insert into UD_OBJECT_TYPE (PARENT_TYPE_ID, NAME," +
            "ABBRIVIATION, IS_SYSTEM) values (?, ?, ?, ?)";
    private static final String UPDATE = "update UD_OBJECT_TYPE  set PARENT_TYPE_ID = ?, NAME = ?," +
            "ABBRIVIATION = ?, IS_SYSTEM = ? where OBJECT_TYPE_ID = ?";

    public UdObjectTypeJDBC(BigInteger id) {
        this.id = id;
        isLoaded = false;
        attrsLoaded = false;
    }

    public UdObjectTypeJDBC() {
        isLoaded = true;
        attrsLoaded = true;
    }

    private void update() {
        if(isLoaded)
            return;
        try {
            PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(LOAD);
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                id = rs.getBigDecimal("OBJECT_TYPE_ID").toBigInteger();
                parentId = rs.getBigDecimal("PARENT_TYPE_ID").toBigInteger();
                name = rs.getString("NAME");
                abbreviation = rs.getString("ABBRIVIATION");
                isSystem = rs.getBoolean("IS_SYSTEM");
            }
            if(parentId != null)
                parent = new UdObjectTypeJDBC(parentId);
            rs.close();
            isLoaded = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAttrs() {
        if(attrsLoaded)
            return;
        try {
            PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(LOAD);
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            attributes = new HashSet<>(rs.getFetchSize());
            while (rs.next()) {
                BigInteger id = rs.getBigDecimal("OBJECT_TYPE_ID").toBigInteger();
                attributes.add(new UdAttributeJDBC(id));
            }
            rs.close();
            attrsLoaded = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        if(id == null) {
            try {
                PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, parent == null ? "null" : parent.getId().toString());
                st.setString(2, name);
                st.setString(3, abbreviation);
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
                st.setString(1, parent == null ? "null" : parent.getId().toString());
                st.setString(2, name);
                st.setString(3, abbreviation);
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

    public Set<BigInteger> getAttributeIds() {
        updateAttrs();
        Set<BigInteger> attributeIds = new HashSet<>();
        for (UdAttribute a: attributes) {
            attributeIds.add(a.getId());
        }
        return attributeIds;
    }

    public Set<UdAttribute> getAttributes() {
        updateAttrs();
        return attributes;
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
    public UdObjectType getParent() {
        update();
        return parent;
    }

    @Override
    public void setParent(UdObjectType parent) {
        this.parent = parent;
    }

    @Override
    public BigInteger getParentId() {
        update();
        return parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UdObjectTypeJDBC that = (UdObjectTypeJDBC) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
