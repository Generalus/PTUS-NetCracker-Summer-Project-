package ru.thesn.ptus.app.udm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import ru.thesn.ptus.app.udm.structure.*;
import static ru.thesn.ptus.app.udm.UdConstants.*;
import java.sql.SQLException;




public class DatabaseHelper extends OrmLiteSqliteOpenHelper implements BaseColumns {

    private SQLiteDatabase db;
    private Context context;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PTUS.db";

    private UdAttribute.DAO udAttributeDao;
    private UdAttributeType.DAO udAttributeTypeDao;
    private UdAttributeTypeHandler.DAO udAttributeTypeHandlerDao;
    private UdObject.DAO udObjectDao;
    private UdObjectType.DAO udObjectTypeDao;
    private UdParameter.DAO udParameterDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }

    @Override
    public synchronized void close() {
        db.close();
        super.close();
    }

    @Override
    public synchronized void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        SQLExecutor.executeSqlScript(context, db, "ptus_db_create.sql", true);
    }

    @Override
    public synchronized void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        dropTable(USER_PERMISSION);
        dropTable(USER_DATA);
        dropTable(ABSTRACT_PROPERTY);
        dropTable(COUNTER_PROPERTY);
        dropTable(PROPERTY);
        dropTable(ATTRIBUTE_TYPE);
        dropTable(OBJECT_TYPE_ATTRIBUTES);
        dropTable(ATTRIBUTE);
        dropTable(OBJECT_TYPE);
        dropTable(OBJECT);

        onCreate(db);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public UdAttribute.DAO getUdAttributeDao() throws SQLException{
        if(udAttributeDao == null){
            udAttributeDao = new UdAttribute.DAO(getConnectionSource(), UdAttribute.class);
        }
        return udAttributeDao;
    }

    public UdAttributeType.DAO getUdAttributeTypeDao() throws SQLException{
        if(udAttributeTypeDao == null){
            udAttributeTypeDao = new UdAttributeType.DAO(getConnectionSource(), UdAttributeType.class);
        }
        return udAttributeTypeDao;
    }

    public UdAttributeTypeHandler.DAO getUdAttributeTypeHandlerDao() throws SQLException {
        if(udAttributeTypeHandlerDao == null){
            udAttributeTypeHandlerDao = new UdAttributeTypeHandler.DAO(getConnectionSource(), UdAttributeTypeHandler.class);
        }
        return udAttributeTypeHandlerDao;
    }

    public UdObject.DAO getUdObjectDao() throws SQLException{
        if(udObjectDao == null){
            udObjectDao = new UdObject.DAO(getConnectionSource(), UdObject.class);
        }
        return udObjectDao;
    }

    public UdObjectType.DAO getUdObjectTypeDao() throws SQLException{
        if(udObjectTypeDao == null){
            udObjectTypeDao = new UdObjectType.DAO(getConnectionSource(), UdObjectType.class);
        }
        return udObjectTypeDao;
    }

    public UdParameter.DAO getUdParameterDao() throws SQLException{
        if(udParameterDao == null){
            udParameterDao = new UdParameter.DAO(getConnectionSource(), UdParameter.class);
        }
        return udParameterDao;
    }



    public synchronized void dropTable(String name){
        String format = "DROP TABLE IF EXISTS '%s'";
        db.execSQL(String.format(format, name));
    }


}
