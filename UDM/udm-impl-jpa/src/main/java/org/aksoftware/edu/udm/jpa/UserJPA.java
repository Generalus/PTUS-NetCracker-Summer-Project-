package org.aksoftware.edu.udm.jpa;

import org.aksoftware.edu.udm.User;

import javax.persistence.*;
import java.math.BigInteger;


@Entity
@Table(name = "UD_USER_DATA")
@NamedQueries({
        @NamedQuery(name = "user_by_login", query = "SELECT u FROM UserJPA u where u.login = ?1")
})


public class UserJPA implements User {  // [Никита]: что делать с этим классом?
                                        // Связывать как-нибудь еще с UdObjectJPA?
                                        // Не знаю, уместно ли здесь наследование,
                                        // хотя в диаграмме классов оно явным образом указано
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private BigInteger id;

    @Column(name = "USER_ABBRIVIATION")
    private String abbr;

    @Column(name = "USER_LOGIN")
    private String login;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_OBJECT_ID")
    private BigInteger objectID;

    @Column(name = "USER_CATEGORY")
    private Integer userCategory;

    public UserJPA(String login, String password, String abbr, BigInteger objectID, Integer userCategory) {
        this.abbr = abbr;
        this.login = login;
        this.password = password;
        this.objectID = objectID;
        this.userCategory = userCategory;
    }

    public UserJPA() {
    }

    public BigInteger getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getObjectID() {
        return objectID;
    }

    public void setObjectID(BigInteger objectID) {
        this.objectID = objectID;
    }

    public Integer getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(Integer userCategory) {
        this.userCategory = userCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserJPA userJPA = (UserJPA) o;

        return id.equals(userJPA.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
