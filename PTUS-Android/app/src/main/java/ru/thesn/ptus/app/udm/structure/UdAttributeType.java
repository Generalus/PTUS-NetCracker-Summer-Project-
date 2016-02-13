package ru.thesn.ptus.app.udm.structure;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.thesn.ptus.app.udm.UdConstants.*;


@DatabaseTable(tableName = ATTRIBUTE_TYPE)
public class UdAttributeType {

    public static class DAO extends BaseDaoImpl<UdAttributeType, BigInteger> {

        public DAO(ConnectionSource connectionSource, Class<UdAttributeType> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }

        public List<UdAttributeType> getAllUdAttributeTypes() throws SQLException{
            return this.queryForAll();
        }

    }

    UdAttributeType() {
    }

    @DatabaseField(columnName = "ATTR_TYPE_ID", generatedId = true, dataType = DataType.BIG_INTEGER)
    private BigInteger id;

    @DatabaseField(columnName = "NAME")
    private String name;

    @DatabaseField(columnName = "ATTR_PARAMS")
    private String params;


    @DatabaseField(columnName = "ABBREVIATION")
    private String abbreviation;

    @DatabaseField(columnName = "ATTR_TYPE_HANDLER")
    private String handler;

    @ForeignCollectionField
    private ForeignCollection<UdAttribute> udAttributes;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public ForeignCollection<UdAttribute> getUdAttributes() {
        return udAttributes;
    }

    public void setUdAttributes(ForeignCollection<UdAttribute> udAttributes) {
        this.udAttributes = udAttributes;
    }
}
