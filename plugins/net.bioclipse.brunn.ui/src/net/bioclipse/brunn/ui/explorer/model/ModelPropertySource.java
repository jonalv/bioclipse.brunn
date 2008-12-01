package net.bioclipse.brunn.ui.explorer.model;

import java.util.ArrayList;
import java.util.HashMap;

import net.bioclipse.brunn.pojos.AbstractAuditableObject;
import net.bioclipse.brunn.pojos.AbstractBaseObject;
import net.bioclipse.brunn.pojos.AbstractBasePlate;
import net.bioclipse.brunn.pojos.AbstractSample;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.ui.explorer.model.folders.Resources;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource2;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;


public class ModelPropertySource implements IPropertySource2 {

	//TODO: What should these Strings be? Any conventions?
	private static final String 
		PROPERTY_NAME                  = "name", 
		PROPERTY_CREATOR               = "creator",
		PROPERTY_TYPE                  = "type",
		PROPERTY_PLATETYPECOLS         = "plateTypeCols",
		PROPERTY_PLATETYPEROWS         = "plateTypeRows",
		PROPERTY_ABSTRACTBASEPLATECOLS = "abstractBasePlateCols",
		PROPERTY_ABSTRACTBASEPLATEROWS = "abstractBasePlateRows",
		PROPERTY_MASTERPLATELOCKED     = "masterPlateLocked", 
		PROPERTY_MOLECULARWEIGHT       = "molecularWeight",
		PROPERTY_CURATED               = "curated",
		PROPERTY_SEEDEDCELL            = "seededCell",
		PROPERTY_BARCODE               = "barcode", 
		PROPERTY_MASTERPLATEPLATESLEFT = "platesLeft",
		PROPERTY_CREATIONDATE          = "creationDate";
	
	private Model model;

	private ArrayList<IPropertyDescriptor> properties;

	
	public ModelPropertySource() {
	}

	public ModelPropertySource(Model model) {
		this.model = model;
	}

	public boolean isPropertyResettable(Object id) {
		return false;
	}

	public boolean isPropertySet(Object id) {
		return true;
	}

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {

		if(properties == null) {
			
			properties = new ArrayList<IPropertyDescriptor>();
			
			/*
			 * General properties
			 */
			
			PropertyDescriptor nameDescriptor = new TextPropertyDescriptor(PROPERTY_NAME, "Name");
            nameDescriptor.setCategory("General");
            properties.add(nameDescriptor);
            
            PropertyDescriptor creatorDescriptor = new TextPropertyDescriptor(PROPERTY_CREATOR, "Creator");
            creatorDescriptor.setCategory("General");
            properties.add(creatorDescriptor);
            
            PropertyDescriptor typeDescriptor = new TextPropertyDescriptor(PROPERTY_TYPE, "Type");
            typeDescriptor.setCategory("General");
            properties.add(typeDescriptor);
            
            PropertyDescriptor creationDateDescriptor = new TextPropertyDescriptor(PROPERTY_CREATIONDATE, "Creation date");
            creationDateDescriptor.setCategory("General");
            properties.add(creationDateDescriptor);
            
            /*
             * Specific properties
             */
            if(model instanceof Resources) {
            	
            }
            else {
	            if(model.getPOJO() instanceof PlateType) {
	            	
	            	PropertyDescriptor plateTypeColsDescriptor = 
	            		new TextPropertyDescriptor(PROPERTY_PLATETYPECOLS, "Number of columns");
	            	plateTypeColsDescriptor.setCategory("Specific");
	                properties.add(plateTypeColsDescriptor);
	                
	                PropertyDescriptor plateTypeRowsDescriptor = 
	            		new TextPropertyDescriptor(PROPERTY_PLATETYPEROWS, "Number of rows");
	            	plateTypeRowsDescriptor.setCategory("Specific");
	                properties.add(plateTypeRowsDescriptor);
	            }
	            
	            if(model.getPOJO() instanceof AbstractBasePlate) {
	            	
	            	PropertyDescriptor abstractBasePlateColsDescriptor = 
	            		new TextPropertyDescriptor(PROPERTY_ABSTRACTBASEPLATECOLS, "Number of columns");
	            	abstractBasePlateColsDescriptor.setCategory("Specific");
	                properties.add(abstractBasePlateColsDescriptor);
	                
	                PropertyDescriptor abstractBasePlateRowsDescriptor = 
	            		new TextPropertyDescriptor(PROPERTY_ABSTRACTBASEPLATEROWS, "Number of rows");
	                abstractBasePlateRowsDescriptor.setCategory("Specific");
	                properties.add(abstractBasePlateRowsDescriptor);
	            }
	            
	            if(model.getPOJO() instanceof MasterPlate) {
	            	
	            	PropertyDescriptor masterPlateLockedDescriptor = 
	            		new TextPropertyDescriptor(PROPERTY_MASTERPLATELOCKED, "Locked");
	            	masterPlateLockedDescriptor.setCategory("Specific");
	                properties.add(masterPlateLockedDescriptor);
	                
	                PropertyDescriptor masterPlatePlatesLeft = 
	                	new TextPropertyDescriptor(PROPERTY_MASTERPLATEPLATESLEFT, "Plates left:");
	                masterPlatePlatesLeft.setCategory("Specific");
	                properties.add(masterPlatePlatesLeft);
	                
	            }
	            
	            if(model.getPOJO() instanceof DrugOrigin) {
	            	
	            	PropertyDescriptor molecularWeightDescriptor = 
	            		new TextPropertyDescriptor(PROPERTY_MOLECULARWEIGHT, "Molecular weight");
	            	molecularWeightDescriptor.setCategory("Specific");
	                properties.add(molecularWeightDescriptor);
	            }
	            
	            if(model.getPOJO() instanceof Plate) {
	            	
	            	PropertyDescriptor curatedDescriptor = 
	            		new TextPropertyDescriptor(PROPERTY_CURATED, "Curated");
	            	curatedDescriptor.setCategory("Specific");
	                properties.add(curatedDescriptor);
	                
	                PropertyDescriptor seededCellDescriptor = 
	            		new TextPropertyDescriptor(PROPERTY_SEEDEDCELL, "Seeded cell type");
	            	seededCellDescriptor.setCategory("Specific");
	                properties.add(seededCellDescriptor);
	                
	                PropertyDescriptor barcodeDescriptor = 
	            		new TextPropertyDescriptor(PROPERTY_BARCODE, "barcode");
	            	barcodeDescriptor.setCategory("Specific");
	                properties.add(barcodeDescriptor);
	            }
            }
		}

		return properties.toArray(new IPropertyDescriptor[0]);
	}

