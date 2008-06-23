package net.bioclipse.brunn.business;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.pojos.UniqueFolder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class LazyLoadingSessionHolder {

	private static Session session;
	
	private LazyLoadingSessionHolder() {};
	
	public static Session getInstance() {
		if(session == null) {
			SessionFactory sf = (SessionFactory) Springcontact.getBean("niceSessionFactory");
			session = sf.openSession();
		}
		return session;
	}
}
