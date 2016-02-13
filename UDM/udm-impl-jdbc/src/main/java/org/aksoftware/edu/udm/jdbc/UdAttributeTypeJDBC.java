package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.UdAttributeType;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class UdAttributeTypeJDBC implements UdAttributeType, Storable {

    private boolean isLoaded;
    private BigInteger id;
    private String name;
    private String abbreviation;
    private String handler;
    private Map<String, String> params;
    private static final String LOAD = "select * from UD_ATTRIBUTE_TYPE where ATTR_TYPE_ID = ?";
    private static final String INSERT = "insert into UD_ATTRIBUTE_TYPE (NAME, ABBRIVIATION, ATTR_PARAMS, ATTR_TYPE_HANDLER) values (?, ?, ?, ?)";
    private static final String UPDATE = "update UD_ATTRIBUTE_TYPE set NAME = ?, " +
            "ABBRIVIATION = ?, ATTR_PARAMS = ?, ATTR_TYPE_HANDLER = ? where ATTR_TYPE_ID = ?";

    public UdAttributeTypeJDBC(BigInteger id) {
        this.id = id;
        isLoaded = false;
    }

    public UdAttributeTypeJDBC() {
        isLoaded = true;
    }

    private void update() {
        if(isLoaded)
            return;
        try {
            PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(LOAD);
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getBigDecimal("ATTR_TYPE_ID").toBigInteger();
                name = rs.getString("NAME");
                abbreviation = rs.getString("ABBRIVIATION");
                processParams(rs.getString("ATTR_PARAMS"));
                handler = rs.getString("ATTR_TYPE_HANDLER");
            }
            rs.close();
            isLoaded = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        if(id == null) {
            try {
                PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, name);
                st.setString(2, abbreviation);
                st.setString(3, params());
                st.setString(4, handler);
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
                st.setString(1, name);
                st.setString(2, abbreviation);
                st.setString(3, params());
                st.setString(4, handler);
                st.setString(5, id.toString());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String params() {
        StringBuilder sb = new StringBuilder();
        for(String par : params.keySet()) {
            sb.append(par).append("=").append(params.get(par)).append(";");
        }
        return sb.toString();
    }

    private void processParams(String attrParams) {
        String[] paramsArray = attrParams.split(";");
        params = new HashMap<>(paramsArray.length);
        for (int i = 0; i < paramsArray.length - 1; i++) {
            String[] tmp = paramsArray[i].split("=");
            params.put(tmp[0], tmp[1]);
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

    public Map<String, String> getAttributeTypeParameters() {
        update();
        return params;
    }

    public String getHandler() {
        update();
        return handler;
    }


    @Override
    public void setName(String name) {
        update();
        this.name = name;
    }

    @Override
    public void setAbbreviation(String abbr) {
        update();
        this.abbreviation = abbr;
    }

    @Override
    public void setAttributeTypeParameters(Map<String, String> parameters) {
        update();
        this.params = parameters;
    }

    @Override
    public void setHandler(String handler) {
        update();
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UdAttributeTypeJDBC that = (UdAttributeTypeJDBC) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
