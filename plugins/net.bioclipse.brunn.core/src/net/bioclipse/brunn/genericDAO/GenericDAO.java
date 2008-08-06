package net.bioclipse.brunn.genericDAO;

import java.lang.reflect.Method;
import java.util.List;

import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.pojos.ILISObject;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * This class is the basis for the daos. It uses generics to be able to return
 * daos without need for casting. Spring uses it as base to build from when 
 * implementing the dao interfaces. It also contains some methods for running 
 * the correspondingly named Hibernate query when methods defined in the DAO 
 * interface are called.
 * 
 * 
 * @author jonathan
 *
 * @param <T> The type of the persistent class the dao object should work with.
 */
public class GenericDAO<T> extends HibernateDaoSupport 
                           implements IGenericDAO<T>, FinderExecutor {

	private Class<T> type;

	public GenericDAO(Class<T> type){
		this.type = type;
	}

	public void delete(ILISObject obj) {
		obj.delete();
		obj = (ILISObject) getSession().merge(obj);
		getSession().saveOrUpdate(obj);
		LazyLoadingSessionHolder.getInstance().evict(
				LazyLoadingSessionHolder.getInstance().get(type, obj.getId()));
	}

	@SuppressWarnings("unchecked")
    public T getById(long id) {
		
		T object = 	(T) LazyLoadingSessionHolder.getInstance()
                                                .get(type, id);
		LazyLoadingSessionHolder.getInstance().setReadOnly(object, true);
		return object;
	}

	public void save(T instance) {
		getSession().saveOrUpdate(instance);
		LazyLoadingSessionHolder.getInstance().evict(instance);
	}

	@SuppressWarnings("unchecked")
    public List<T> executeFinder(Method method, final Object[] queryArgs) {

		String queryName = queryNameFromMethod(method);
		Query namedQuery = LazyLoadingSessionHolder.getInstance().getNamedQuery(queryName);

		for( int i = 0 ; i < (queryArgs == null? 0 : queryArgs.length); i++ ) {

			Object arg = queryArgs[i];
			namedQuery.setParameter(i, arg);
		}
		List<T> list = namedQuery.list();
//		for( Object obj : list) {
//			LazyLoadingSessionHolder.getInstance().setReadOnly(obj, true);
//		}
		return (List<T>)list;
	}

	public String queryNameFromMethod(Method finderMethod) {
		return type.getSimpleName() + "." + finderMethod.getName();
	}

	public void update(T instance) {
	    getSession().update(instance);
    }
	
	@SuppressWarnings("unchecked")
    public T merge(T instance) {
		return (T) getSession().merge(instance);
	}

	@SuppressWarnings("unchecked")
    public <T2> T2 mergeObject(T2 o) {
	    return (T2) getSession().merge(o);
    }

	public void evict(T o) {
		getSession().evict(o);
    }
}
