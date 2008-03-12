package net.bioclipse.brunn.tests;

import java.util.Set;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


/**
 * Some useful stuff that are mostly used during development.
 * 
 * @author jonathan
 *
 */
public abstract class Tools {

	/** 
	 * Performs a schema export to the database according to the mapping
	 *  files. This results in a new and totally clean database.
	 */
	public static void newCleanDatabase(){
		Configuration cfg = new Configuration();
		SchemaExport cfgExp;
		try {
			cfg.configure();
			cfgExp = new SchemaExport(cfg);
			cfgExp.create(true, true);
		} 
		catch (org.hibernate.HibernateException e3) {
			e3.printStackTrace();
		}
	}
	
	
	/**
	 * @param a one Set
	 * @param b another Set
	 * @return whether there exists an object x in a and an object y in b such that:
	 * x==y 
	 *   
	 */
	public static boolean inludesSame(Set a, Set b) {
		
		for (Object x : a) {
			
			for (Object y : b) {
	            
				if(x == y){
					return true;
				}
            }
	        
        }
		return false;
	}
}
