package org.aksoftware.edu.udm.jpa;

import org.aksoftware.edu.udm.*;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;


public enum UdmManagerJPA implements UdmManager{
    MySQL,
    Derby;

    private volatile EntityManagerFactory emf;

    private synchronized EntityManagerFactory getEmf() {
        if (emf == null){
            emf = Persistence.createEntityManagerFactory(this.name());
        }
        return emf;
    }

    public void save(UdEntity... entities){
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for(UdEntity object: entities)
            em.persist(object);
        tx.commit();
        em.close();
    }

    public synchronized void close(){
        emf.close();
        emf = null;
    }

    // ====== Служебные методы ======
    // TODO [Никита]: что будет, если передать в качестве параметров в findObject один null, пару? Проверить.

    private Object findObjectByID(Class clazz, BigInteger id){
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Object object = em.find(clazz, id);
        tx.commit();
        em.close();
        return object;
    }

    private Object findObject(String queryName, Object... params){
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNamedQuery(queryName);
        for(int i = 0; i < params.length; i++)
            query.setParameter(i+1, params[i]);
        Object object = query.getSingleResult();
        tx.commit();
        em.close();
        return object;
    }

    private List<?> findObjects(String queryName, Object... params){
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNamedQuery(queryName);
        for(int i = 0; i < params.length; i++)
            query.setParameter(i+1, params[i]);
        List<?> list = query.getResultList();
        tx.commit();
        em.close();
        return list;
    }

    // ==============================


    public User getUser(String login) {
        return (User)findObject("user_by_login", login);
    }

    public User getUser(BigInteger id) {
        return (User)findObjectByID(UserJPA.class, id);
    }

    public UdAttribute getAttribute(BigInteger id) {
        return (UdAttribute)findObjectByID(UdAttributeJPA.class, id);
    }

    public UdAttribute getAttribute(String abbreviation) {
        return (UdAttribute)findObject("attr_by_abbr", abbreviation);
    }

    public UdAttributeType getAttributeType(BigInteger id) {
        return (UdAttributeType)findObjectByID(UdAttributeTypeJPA.class, id);
    }

    public UdAttributeType getAttributeType(String abbreviation) {
        return (UdAttributeType)findObject("attr_type_by_abbr", abbreviation);
    }

    public UdObjectType getObjectType(BigInteger id) {
        return (UdObjectType)findObjectByID(UdObjectTypeJPA.class, id);
    }

    public UdObjectType getObjectType(String abbreviation) {
        return (UdObjectType)findObject("obj_type_by_abbr", abbreviation);
    }

    public UdObject getObject(BigInteger id) {
        return (UdObject)findObjectByID(UdObjectJPA.class, id);
    }
}
