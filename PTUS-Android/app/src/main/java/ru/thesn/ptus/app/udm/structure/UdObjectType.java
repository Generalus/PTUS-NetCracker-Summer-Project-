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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.thesn.ptus.app.udm.UdConstants.*;


@DatabaseTable(tableName = OBJECT_TYPE)
public class UdObjectType {

    public static class DAO extends BaseDaoImpl<UdObjectType, BigInteger> {

        public DAO(ConnectionSource connectionSource, Class<UdObjectType> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }

        public List<UdObjectType> getAllUdObjectTypes() throws SQLException{
            return this.queryForAll();
        }

    }

    UdObjectType() {
    }

    @DatabaseField(columnName = "OBJECT_TYPE_ID", generatedId = true, dataType = DataType.BIG_INTEGER)
    private BigInteger id;

    @DatabaseField(columnName = "NAME")
    private String name;

    @DatabaseField(columnName = "ABBREVIATION")
    private String abbreviation;

    @ForeignCollectionField
    private ForeignCollection<UdObject> UdObjects;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private UdObjectType parent;

    @ForeignCollectionField
    private ForeignCollection<UdObject> children;


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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public ForeignCollection<UdObject> getUdObjects() {
        return UdObjects;
    }

    public void setUdObjects(ForeignCollection<UdObject> udObjects) {
        UdObjects = udObjects;
    }

    public UdObjectType getParent() {
        return parent;
    }

    public void setParent(UdObjectType parent) {
        this.parent = parent;
    }

    public ForeignCollection<UdObject> getChildren() {
        return children;
    }

    public void setChildren(ForeignCollection<UdObject> children) {
        this.children = children;
    }
}
