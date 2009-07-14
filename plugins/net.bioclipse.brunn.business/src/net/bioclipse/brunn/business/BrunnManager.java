/*******************************************************************************
 * Copyright (c) 2008 The Bioclipse Project and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ola Spjuth
 *     Jonathan Alvarsson
 *     Stefan Kuhn
 *     
 ******************************************************************************/
package net.bioclipse.brunn.business;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.domain.Plate;
import net.bioclipse.brunn.domain.PlateFunction;
import net.bioclipse.brunn.domain.Substance;
import net.bioclipse.brunn.domain.Well;
import net.bioclipse.brunn.domain.WellFunction;
import net.bioclipse.brunn.pojos.AbstractSample;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.PatientSample;
import net.bioclipse.brunn.results.PlateResults;

import org.apache.log4j.Logger;

/**
 * The manager class for Brunn. Contains Brunn related methods.
 * 
 * @author jonas
 * 
 */
public class BrunnManager implements IBrunnManager {

	private static final Logger logger = Logger.getLogger(BrunnManager.class);
	IPlateManager plateManager = null;
	Plate plate = new Plate();
	
	public String getManagerName() {
	    return "brunn";
	}
	
	public String run() {
		return "weehow";
	}
	
	public String getPlateByBarcode(String barcode) {
		if(plateManager == null) {
			plateManager = (IPlateManager) Springcontact.getBean("plateManager");
		}
		Collection<net.bioclipse.brunn.pojos.Plate> pojoPlates = plateManager.getAllPlates();
		for(net.bioclipse.brunn.pojos.Plate oldPlate : pojoPlates) {
			if(plate.getBarcode().equals(barcode)) {
				return plate.toString();
			}
			if(oldPlate.getBarcode().equals(barcode)) {
				PlateResults plateResults = new PlateResults(oldPlate,null);
				Set<net.bioclipse.brunn.pojos.Well> oldWells = oldPlate.getWells();
				Set<Well> wells = new HashSet<Well>();
				for(net.bioclipse.brunn.pojos.Well oldWell : oldWells) {
					Set<AbstractSample> samples = oldWell.getSampleContainer().getSamples();
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
						String name = oldWell.getName();
						char row = name.substring(0,1).charAt(0);
						int col = Integer.parseInt(name.substring(1));
						WellFunction wellFunction = new WellFunction(oldWellFunction.getName(), plateResults.getValue(col, row, oldWellFunction.getName()));//calculator.safeValueOf(oldWellFunction.getExpression()));
						wellFunctions.add(wellFunction);
					}
					wells.add(new Well(oldWell.getName(), cellType, cellName, wellFunctions, substances));
				}
				Set<net.bioclipse.brunn.pojos.PlateFunction> oldPlateFunctions = oldPlate.getPlateFunctions();
				Set<PlateFunction> plateFunctions = new HashSet<PlateFunction>();
				for(net.bioclipse.brunn.pojos.PlateFunction oldPlateFunction : oldPlateFunctions) {
					PlateFunction plateFunction = new PlateFunction(oldPlateFunction.getName(), plateResults.getValue(oldPlateFunction.getName()));//calculator.safeValueOf(oldPlateFunction.getExpression()));
					plateFunctions.add(plateFunction);
				}
				plate = new Plate(oldPlate.getName(), oldPlate.getBarcode(), plateFunctions, wells);
				return plate.toString();
			}
		}
		return "No plate with barcode "+barcode;
	}
	
	public List<String> getAllPlateBarcodes() {
		if(plateManager == null) {
			plateManager = (IPlateManager) Springcontact.getBean("plateManager");	
		}
		return plateManager.getAllPlateBarcodes();
	}
}
