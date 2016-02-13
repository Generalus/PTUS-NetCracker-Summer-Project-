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

@DatabaseTable(tableName = OBJECT)
public class UdObject {

    public static class DAO extends BaseDaoImpl<UdObject, BigInteger> {

        public DAO(ConnectionSource connectionSource, Class<UdObject> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }

        public List<UdObject> getAllUdObjects() throws SQLException{
            return this.queryForAll();
        }

    }

    UdObject() {
    }

    @DatabaseField(columnName = "OBJECT_ID", generatedId = true, dataType = DataType.BIG_INTEGER)
    private BigInteger id;

    @DatabaseField(columnName = "NAME")
    private String name;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private UdObject parent;

    @ForeignCollectionField
    private ForeignCollection<UdObject> children;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private UdObjectType type;

    @ForeignCollectionField
    private ForeignCollection<UdObject> parameters;

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

    public UdObject getParent() {
        return parent;
    }

    public void setParent(UdObject parent) {
        this.parent = parent;
    }

    public ForeignCollection<UdObject> getChildren() {
        return children;
    }

    public void setChildren(ForeignCollection<UdObject> children) {
        this.children = children;
    }

    public UdObjectType getType() {
        return type;
    }

    public void setType(UdObjectType type) {
        this.type = type;
    }

    public ForeignCollection<UdObject> getParameters() {
        return parameters;
    }

    public void setParameters(ForeignCollection<UdObject> parameters) {
        this.parameters = parameters;
    }
}
