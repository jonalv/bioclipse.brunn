package net.bioclipse.brunn.business.origin;

import net.bioclipse.brunn.genericDAO.ICellOriginDAO;
import net.bioclipse.brunn.genericDAO.IDrugOriginDAO;
import net.bioclipse.brunn.genericDAO.IFolderDAO;
import net.bioclipse.brunn.genericDAO.IPatientOriginDAO;
import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;

public abstract class AbstractDAOBasedOriginManager implements
        IOriginManager {

	protected ICellOriginDAO    cellOriginDAO;
	protected IDrugOriginDAO    drugOriginDAO;
	protected IUniqueFolderDAO  uniqueFolderDAO;
	protected IFolderDAO        folderDAO;
	protected IPatientOriginDAO patientOriginDAO;
	
	public void setCellOriginDAO(ICellOriginDAO cellOriginDAO) {
    	this.cellOriginDAO = cellOriginDAO;
    }

	public void setDrugOriginDAO(IDrugOriginDAO drugOriginDAO) {
    	this.drugOriginDAO = drugOriginDAO;
    }

	public void setUniqueFolderDAO(IUniqueFolderDAO uniqueFolderDAO) {
    	this.uniqueFolderDAO = uniqueFolderDAO;
    }

	public void setFolderDAO(IFolderDAO folderDAO) {
    	this.folderDAO = folderDAO;
    }

	public void setPatientOriginDAO(IPatientOriginDAO patientOriginDAO) {
    	this.patientOriginDAO = patientOriginDAO;
    }
}
