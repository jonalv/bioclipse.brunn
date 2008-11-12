package net.bioclipse.brunn.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.pojos.AbstractSample;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.PatientSample;
import net.bioclipse.brunn.provider.FakePlateManager;
import net.bioclipse.expression.Calculator;

public class Creator {
	
	private FakePlateManager plateManager = new FakePlateManager();
	private IPlateManager pm;
	private Set<Plate> plates = new HashSet<Plate>();
	
	public Creator() {
		
	}
	
	public void generalStyle() {
		Calculator calculator = new Calculator();
		pm = (IPlateManager) Springcontact.getBean("plateManager");
		Collection<net.bioclipse.brunn.pojos.Plate> pojoPlates = pm.getAllPlates();
		for(net.bioclipse.brunn.pojos.Plate oldPlate :pojoPlates) {
			String barcode = oldPlate.getBarcode();
			Set<net.bioclipse.brunn.pojos.Well> oldWells = oldPlate.getWells();
			Set<Well> wells = new HashSet<Well>();
			for(net.bioclipse.brunn.pojos.Well oldWell : oldWells) {
				Set<AbstractSample> samples = oldWell.getSampleContainer().getSamples();
				//oldWell.getSampleMarkers().iterator();
				Set<Substance> substances = new HashSet<Substance>();
				String cellType = "";
				String cellName = "";
				for(AbstractSample sample : samples) {
					if(sample instanceof DrugSample) {
						Substance substance = new Substance(sample.getName(), ((DrugSample) sample).getConcentration());
						substances.add(substance);
					}
					else if(sample instanceof CellSample) {
						cellType = "cellOrigin";
						cellName = sample.getName();
					}
					else if(sample instanceof PatientSample) {
						cellType = "patientOrigin";
						cellName = sample.getName();
					}
				}
				Set<net.bioclipse.brunn.pojos.WellFunction> oldWellFunctions = oldWell.getWellFunctions();
				Set<WellFunction> wellFunctions = new HashSet<WellFunction>();
				for(net.bioclipse.brunn.pojos.WellFunction oldWellFunction : oldWellFunctions) {
					WellFunction wellFunction = new WellFunction(oldWellFunction.getName(), calculator.safeValueOf(oldWellFunction.getExpression()));
					wellFunctions.add(wellFunction);
				}
				//((DrugSample) oldWell.getSampleContainer().getSamples().toArray()[0]).getDrugOrigin().getName()
				wells.add(new Well(oldWell.getName(), cellType, cellName, wellFunctions, substances));
			}
			Set<net.bioclipse.brunn.pojos.PlateFunction> oldPlateFunctions = oldPlate.getPlateFunctions();
			Set<PlateFunction> plateFunctions = new HashSet<PlateFunction>();
			for(net.bioclipse.brunn.pojos.PlateFunction oldPlateFunction : oldPlateFunctions) {
				PlateFunction plateFunction = new PlateFunction(oldPlateFunction.getName(), calculator.safeValueOf(oldPlateFunction.getExpression()));
				plateFunctions.add(plateFunction);
			}
			plates.add(new Plate(oldPlate.getName(), oldPlate.getBarcode(), plateFunctions, wells));
		}
/*		
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
		}*/
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
