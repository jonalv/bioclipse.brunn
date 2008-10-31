 /*******************************************************************************
 * Copyright (c) 2008 The Bioclipse Project and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ola Spjuth
 *     Stefan Kuhn
 *     Jonathan Alvarsson
 *
 ******************************************************************************/
package net.bioclipse.brunn.business;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import net.bioclipse.brunn.business.plate.PlateManager;
import net.bioclipse.core.PublishedClass;
import net.bioclipse.core.PublishedMethod;
import net.bioclipse.core.Recorded;
import net.bioclipse.core.ResourcePathTransformer;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.business.IBioclipseManager;
import net.bioclipse.core.domain.IMolecule;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

@PublishedClass( "Contains Brunn related methods")
public interface IBrunnManager extends IBioclipseManager {
	
    @Recorded
    @PublishedMethod( methodSummary = "Outputs weehow")
	public String run();

    @Recorded
    @PublishedMethod( methodSummary = "Returns the plate with that barcode")
	public String getPlateByBarcode(String barcode);

    @Recorded
    @PublishedMethod( methodSummary = "Returns the plate with that barcode")
    public List<String> getAllPlateBarcodes();
}