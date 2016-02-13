package ru.thesn.ptus.app.udm.structure;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import static ru.thesn.ptus.app.udm.UdConstants.*;


@DatabaseTable(tableName = ABSTRACT_PROPERTY)
public class UdParameter {

    public static class DAO extends BaseDaoImpl<UdParameter, BigInteger> {

        public DAO(ConnectionSource connectionSource, Class<UdParameter> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }

        public List<UdParameter> getAllUdParameters() throws SQLException{
            return this.queryForAll();
        }

    }

    UdParameter() {
    }

    @DatabaseField(columnName = "PROPERTY_ID", generatedId = true, dataType = DataType.BIG_INTEGER)
    private BigInteger id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private UdAttribute attribute;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private UdObject object;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public UdAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(UdAttribute attribute) {
        this.attribute = attribute;
    }

    public UdObject getObject() {
        return object;
    }

    public void setObject(UdObject object) {
        this.object = object;
    }
}
