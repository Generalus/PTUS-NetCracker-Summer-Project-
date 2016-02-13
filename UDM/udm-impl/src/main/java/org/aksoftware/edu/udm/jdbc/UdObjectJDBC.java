package org.aksoftware.edu.udm.jdbc;

//import com.sun.tools.internal.xjc.reader.dtd.bindinfo.BIAttribute;
import org.aksoftware.edu.udm.Parameter;
import org.aksoftware.edu.udm.UdObject;
import org.aksoftware.edu.udm.UdObjectType;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


public class UdObjectJDBC implements UdObject {

    private final Statement st;
    private final String sqlCommand;
    private BigInteger id;
    private BigInteger parentId;
    private String name;
    private String abbreviation;
    //TODO SECURITY_LEVEL, OWNER_USER_ID
    private UdObjectType udObjectType;
    private UdObject parent;
    private Set<Parameter> parameters = new HashSet<>();

    public UdObjectJDBC(Statement st, String sqlCommand) throws SQLException {
        this.st = st;
        this.sqlCommand = sqlCommand;
        update();
    }

    public void update() throws SQLException {
        try(ResultSet rs = st.executeQuery(sqlCommand)) {
            BigInteger typeId = null;
            while (rs.next()) {
                id = rs.getBigDecimal("OBJECT_ID").toBigInteger();
                typeId = rs.getBigDecimal("OBJECT_TYPE_ID").toBigInteger();
                parentId = rs.getBigDecimal("PARENT_OBJECT_ID").toBigInteger();
                name = rs.getString("NAME");
                abbreviation = rs.getString("ABBRIVIATION");
                // TODO SECURITY_LEVEL, etc.
            }

            udObjectType = new UdObjectTypeJDBC(st, "select *" +
                " from udm.UD_OBJECT_TYPE where OBJECT_TYPE_ID = " + typeId);
            /*parent = new UdObjectJDBC(st, "select *" +
                " from udm.UD_OBJECT where OBJECT_ID = " + parentId);*/
        }
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UdObject getParent() {
        if (parentId == null) return null;
        if (parent != null) return parent;
        try {
            parent = new UdObjectJDBC(st, "select *" +
                    " from udm.UD_OBJECT where OBJECT_ID = " + parentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parent;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public UdObjectType getObjectType() {
        return udObjectType;
    }

    public BigInteger getObjectTypeId() {
        return udObjectType.getId();
    }

    public void setParameter(Parameter parameter) {
        parameters.add((ParameterJDBC)parameter);
    }

    public Parameter getParameter(BigInteger id) {
        for(Parameter par : parameters)
            if(par.getId().equals(id))
                return par;
        return null;
    }

    public Set<? extends Parameter> getParameters() {
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
        this.udObjectType = type;
    }

    @Override
    public void setParameters(Set<Parameter> parameters) {

    }

    @Override
    public void addParameter(Parameter parameter) {

    }
}
