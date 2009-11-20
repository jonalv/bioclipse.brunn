/*******************************************************************************
 * Copyright (c) 2009  Claes Andersson <claes.andersson@medsci.uu.se>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: Bioclipse Project <http://www.bioclipse.net>
 ******************************************************************************/
package net.bioclipse.brunn.business.tests;

import net.bioclipse.core.tests.AbstractManagerTest;
import net.bioclipse.managers.business.IBioclipseManager;
import net.bioclipse.brunn.business.IBrunnManager;
import net.bioclipse.brunn.business.BrunnManager;

/**
 * JUnit tests for checking if the tested Manager is properly tested.
 * 
 * @author egonw
 */
public class APITest extends AbstractManagerTest {
    
    private static BrunnManager manager = new BrunnManager();

    @Override
    public IBioclipseManager getManager() {
        return manager;
    }

    @Override
    public Class<? extends IBioclipseManager> getManagerInterface() {
        return IBrunnManager.class;
    }

}