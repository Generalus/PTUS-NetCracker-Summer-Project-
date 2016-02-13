package ru.thesn.ptus.app.udm.structure;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public class UdAttributeTypeHandler {

    public static class DAO extends BaseDaoImpl<UdAttributeTypeHandler, BigInteger> {

        public DAO(ConnectionSource connectionSource, Class<UdAttributeTypeHandler> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }

        public List<UdAttributeTypeHandler> getAllUdAttributeTypeHandlers() throws SQLException{
            return this.queryForAll();
        }

    }


    UdAttributeTypeHandler() {
    }


}
