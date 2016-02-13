package org.aksoftware.edu.udm.jdbc;


import org.aksoftware.edu.udm.TestApi;
import org.aksoftware.edu.udm.UdmManager;
import org.junit.*;
import java.util.HashMap;
import java.util.Map;

@Ignore // [Никита]: Все эти тесты пока игнорируются, поскольку JDBC-модуль не работает должным образом!
public class UdmManagerTest extends TestApi{
    private static UdmManager manager;

    private static Map<String, Class> classes;

    static {
        classes = new HashMap<>();
        classes.put("User", UserJDBC.class);
        classes.put("ObjectType", UdObjectTypeJDBC.class);
        classes.put("Object", UdObjectJDBC.class);
        //TODO добавить остальные по аналогии
    }

    public Class getClassByName(String name) throws ClassNotFoundException{
        if (!classes.containsKey(name))
            throw new ClassNotFoundException();
        return classes.get(name);
    }

    @BeforeClass
    public static void setUp(){
        manager = UdmManagerJDBC.Derby;
        //TODO executeSqlScript(путь к insert.sql), если пользуемся базой Derby
    }

    @Override
    public UdmManager getManager() {
        return manager;
    }

    @AfterClass
    public static void tearDown(){
        manager.close();
    }

}
