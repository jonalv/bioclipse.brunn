package net.bioclipse.brunn.business.domain;

import java.util.HashSet;
import java.util.Set;
import net.bioclipse.brunn.results.PlateResults;

public class Plate {
	
	private String name;
	private String barcode;
	private Set<PlateFunction> plateFunctions;
	private Set<Well> wells;

	
	
	public Plate() {
		name = "";
		barcode = "";
		plateFunctions = new HashSet<PlateFunction>();
		wells = new HashSet<Well>();
	}
	
	public Plate(net.bioclipse.brunn.pojos.Plate pojoPlate) {
		this.name = pojoPlate.getName();
		this.barcode = pojoPlate.getBarcode();

		plateFunctions = new HashSet<PlateFunction>();
		wells = new HashSet<Well>();
		
		PlateResults interpreter = new PlateResults(pojoPlate, null);
	
		for (net.bioclipse.brunn.pojos.PlateFunction pf : pojoPlate.getPlateFunctions()) {
			this.addFunction(new PlateFunction(pf.getName(), 
					interpreter.getValue(pf.getName()),
					pf.getExpression(),
					pf.getGoodFrom(), pf.getGoodTo(),
					pf.getHasSpecifiedFromValue(),pf.getHasSpecifiedToValue()
					));
		}
		
		for (net.bioclipse.brunn.pojos.Well pojoW : pojoPlate.getWells()) {
			Well nw = new Well();
			nw.setName(pojoW.getName());
			nw.setOutlier(pojoW.isOutlier());
			
			
			for (net.bioclipse.brunn.pojos.AbstractSample c : pojoW.getSampleContainer().getSamples()) {
				if (c instanceof net.bioclipse.brunn.pojos.DrugSample) {
					nw.addSubstance(new Substance(
					c.getName()
					, ((net.bioclipse.brunn.pojos.DrugSample) c).getConcentration()
					, ((net.bioclipse.brunn.pojos.DrugSample) c).getConcUnit().toString()
					));
				}
				if (c instanceof net.bioclipse.brunn.pojos.CellSample) {
					nw.setCellName(c.getName());
				}
			}
			
			for (net.bioclipse.brunn.pojos.WellFunction wf : pojoW.getWellFunctions()) {
				nw.addWellFunction(new WellFunction(
						wf.getName(),
						interpreter.getValue(pojoW.getCol(),pojoW.getRow(),wf.getName()),
						wf.getExpression()
						));
			}
			
			this.addWell(nw);
			
		}
	}
	public Plate(String name, String barcode) {
		this.name = name;
		this.barcode = barcode;
		plateFunctions = new HashSet<PlateFunction>();
		wells = new HashSet<Well>();
	}
	
	public Plate(String name, String barcode, Set<PlateFunction> plateFunctions, Set<Well> wells) {
		this.name = name;
		this.barcode = barcode;
		this.plateFunctions = plateFunctions;
		this.wells = wells;
	}
	
	public void addFunction(PlateFunction plateFunction) {
		plateFunctions.add(plateFunction);
	}
	
	public void addWell(Well well) {
		wells.add(well);
	}
	
	public String getName() {
		return name;
	}
	
	public String getBarcode() {
		return barcode;
	}
	
	public Set<PlateFunction> getPlateFunctions() {
		return plateFunctions;
	}
	
	public Set<Well> getWells() {
		return wells;
	}
	
	public Set<WellFunction> getWellFunctions() {
		Set<WellFunction> wellFunctions = new HashSet<WellFunction>();
		for(Well well : wells) {
			wellFunctions.addAll(well.getWellFunctions());
		}
		return wellFunctions;
	}
	
	public Set<Substance> getSubstances() {
		Set<Substance> substances = new HashSet<Substance>();
		for(Well well : wells) {
			substances.addAll(well.getSubstances());
		}
		return substances;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public void setPlateFunctions(Set<PlateFunction> plateFunctions) {
		this.plateFunctions = plateFunctions;
	}
	
	public void setWells(Set<Well> wells) {
		this.wells = wells;
	}
	
	public void print() {
		System.out.println("\nPlate: "+name+", barcode: "+barcode);
		for(PlateFunction pf : plateFunctions) {
			pf.print();
		}
		for(Well w : wells) {
			w.print();
		}
	}
	
	public String toString() {
		String result = "";
		result+="\nPlate: "+name+", barcode: "+barcode+"\n";
		for(PlateFunction pf : plateFunctions) {
			result+=pf.toString()+"\n";
		}
		for(Well w : wells) {
			result+=w.toString();
		}
		return result;
	}

}
