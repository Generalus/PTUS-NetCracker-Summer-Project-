package org.aksoftware.edu.udm;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.Set;


public abstract class TestApi {

    public abstract UdmManager getManager();


    // ==== Служебные методы ====

    public abstract Class getClassByName(String name) throws ClassNotFoundException;


    /*
     *  [Никита]: Если в конструктор требуется отправить null, то вместо него отправлять класс объекта.
     *  Например, double.class, getClassByName("ваш ключ для класса из UDM-JPA")
     */

    private Object getObject(String className, Object... args) throws Exception{
        Class[] classes = new Class[args.length];
        for(int i = 0; i < args.length; i++) {
            Object object = args[i];
            if (object instanceof Class) {
                classes[i] = (Class)object;
                args[i] = null;
            } else if (object != null){
                classes[i] = object.getClass();
            } else {
                throw new NullPointerException("Передан нулевой параметр в тестах!");
            }
        }
        Constructor c = getClassByName(className).getConstructor(classes);
        return c.newInstance(args);
    }

    // ===========================

    @Test
    public void testGetAttributes(){
        getManager().getUser(BigInteger.ONE);
        getManager().getObjectType(BigInteger.ONE);
        getManager().getObject(BigInteger.ONE);
        getManager().getAttributeType(BigInteger.ONE);
        getManager().getAttribute(BigInteger.ONE);
    }



    @Test
    public void testUserEquals(){
        try {
            User user = (User)getObject("User", "Vasyan1", "12345qwerty", "NRTY", BigInteger.class, 3);
            getManager().save(user);
            User user2 = getManager().getUser("Vasyan1");
            assertNotNull("ID should not be null", user2.getId());
            assertEquals("Vasyan1", user2.getLogin());
            assertEquals("12345qwerty", user2.getPassword());
            assertEquals("NRTY", user2.getAbbr());
            //assertEquals(user2, user);
        } catch (Exception e){
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void testGetters() {
        try {
            String login = "Vasyan2";
            String password = "12345qwerty";
            String abbr = "NRTN";
            User user = (User)getObject("User", login, password, abbr, BigInteger.class, 3);
            assertEquals(login, user.getLogin());
            assertEquals(password, user.getPassword());
            assertEquals(abbr, user.getAbbr());
        } catch (Exception e){
            e.printStackTrace();
            assert false;
        }
    }


    @Test
    public void testGettersAndSetters() {
        try {
            String login = "Vasyan3";
            String password = "12345qwerty";
            String abbr = "NRTS";

            User user = (User)getObject("User");
            user.setLogin(login);
            user.setPassword(password);
            user.setAbbr(abbr);

            assertEquals(login, user.getLogin());
            assertEquals(password, user.getPassword());
            assertEquals(abbr, user.getAbbr());


            user = (User)getObject("User", login, String.class, String.class, BigInteger.class, 1);
            user.setPassword(password);
            user.setAbbr(abbr);
            getManager().save(user);
            User user2 = getManager().getUser(login);
            //assertEquals(user, user2);
            assertEquals(login, user2.getLogin());
            assertEquals(password, user2.getPassword());
            assertEquals(abbr, user2.getAbbr());
            assertEquals(user.getId(), user2.getId());

        } catch (Exception e){
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void testCollection() {
        Set<? extends Parameter> set = getManager().getAttribute(BigInteger.ONE).getParameters();
        System.out.println("=============== " + set.size() + " ===============");
        assertEquals(2, set.size());// TODO пока JDBC не может это обработать
    }


}
