package net.bioclipse.brunn.ui.transferTypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

public class CompoundIdTransfer extends ByteArrayTransfer {

	private static final String TYPENAME = "Compound";
	private static final int TYPEID = registerType(TYPENAME);
	private static CompoundIdTransfer _instance = new CompoundIdTransfer();
	
	private CompoundIdTransfer(){
		super();
	}
	
	public static CompoundIdTransfer getInstance() {
		return _instance;
	}
	public void javaToNative (Object object, TransferData transferData) {
		
	 	if (object == null ) return;
	 	
	 	if (isSupportedType(transferData)) {

	 		Object[] objects = (Object[])object;
	 		DrugOrigin[] compounds = new DrugOrigin[objects.length];
	 		System.arraycopy(objects, 0, compounds, 0, objects.length);

	 		try {
	 			// write data to a byte array and then ask super to convert to pMedium
	 			ByteArrayOutputStream out = new ByteArrayOutputStream();
	 			DataOutputStream writeOut = new DataOutputStream(out);
	 			for (int i = 0, length = compounds.length; i < length;  i++) {
	 				writeOut.writeLong( compounds[i].getId() );
	 			}
	 			byte[] buffer = out.toByteArray();
	 			writeOut.close();
	 
	 			super.javaToNative(buffer, transferData);
	 			
	 		} catch (IOException e) {
	 			e.printStackTrace();
	 		}
	 	}
	 }
	 public Object nativeToJava(TransferData transferData){
		 
	 	if (isSupportedType(transferData)) {
	 		
	 		byte[] buffer = (byte[])super.nativeToJava(transferData);
	 		if (buffer == null) return null;
	 		
	 		DrugOrigin[] myData = new DrugOrigin[0];
	 		try {
	 			ByteArrayInputStream in = new ByteArrayInputStream(buffer);
	 			DataInputStream readIn = new DataInputStream(in);
	 			
	 			List<DrugOrigin> drugOrigins = new LinkedList<DrugOrigin>();
	 			while(readIn.available() > 1) {
	 				IOriginManager originManager = (IOriginManager) Springcontact.getBean("originManager");
	 				DrugOrigin drugOrigin = originManager.getDrugOrigin(readIn.readLong());
	 				drugOrigins.add(drugOrigin);
	 			}
	 			myData = drugOrigins.toArray(myData);
	 			
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
		return new int[] {TYPEID};
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] {TYPENAME};
	}
}
