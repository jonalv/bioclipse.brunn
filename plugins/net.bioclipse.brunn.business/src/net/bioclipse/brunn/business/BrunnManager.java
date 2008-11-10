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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plate.PlateManager;
import net.bioclipse.brunn.domain.Creator;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.core.ResourcePathTransformer;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.BioList;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.core.jobs.Job;
import net.bioclipse.core.util.LogUtils;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * The manager class for Brunn. Contains Brunn related methods.
 * 
 * @author jonas
 * 
 */
public class BrunnManager implements IBrunnManager {

	private static final Logger logger = Logger.getLogger(BrunnManager.class);
	Creator creator = new Creator();
	
	public String getNamespace() {
		return "brunn";
	}

	public String run() {
		creator.generalStyle();
		return "weehow";
	}
	
	public String getPlateByBarcode(String barcode) {
		//IPlateManager plateManager = (IPlateManager) Springcontact.getBean("plateManager");
		//return plateManager.getPlate(barcode).getName();
		return creator.getPlateByBarcode(barcode);
	}
	
	public Set<String> getAllPlateBarcodes() {
		/*IPlateManager plateManager = (IPlateManager) Springcontact.getBean("plateManager");
		List<String> barcodes = plateManager.getAllPlateBarcodes();
		for(String s: barcodes) {
			getPlateByBarcode(s);
		}*/
		return creator.getAllPlateBarcodes();
	}
}
