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
package net.bioclipse.brunn.business.tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import net.bioclipse.core.ResourcePathTransformer;
import net.bioclipse.usermanager.AccountType;
import net.bioclipse.usermanager.Activator;
import net.bioclipse.usermanager.business.IUserManager;
import net.bioclipse.usermanager.business.UserManager;
import net.bioclipse.brunn.business.IBrunnManager;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public abstract class AbstractBrunnManagerPluginTest {

    protected static IBrunnManager brunn;
    
    static IUserManager userManager;
    
    @BeforeClass public static void login() {
    	userManager = Activator.getDefault().getUserManager();
    	userManager.deleteUser("me");
    	userManager.createUser("me", "me");
    	userManager.logIn("me", "me");
    	
		AccountType brunnType=null;
			    	
    	for ( AccountType t : userManager.getAvailableAccountTypes() ) {
    		if (t.getName().equals("BrunnAccountType")) {
    	    	brunnType = t;
    		}
    	}
    	
    	userManager.createAccount(
    			"brunn", 
    			new HashMap() {{
    				put("Database password","qu!ss89");
    				put("Brunn user", "Administrator");
    				put("Brunn password", "masterkey");
    				put("Database user", "brunn");
    				put("URL", "jdbc:mysql://malinda.medsci.uu.se/brunn");
    				
    		    }}, 
    		    brunnType);
    	userManager.logOut();
    	userManager.logIn("me", "me");
    }
    
    @Test public void testIsLoggedIn() {
    	assertTrue(userManager.isLoggedIn());
    }
    
    @Test public void testFetchPlate() {
    	assertNotNull( brunn.getPlateByBarcode("6271"));
    }

}
