package ru.thesn.ptus.app.udm.structure;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import static ru.thesn.ptus.app.udm.UdConstants.*;


@DatabaseTable(tableName = ATTRIBUTE)
public class UdAttribute {

    public static class DAO extends BaseDaoImpl<UdAttribute, BigInteger> {

        public DAO(ConnectionSource connectionSource, Class<UdAttribute> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }

        public List<UdAttribute> getAllUdAttributes() throws SQLException{
            return this.queryForAll();
        }

    }

    UdAttribute() {
    }

    @DatabaseField(columnName = "ATTR_ID", generatedId = true, dataType = DataType.BIG_INTEGER)
    private BigInteger id;

    @DatabaseField(columnName = "NAME")
    private String name;

    @DatabaseField(columnName = "ABBREVIATION")
    private String abbreviation;

    @DatabaseField(columnName = "IS_SYSTEM", dataType = DataType.BOOLEAN)
    private boolean system;


    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private UdAttributeType type;

    @ForeignCollectionField
    private ForeignCollection<UdObject> parameters;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public UdAttributeType getType() {
        return type;
    }

    public void setType(UdAttributeType type) {
        this.type = type;
    }

    public ForeignCollection<UdObject> getParameters() {
        return parameters;
    }

    public void setParameters(ForeignCollection<UdObject> parameters) {
        this.parameters = parameters;
    }
}
