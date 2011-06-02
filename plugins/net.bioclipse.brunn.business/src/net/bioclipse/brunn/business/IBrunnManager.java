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

import net.bioclipse.brunn.business.domain.Plate;
import net.bioclipse.core.PublishedClass;
import net.bioclipse.core.PublishedMethod;
import net.bioclipse.managers.business.IBioclipseManager;

@PublishedClass(
    value="Retrieves data from Brunn.",
    doi={"10.1186/1471-2105-12-179"}
)


public interface IBrunnManager extends IBioclipseManager {
	@PublishedMethod(params = "String barcode",methodSummary="Retrieves a Plate from Brunn by barcode.")
	Plate getPlateByBarcode(String barcode);

}
