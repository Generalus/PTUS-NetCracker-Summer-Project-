package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.*;

import java.math.BigInteger;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class UdmManagerJDBC implements UdmManager {

    private static final String LOAD_USER_BY_LOGIN = "select USER_ID from UD_USER_DATA where USER_LOGIN = ?";
    private static final String LOAD_ATTR_BY_ABBR = "select ATTR_ID from UD_ATTRIBUTES where ABBRIVIATION = ?";
    private static final String LOAD_ATTR_TYPE_BY_ABBR = "select ATTR_TYPE_ID from UD_ATTRIBUTE_TYPE where ABBRIVIATION = ?";
    private static final String LOAD_OBJ_TYPE_BY_ABBR = "select OBJECT_TYPE_ID from UD_OBJECT_TYPE where ABBRIVIATION = ?";

    enum Driver {
        MySQL,
        Derby;
    }

    private synchronized static void installDB(Driver db) {
        try {
            String className;
            String url;
            Properties properties = new Properties();
            switch(db) {
                case MySQL:
                    className = "com.mysql.jdbc.Driver";
                    url = "jdbc:mysql://aksoftware.org/udm";
                    properties.put("user", "UDMADM");
                    properties.put("password", "ADMUDM");
                    break;
                case Derby:
                    className = "org.apache.derby.jdbc.EmbeddedDriver";
                    url = "jdbc:derby:memory:udm_db;";
                    properties.put("create", "true");
                    //TODO executeSqlScript(путь к create.sql)
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            Class.forName(className);
            getInstance().con = DriverManager.getConnection(url, properties);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        if(Driver.Derby.equals(db)) {
            loadScript("/create.sql");
            loadScript("/insert.sql");
        }
    }

    private static void loadScript(String path) {
        try(Scanner scanner = new Scanner(UdmManager.class.getResourceAsStream(path))) {
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String rawStatement = scanner.next();
                if(rawStatement.startsWith("--"))
                    continue;
                try (Statement currentStatement = getInstance().con.createStatement()) {
                    currentStatement.execute(rawStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static UdmManagerJDBC instance;

    private volatile Connection con;

    public synchronized Connection getConnection() {
        if(con == null)
            installDB(Driver.MySQL);
        return con;
    }

    public static UdmManagerJDBC getInstance(){
        if (instance == null){
            instance = new UdmManagerJDBC();
        }
        return instance;
    }

    public static UdmManagerJDBC getInstance(Driver driver){
        installDB(driver);
        return getInstance();
    }

    private UdmManagerJDBC() {

    }

    @Override
    public void save(UdEntity... entities) {
        for(UdEntity entity : entities)
            if(entity instanceof Storable)
                ((Storable) entity).save();
    }

    public void close() { // finalize() медленный
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String login) {
        UserJDBC user = null;
        try {
            PreparedStatement st = getConnection().prepareStatement(LOAD_USER_BY_LOGIN);
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                BigInteger id = rs.getBigDecimal("USER_ID").toBigInteger();
                user = new UserJDBC(id);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUser(BigInteger id) {
        return new UserJDBC(id);
    }

    public UdAttribute getAttribute(BigInteger id) {
        return new UdAttributeJDBC(id);
    }

    public UdAttribute getAttribute(String abbreviation) {
        UdAttribute attr = null;
        try {
            PreparedStatement st = getConnection().prepareStatement(LOAD_ATTR_BY_ABBR);
            st.setString(1, abbreviation);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                BigInteger id = rs.getBigDecimal("ATTR_ID").toBigInteger();
                attr = new UdAttributeJDBC(id);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attr;
    }

    public UdAttributeType getAttributeType(BigInteger id) {
        return new UdAttributeTypeJDBC(id);
    }

    public UdAttributeType getAttributeType(String abbreviation) {
        UdAttributeType type = null;
        try {
            PreparedStatement st = getConnection().prepareStatement(LOAD_ATTR_TYPE_BY_ABBR);
            st.setString(1, abbreviation);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                BigInteger id = rs.getBigDecimal("ATTR_TYPE_ID").toBigInteger();
                type = new UdAttributeTypeJDBC(id);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    public UdObjectType getObjectType(BigInteger id) {
        return new UdObjectTypeJDBC(id);
    }

    public UdObjectType getObjectType(String abbreviation) {
        UdObjectType type = null;
        try {
            PreparedStatement st = getConnection().prepareStatement(LOAD_OBJ_TYPE_BY_ABBR);
            st.setString(1, abbreviation);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                BigInteger id = rs.getBigDecimal("OBJECT_TYPE_ID").toBigInteger();
                type = new UdObjectTypeJDBC(id);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    public UdObject getObject(BigInteger id) {
        return new UdObjectJDBC(id);
    }
}
