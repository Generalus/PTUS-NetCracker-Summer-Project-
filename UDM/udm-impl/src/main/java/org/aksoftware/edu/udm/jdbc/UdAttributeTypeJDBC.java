package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.UdAttributeType;
import org.aksoftware.edu.udm.exception.BadAttributeTypeParametersException;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class UdAttributeTypeJDBC implements UdAttributeType {

    private final Statement st;
    private final String sqlCommand;
    private BigInteger id;
    private String name;
    private String abbreviation;
    private String handler;
    private HashMap<String, String> params;

    public UdAttributeTypeJDBC(Statement st, String sqlCommand) throws SQLException {
        this.st = st;
        this.sqlCommand = sqlCommand;
        update();
    }

    public void update() throws SQLException {
        try(ResultSet rs = st.executeQuery(sqlCommand)) {
            while (rs.next()) {
                id = rs.getBigDecimal("ATTR_TYPE_ID").toBigInteger();
                name = rs.getString("NAME");
                abbreviation = rs.getString("ABBRIVIATION");
                processParams(rs.getString("ATTR_PARAMS"));
                handler = rs.getString("ATTR_TYPE_HANDLER");
            }
        }
    }

    private void processParams(String attrParams) {
        String[] paramsArray = attrParams.split(";");
        params = new HashMap<>();
        for (String s : paramsArray) {
            String[] tmp = s.split("=");
            if(tmp.length != 2)
                throw new BadAttributeTypeParametersException();
            params.put(tmp[0], tmp[1]);
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

    public Map<String, String> getAttributeTypeParameters() {
        return params;
    }

    public String getHandler() {
        return handler;
    }


    @Override
    public void setName(String name) {

    }

    @Override
    public void setAbbreviation(String abbr) {

    }

    @Override
    public void setAttributeTypeParameters(Map<String, String> parameters) {

    }

    @Override
    public void setHandler(String handler) {

    }
}
