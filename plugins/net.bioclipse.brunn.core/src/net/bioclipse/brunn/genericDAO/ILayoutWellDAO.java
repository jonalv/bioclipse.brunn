package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.LayoutWell;

public interface ILayoutWellDAO extends IGenericDAO<LayoutWell>{

		public List<LayoutWell> findAll();
}
