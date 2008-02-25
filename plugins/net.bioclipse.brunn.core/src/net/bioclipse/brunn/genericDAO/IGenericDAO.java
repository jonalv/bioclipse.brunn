package net.bioclipse.brunn.genericDAO;

import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.pojos.User;

import org.hibernate.SessionFactory;

/**
 * Definition of the basic dao methods that all daos should have.
 * @author  jonathan
 * @param  < T  >  The type of the persistent object the dao should work with.
 */
public interface IGenericDAO<T> {

    /** persist the object */
    public void save(T instance);
    
    /**
     * reattach the object to the session.
     * See also Hibernate documentation for update 
     */
    public void update(T instance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    public T getById(long id);

    /** Mark an object as deleted in the persistent storage in the database */
    public void delete(ILISObject obj);
    
    public SessionFactory getSessionFactory();
    
    /**
     * See Hibernate documentation for merge
     */
    public T merge(T instance);
    
    public <T2> T2 mergeObject(T2 o);
    
    public void evict(T o);
}