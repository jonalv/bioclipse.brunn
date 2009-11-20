/*******************************************************************************
 * Copyright (c) 2009  Claes Andersson <claes.andersson@medsci.uu.se>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://www.bioclipse.net/
 ******************************************************************************/
package net.bioclipse.brunn.business;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.domain.Plate;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.managers.business.IBioclipseManager;

import org.apache.log4j.Logger;

public class BrunnManager implements IBioclipseManager {

    private static final Logger logger = Logger.getLogger(BrunnManager.class);
	private IPlateManager plateManager;

    /**
     * Gives a short one word name of the manager used as variable name when
     * scripting.
     */
    public String getManagerName() {
        return "brunn";
    }
    
    public Plate getPlateByBarcode(String barcode) {
		if(plateManager == null) {
			plateManager = (IPlateManager) Springcontact.getBean("plateManager");
		}
		
		Plate returnedPlate = new Plate();
		
		net.bioclipse.brunn.pojos.Plate brunnPlate = plateManager.getPlate(barcode);
		
		returnedPlate.setName(brunnPlate.getName());
		
		return returnedPlate;
    }
}
