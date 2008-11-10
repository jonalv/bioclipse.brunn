package net.bioclipse.brunn.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.domain.Plate;
import net.bioclipse.brunn.domain.PlateFunction;
import net.bioclipse.brunn.domain.Substance;
import net.bioclipse.brunn.domain.Well;
import net.bioclipse.brunn.domain.WellFunction;

public class FakePlateManager {
	
	public FakePlateManager() {
		
	}
	
	public Set<Plate> getAllPlates() {
		Set<Plate> plates = new HashSet<Plate>();
		plates.add(new Plate("p1","1"));
		plates.add(new Plate("p2","2"));
		return plates;
	}
	
	public Set<Well> getAllWells(String barcode) {
		Set<Well> wells = new HashSet<Well>();
		wells.add(new Well("w1","cancer","benign"));
		wells.add(new Well("w2","cancer","malign"));
		return wells;
	}
	
	public Set<Substance> getAllSubstances(String barcode, String wellName) {
		Set<Substance> substances = new HashSet<Substance>();
		substances.add(new Substance("s1", 1.));
		substances.add(new Substance("s2", 2.));
		return substances;
	}
	
	public Set<WellFunction> getAllWellFunctions(String barcode, String wellName) {
		Set<WellFunction> wellFunctions = new HashSet<WellFunction>();
		wellFunctions.add(new WellFunction("wf1",1.));
		wellFunctions.add(new WellFunction("wf2",2.));
		return wellFunctions;
	}
	
	public Set<PlateFunction> getAllPlateFunctions(String barcode) {
		Set<PlateFunction> plateFunctions = new HashSet<PlateFunction>();
		plateFunctions.add(new PlateFunction("pf1",1.));
		plateFunctions.add(new PlateFunction("pf2",2.));
		return plateFunctions;
	}
	
	public Set<String> getAllPlateBarcodes() {
		Set<String> barcodes = new HashSet<String>();
		barcodes.add("1");
		barcodes.add("2");
		return barcodes;
	}
	
}
