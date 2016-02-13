package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public enum UdmManagerJDBC implements UdmManager {

    MySQL,
    Derby;

    private volatile Connection con;

    private synchronized Connection getConnection() {
        if (con == null){
            try {
                String className;
                String url;
                Properties properties = new Properties();

                switch (this.name()){
                    case "MySQL":
                        className = "com.mysql.jdbc.Driver";
                        url = "jdbc:mysql://aksoftware.org";
                        properties.put("user", "UDMADM");
                        properties.put("password", "ADMUDM");
                        break;
                    case "Derby":
                        className = "org.apache.derby.jdbc.EmbeddedDriver";
                        url = "jdbc:derby:memory:udm_db;";
                        properties.put("create", "true");
                        //TODO executeSqlScript(путь к create.sql)
                        break;
                    default:
                        throw new IllegalArgumentException();
                }

                Class.forName(className);
                con = DriverManager.getConnection(url, properties);

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }

    public synchronized void close() { // finalize() медленный
        try {
            if (con != null) {
                con.close();
            }
            con = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeSqlScript(File inputFile) {
        String delimiter = ";";
        try(Scanner scanner = new Scanner(inputFile).useDelimiter(delimiter)) {
            while (scanner.hasNext()) {
                String rawStatement = scanner.next() + delimiter;
                try (Statement currentStatement = getConnection().createStatement()) {
                    currentStatement.execute(rawStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String login) {
        return null; // TODO
    }

    public User getUser(BigInteger id) {
        /*
        String query = "select *" +
                " from udm.UD_OBJECT where OBJECT_ID = " + id;
        try {
            return new UdObjectJDBC(st, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        return null;
    }

    public UdAttribute getAttribute(BigInteger id) {
        String query = "select *" +
                " from udm.UD_ATTRIBUTE where ATTR_ID = " + id;
        try {
            return new UdAttributeJDBC(getConnection().createStatement(), query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UdAttribute getAttribute(String abbreviation) {
        String query = "select *" +
                " from udm.UD_ATTRIBUTE where ABBRIVIATION = " + abbreviation;
        try {
            return new UdAttributeJDBC(getConnection().createStatement(), query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UdAttributeType getAttributeType(BigInteger id) {
        String query = "select *" +
                " from udm.UD_ATTRIBUTE_TYPE where ATTR_TYPE_ID = " + id;
        try {
            return new UdAttributeTypeJDBC(getConnection().createStatement(), query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UdAttributeType getAttributeType(String abbreviation) {
        String query = "select *" +
                " from udm.UD_ATTRIBUTE_TYPE where ABBRIVIATION = " + abbreviation;
        try {
            return new UdAttributeTypeJDBC(getConnection().createStatement(), query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UdObjectType getObjectType(BigInteger id) {
        String query = "select *" +
                " from udm.UD_OBJECT_TYPE where OBJECT_TYPE_ID = " + id;
        try {
            return new UdObjectTypeJDBC(getConnection().createStatement(), query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UdObjectType getObjectType(String abbreviation) {
        String query = "select *" +
                " from udm.UD_OBJECT_TYPE where ABBRIVIATION = " + abbreviation;
        try {
            return new UdObjectTypeJDBC(getConnection().createStatement(), query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UdObject getObject(BigInteger id) {
        String query = "select *" +
                " from udm.UD_OBJECT where OBJECT_ID = " + id;
        try {
            return new UdObjectJDBC(getConnection().createStatement(), query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(UdEntity... entities) {
        for(UdEntity object: entities){
            try {
                object.getClass().getMethod("save").invoke(object);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
