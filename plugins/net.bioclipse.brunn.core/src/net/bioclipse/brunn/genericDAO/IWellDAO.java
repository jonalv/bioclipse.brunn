package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.Well;

public interface IWellDAO extends IGenericDAO<Well> {

	public List<Well> findAll();
}
