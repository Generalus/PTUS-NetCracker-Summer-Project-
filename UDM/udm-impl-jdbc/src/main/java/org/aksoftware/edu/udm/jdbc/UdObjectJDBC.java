package org.aksoftware.edu.udm.jdbc;

//import com.sun.tools.internal.xjc.reader.dtd.bindinfo.BIAttribute;
import org.aksoftware.edu.udm.Parameter;
import org.aksoftware.edu.udm.UdObject;
import org.aksoftware.edu.udm.UdObjectType;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


public class UdObjectJDBC implements UdObject, Storable {

    private boolean isLoaded, paramsLoaded;
    private BigInteger id;
    private BigInteger parentId;
    private BigInteger typeId;
    private String name;
    private String abbreviation;
    //TODO SECURITY_LEVEL, OWNER_USER_ID
    private UdObjectType objectType;
    private UdObject parent;
    private Set<Parameter> parameters;
    private static final String LOAD = "select * from UD_OBJECT where OBJECT_ID = ?";
    private static final String LOAD_PARAMS = "select PROPERTY_ID from ABSTRACT_PROPERTY where OBJECT_ID = ?";
    private static final String INSERT = "insert into UD_OBJECT (OBJECT_TYPE_ID, PARENT_OBJECT_ID," +
            "NAME, ABBRIVIATION) values (?, ?, ?, ?)";
    private static final String UPDATE = "update UD_OBJECT  set OBJECT_TYPE_ID = ?, PARENT_OBJECT_ID = ?," +
            "NAME = ?, ABBRIVIATION = ? where OBJECT_ID = ?";

    public UdObjectJDBC(BigInteger id) {
        this.id = id;
        isLoaded = false;
        paramsLoaded = false;
    }

    public UdObjectJDBC() {
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
                typeId = rs.getBigDecimal("OBJECT_TYPE_ID").toBigInteger();
                parentId = rs.getBigDecimal("PARENT_OBJECT_ID").toBigInteger();
                name = rs.getString("NAME");
                abbreviation = rs.getString("ABBRIVIATION");
            }
            if(typeId != null)
                objectType = new UdObjectTypeJDBC(typeId);
            if(parentId != null)
                parent = new UdObjectJDBC(parentId);
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
            parameters = new HashSet<>(rs.getFetchSize());
            if (rs.next()) {
                BigInteger id = rs.getBigDecimal("PROPERTY_ID").toBigInteger();
                parameters.add(new ParameterJDBC(id));
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
                st.setString(1, objectType == null ? "null" : objectType.getId().toString());
                st.setString(2, parent == null ? "null" : parent.getId().toString());
                st.setString(3, name);
                st.setString(4, abbreviation);
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
                st.setString(1, objectType == null ? "null" : objectType.getId().toString());
                st.setString(2, parent == null ? "null" : parent.getId().toString());
                st.setString(3, name);
                st.setString(4, abbreviation);
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

    public UdObject getParent() {
        update();
        return parent;
    }

    public BigInteger getParentId() {
        update();
        return parentId;
    }

    public UdObjectType getObjectType() {
        update();
        return objectType;
    }

    public BigInteger getObjectTypeId() {
        update();
        return typeId;
    }

    public Parameter getParameter(BigInteger id) {
        updateParams();
        for(Parameter par : parameters)
            if(par.getId().equals(id))
                return par;
        return null;
    }

    public Set<? extends Parameter> getParameters() {
        updateParams();
        return parameters;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setParent(UdObject parent) {
        this.parent = parent;
    }

    @Override
    public void setObjectType(UdObjectType type) {
        this.objectType = type;
    }

    @Override
    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UdObjectJDBC that = (UdObjectJDBC) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
