package org.aksoftware.edu.udm.jpa;

import org.aksoftware.edu.udm.UdAttributeType;
import org.aksoftware.edu.udm.exception.BadAttributeTypeParametersException;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;


@Entity
@Table(name = "UD_ATTRIBUTE_TYPE")

@NamedQueries({
        @NamedQuery(name = "attr_type_by_abbr", query = "SELECT attr FROM UdAttributeTypeJPA attr where attr.abbr = ?1")
})

public class UdAttributeTypeJPA implements UdAttributeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTR_TYPE_ID")
    private BigInteger id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ATTR_PARAMS")
    private String params;

    @Column(name = "ABBRIVIATION")
    private String abbr;

    @Column(name = "ATTR_TYPE_HANDLER")
    private String handler;


    public UdAttributeTypeJPA(String name, String params, String abbr, String handler) {
        this.name = name;
        this.params = params;
        this.abbr = abbr;
        this.handler = handler;
    }

    public UdAttributeTypeJPA() {
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbr;
    }

    public void setAbbreviation(String abbr) {
        this.abbr = abbr;
    }

    public Map<String, String> getAttributeTypeParameters() throws BadAttributeTypeParametersException{
        Map<String, String> map = new HashMap<>();
        String[] s = params.split(";");
        for(int i = 0; i < s.length - 1; i++) {
            String[] s2 = s[i].split("=");
            if(s2.length != 2)
                throw new BadAttributeTypeParametersException();
            map.put(s2[0], s[1]);
        }
        return map;
    }

    public void setAttributeTypeParameters(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        for(String par : parameters.keySet()) {
            sb.append(par).append("=").append(parameters.get(par)).append(";");
        }
        params = sb.toString();
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UdAttributeTypeJPA that = (UdAttributeTypeJPA) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