	public Object getPropertyValue(Object id) {

		if( !(model instanceof Resources) ) {
			if( id.equals(PROPERTY_NAME) ) {
				return model.getName();
			}
			if( id.equals(PROPERTY_CREATOR) ) {
				return model.getPOJO().getCreator().getName();
			}
			if( id.equals(PROPERTY_TYPE) ) {
				return model.getPOJO().getClass().getSimpleName();
			}
			if( id.equals(PROPERTY_PLATETYPECOLS) ) {
				return ( (PlateType)model.getPOJO() ).getCols() + "";
			}
			if( id.equals(PROPERTY_PLATETYPEROWS) ) {
				return ( (PlateType)model.getPOJO() ).getRows() + "";
			}
			if( id.equals(PROPERTY_ABSTRACTBASEPLATECOLS) ) {
				return ( (AbstractBasePlate)model.getPOJO() ).getCols() + "";
			}
			if( id.equals(PROPERTY_ABSTRACTBASEPLATEROWS) ) {
				return ( (AbstractBasePlate)model.getPOJO() ).getRows() + "";
			}
			if( id.equals(PROPERTY_MASTERPLATELOCKED) ) {
				return ( (MasterPlate)model.getPOJO() ).isLocked() + "";
			}
			if( id.equals(PROPERTY_MOLECULARWEIGHT) ) {
				return ( (DrugOrigin)model.getPOJO() ).getMolecularWeight() + "";
			}
			if( id.equals(PROPERTY_CURATED) ) {
				return ( (Plate)model.getPOJO() ).isCurated() + "";
			}
			if( id.equals(PROPERTY_SEEDEDCELL) ) {
//				Well well = (Well)( (Plate)model.getPOJO() ).getWells().toArray()[0];
//				for(AbstractSample s : well.getSampleContainer().getSamples() ){
//					if(s instanceof CellSample) {
//						return ( (CellSample)s ).getCellOrigin().getName();
//					}
//				}
				//TODO: Do this with some sql question instead and load it when loading the plate
			}
			if( id.equals(PROPERTY_MASTERPLATEPLATESLEFT) ) {
				MasterPlate masterPlate = (MasterPlate)model.getPOJO();
				return masterPlate.getPlatesLeft();
			}
			
			if( id.equals(PROPERTY_BARCODE) ) {
				return ( (Plate)model.getPOJO() ).getBarcode() + "";
			}
			
			if( id.equals(PROPERTY_CREATIONDATE) ) {
				AbstractAuditableObject o = (AbstractAuditableObject)model.getPOJO();
				for( AuditLog a : o.getAuditLogs() ) {
					if(AuditType.CREATE_EVENT == a.getAuditType()) {
						return a.getTimeStamp();
					}
				}
			}
		}
		return null;
	}

	public void resetPropertyValue(Object id) {
	}

	public void setPropertyValue(Object id, Object value) {
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
}
