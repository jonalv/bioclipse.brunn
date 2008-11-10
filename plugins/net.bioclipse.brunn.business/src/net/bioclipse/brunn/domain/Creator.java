package net.bioclipse.brunn.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.PlateManager;
import net.bioclipse.brunn.provider.FakePlateManager;

public class Creator {
	
	private FakePlateManager plateManager = new FakePlateManager();
	//private PlateManager pm = (PlateManager) Springcontact.getBean("plateManager");
	private Set<Plate> plates = new HashSet<Plate>();
	
	public Creator() {
		
	}
	
	public void generalStyle() {
		Set<Plate> oldPlates = plateManager.getAllPlates();
		for(Plate oldPlate : oldPlates) {
			String barcode = oldPlate.getBarcode();
			Set<Well> oldWells = plateManager.getAllWells(barcode);
			Set<Well> wells = new HashSet<Well>();
			for(Well oldWell : oldWells) {
				Set<Substance> oldSubstances = plateManager.getAllSubstances(barcode,oldWell.getName());
				Set<Substance> substances = new HashSet<Substance>();
				for(Substance oldSubstance : oldSubstances) {
					Substance substance = new Substance(oldSubstance.getName(), oldSubstance.getConcentration());
					substances.add(substance);
				}
				Set<WellFunction> oldWellFunctions = plateManager.getAllWellFunctions(barcode, oldWell.getName());
				Set<WellFunction> wellFunctions = new HashSet<WellFunction>();
				for(WellFunction oldWellFunction : oldWellFunctions) {
					WellFunction wellFunction = new WellFunction(oldWellFunction.getName(), oldWellFunction.getValue());
					wellFunctions.add(wellFunction);
				}
				wells.add(new Well(oldWell.getName(), oldWell.getCellType(), oldWell.getCellName(), wellFunctions, substances));
			}
			Set<PlateFunction> oldPlateFunctions = plateManager.getAllPlateFunctions(barcode);
			Set<PlateFunction> plateFunctions = new HashSet<PlateFunction>();
			for(PlateFunction oldPlateFunction : oldPlateFunctions) {
				PlateFunction plateFunction = new PlateFunction(oldPlateFunction.getName(), oldPlateFunction.getValue());
				plateFunctions.add(plateFunction);
			}
			plates.add(new Plate("p"+barcode, barcode, plateFunctions, wells));
		}
		for(Plate plate : plates) {
			plate.print();
		}
	}
	
	public String getPlateByBarcode(String barcode) {
		for(Plate plate : plates) {
			if(plate.getBarcode().equals(barcode)) {
				return plate.toString();
			}
		}
		return "No plate with barcode "+barcode;
	}
	
	public Set<String> getAllPlateBarcodes() {
		return plateManager.getAllPlateBarcodes();
	}
	
	/*public void createPlates() {
		Set<String> barcodes = plateManager.getAllPlateBarcodes();
		for(String barcode : barcodes) {
			String plateName = plateManager.getPlateNameByBarcode(barcode);
			Well well = plateManager.getWellOnPlate("1",barcode);
			
			Plate plate = new Plate(plateName, barcode);
			
		}
	}*/

}
