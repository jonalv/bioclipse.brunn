package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.User;

/**
 * Definition of the UserDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IUserDAO extends IGenericDAO<User> {

	public List<User> findAll();
	public List<User> findByName(String name);
	
}