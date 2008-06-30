package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.AuditLog;

/**
 * Definition of the AuditLogDAO specific methods.
 * 
 * @author jonathan
 */
public interface IAuditLogDAO extends IGenericDAO<AuditLog>{
	public List<AuditLog> findAll();
}
