package org.aksoftware.edu.udm.jdbc;

import org.aksoftware.edu.udm.UdObject;
import org.aksoftware.edu.udm.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserJDBC implements User, Storable {

    private boolean isLoaded;
    private BigInteger id;
    private BigInteger objectId;
    private String login;
    private String password;
    private String abbreviation;
    private Integer category;
    //TODO SECURITY_LEVEL, OWNER_USER_ID
    private UdObject object;
    private static final String LOAD = "select * from UD_USER_DATA where USER_ID = ?";
    private static final String INSERT = "insert into UD_USER_DATA (USER_ABBRIVIATION, USER_LOGIN," +
            "USER_PASSWORD, USER_OBJECT_ID, USER_CATEGORY) values (?, ?, ?, ?, ?)";
    private static final String UPDATE = "update UD_USER_DATA  set USER_ABBRIVIATION = ?, USER_LOGIN = ?," +
            "USER_PASSWORD = ?, USER_OBJECT_ID = ?, USER_CATEGORY = ? where USER_ID = ?";

    public UserJDBC(BigInteger id) {
        this.id = id;
        isLoaded = false;
    }

    public UserJDBC() {
        isLoaded = true;
    }
    public UserJDBC(String login, String password, String abbr, BigInteger objectID, Integer userCategory) {
        isLoaded = true;
        this.abbreviation = abbr;
        this.login = login;
        this.password = password;
        this.objectId = objectID;
        this.category = userCategory;
        if(objectID != null)
            object = new UdObjectJDBC(objectID);
    }

    private void update() {
        if(isLoaded)
            return;
        try {
            PreparedStatement st = UdmManagerJDBC.getInstance().getConnection().prepareStatement(LOAD);
            st.setString(1, id.toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                login = rs.getString("USER_LOGIN");
                password = rs.getString("USER_PASSWORD");
                BigDecimal bd = rs.getBigDecimal("USER_OBJECT_ID");
                if(bd == null)
                    objectId = null;
                else
                    objectId = bd.toBigInteger();
                category = rs.getInt("USER_CATEGORY");
                abbreviation = rs.getString("USER_ABBRIVIATION");
            }
            if(objectId != null)
                object = new UdObjectJDBC(objectId);
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
                st.setString(1, abbreviation);
                st.setString(2, login);
                st.setString(3, password);
                st.setString(4, object == null ? null : object.getId().toString());
                st.setInt(5, category);
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
                st.setString(1, abbreviation);
                st.setString(2, login);
                st.setString(3, password);
                st.setString(4, object == null ? "null" : object.getId().toString());
                st.setInt(5, category);
                st.setString(6, id.toString());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public String getLogin() {
        update();
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getAbbr() {
        update();
        return abbreviation;
    }

    @Override
    public void setAbbr(String abbr) {
        this.abbreviation = abbr;
    }

    @Override
    public String getPassword() {
        update();
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public BigInteger getObjectID() {
        update();
        return objectId;
    }

    @Override
    public void setObjectID(BigInteger objectID) {
        this.objectId = objectID;
    }

    @Override
    public Integer getUserCategory() {
        update();
        return category;
    }

    @Override
    public void setUserCategory(Integer userCategory) {
        this.category = userCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserJDBC userJDBC = (UserJDBC) o;

        return id.equals(userJDBC.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
