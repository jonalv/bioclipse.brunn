/* 
 * Copyright (c) 2010  Jonathan Alvarsson <jonalv@users.sourceforge.net>
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package net.bioclipse.brunn.ui.editors.plateEditor;

import net.bioclipse.brunn.pojos.Plate;


/**
 * @author jonalv
 *
 */
public interface IPlateExportAction {

    /**
     * Run the actual export action
     * 
     * @param plate
     */
    public void run(Plate plate);
    
    /**
     * @return the name of the export action
     */
    public String getName();
    
}
