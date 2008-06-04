package net.bioclipse.brunn.ui.transferTypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.folder.IFolderManager;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * @author jonalv
 */
public class BrunnTransfer extends ByteArrayTransfer {

	private static final int DRUGORIGIN    = 0;
	private static final int FOLDER        = 1;
	private static final int PLATETYPE     = 2;
	private static final int PLATELAYOUT   = 3;
	private static final int MASTERPLATE   = 4;
	private static final int PLATE         = 5;
	private static final int CELLORIGIN    = 6;
	private static final int PATIENTORIGIN = 7;
	
	private static final String[] TYPENAMES = { "DrugOrigin", 
		                                        "Folder",
		                                        "PlateType", 
		                                        "PlateLayout", 
		                                        "MasterPlate", 
		                                        "Plate", 
		                                        "CellOrigin", 
		                                        "PatientOrigin", };
	private static final int[] TYPEIDS;

	private static BrunnTransfer _instance = new BrunnTransfer();
	
	static {
		int[] typeIds = new int[TYPENAMES.length];
		for (int i = 0; i < TYPENAMES.length; i++) {
			typeIds[i] = registerType(TYPENAMES[i]);
		}
		TYPEIDS = typeIds;
	}
	
	private BrunnTransfer(){
		super();
	}
	
	public static BrunnTransfer getInstance() {
		return _instance;
	}
	public void javaToNative (Object object, TransferData transferData) {
		
	 	if (object == null ) return;
	 	
	 	if (isSupportedType(transferData)) {

	 		Object[] objects = (Object[])object;
	 		ILISObject[] lisObjects = new ILISObject[objects.length];
	 		System.arraycopy(objects, 0, lisObjects, 0, objects.length);
	 		try {
	 			ByteArrayOutputStream out = new ByteArrayOutputStream();
	 			DataOutputStream writeOut = new DataOutputStream(out);
	 			for (int i = 0, length = objects.length; i < length;  i++) {
	 				writeOut.writeInt(  getTypeInt(lisObjects[i]) );
	 				writeOut.writeLong( lisObjects[i].getId()     );
	 			}
	 			byte[] buffer = out.toByteArray();
	 			writeOut.close();
	 
	 			super.javaToNative(buffer, transferData);
	 			
	 		} catch (IOException e) {
	 			e.printStackTrace();
			}
		}
	}
	
	private int getTypeInt(ILISObject object) {
		 
		if ( object instanceof DrugOrigin) {
			return DRUGORIGIN;
		}
		if ( object instanceof Folder ) {
			return FOLDER;
		}
		if ( object instanceof PlateType ) {
			return PLATETYPE;
		}
		if ( object instanceof PlateLayout ) {
			return PLATELAYOUT;
		}
		if ( object instanceof MasterPlate ) {
			return MASTERPLATE;
		}
		if ( object instanceof Plate ) {
			return PLATE;
		}
		if ( object instanceof CellOrigin ) {
			return CELLORIGIN;
		}
		if ( object instanceof PatientOrigin ) {
			return PATIENTORIGIN;
		}
		throw new IllegalArgumentException(
				"BrunnTransfer can't handle " 
				+ object.getClass().getSimpleName() );
	}

	public Object nativeToJava(TransferData transferData) {
		 
	 	if (isSupportedType(transferData)) {
	 		
	 		byte[] buffer = (byte[])super.nativeToJava(transferData);
	 		if (buffer == null) return null;
	 		
	 		ILISObject[] myData = new ILISObject[0];
	 		try {
	 			ByteArrayInputStream in = new ByteArrayInputStream(buffer);
	 			DataInputStream readIn = new DataInputStream(in);
	 			
	 			List<ILISObject> lisObjects = new LinkedList<ILISObject>();
	 			while(readIn.available() > 1) {
	 				switch( readIn.readInt() ) {
					case DRUGORIGIN:
						IOriginManager originManager 
							= (IOriginManager) Springcontact
							                   .getBean("originManager");
		 				DrugOrigin drugOrigin 
		 					= originManager.getDrugOrigin( readIn.readLong() );
		 				lisObjects.add(drugOrigin);
						break;
					case FOLDER:
						IFolderManager folderManager
							= (IFolderManager) Springcontact
							                   .getBean("folderManager");
						Folder folder
							= folderManager.getFolder( readIn.readLong() );
						lisObjects.add(folder);
						break;
					case PLATETYPE:
						IPlateLayoutManager plateLayoutManager 
							= (IPlateLayoutManager) Springcontact
							                     .getBean("plateLayoutManager");
						PlateType plateType 
							= plateLayoutManager.getPlateType( 
									readIn.readLong() );
						lisObjects.add(plateType);
						break;
					case PLATELAYOUT:
						IPlateLayoutManager plateLayoutManager2
							= (IPlateLayoutManager) Springcontact
							                     .getBean("plateLayoutManager");
						PlateLayout plateLayout 
							= plateLayoutManager2.getPlateLayout(
									readIn.readLong() ); 
						lisObjects.add(plateLayout);
						break;
					case MASTERPLATE:
						IPlateManager plateManager
							= (IPlateManager) Springcontact
							                     .getBean("plateManager");
						MasterPlate masterPlate = plateManager
						              .getMasterPlate( readIn.readLong() ); 
						lisObjects.add(masterPlate);
						break;
					case PLATE:
						IPlateManager plateManager2
							= (IPlateManager) Springcontact
							                  .getBean("plateManager");
						Plate plate = plateManager2
						              .getPlate( readIn.readLong() ); 
						lisObjects.add(plate);
						break;
					case CELLORIGIN:
						IOriginManager originManager2 
							= (IOriginManager) Springcontact
							                   .getBean("originManager");
						CellOrigin cellOrigin = originManager2
						                        .getCellOrigin( 
						                        		readIn.readLong() );
						lisObjects.add(cellOrigin);
						break;
					case PATIENTORIGIN:
						IOriginManager originManager3
							= (IOriginManager) Springcontact
			                                   .getBean("originManager");
						PatientOrigin patientOrigin = originManager3
						                              .getPatientOrigin(
						                                   readIn.readLong() );
						lisObjects.add(patientOrigin);
						break;
					default:
						break;
					}
	 			}
	 			myData = lisObjects.toArray(myData);
	 			
	 			readIn.close();
	 		} catch (IOException ex) {
	 			return null;
	 		}
	 		return myData;
	 	}
	 
	 	return null;
	 }
	 
	@Override
	protected int[] getTypeIds() {
		return TYPEIDS;
	}

	@Override
	protected String[] getTypeNames() {
		return TYPENAMES;
	}
}
