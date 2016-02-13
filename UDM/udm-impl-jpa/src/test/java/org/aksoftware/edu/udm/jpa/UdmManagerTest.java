package org.aksoftware.edu.udm.jpa;

import org.aksoftware.edu.udm.TestApi;
import org.aksoftware.edu.udm.UdmManager;
import org.junit.*;
import java.util.HashMap;
import java.util.Map;


public class UdmManagerTest extends TestApi{
    private static UdmManager manager;

    private static Map<String, Class> classes;

    static {
        classes = new HashMap<>();
        classes.put("User", UserJPA.class);
        classes.put("ObjectType", UdObjectTypeJPA.class);
        classes.put("Object", UdObjectJPA.class);
        //TODO добавить остальные по аналогии
    }

    public Class getClassByName(String name) throws ClassNotFoundException{
        if (!classes.containsKey(name))
            throw new ClassNotFoundException();
        return classes.get(name);
    }

    @BeforeClass
    public static void setUp(){
        manager = UdmManagerJPA.Derby;
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
