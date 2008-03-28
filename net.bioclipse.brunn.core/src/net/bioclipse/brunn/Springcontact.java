package net.bioclipse.brunn;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.bioclipse.keyring.AccountType;
import net.bioclipse.keyring.KeyRing;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.tools.ant.taskdefs.BUnzip2;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Springcontact {
	
	private final static String PLUGIN_ID = "net.bioclipse.brunn";
	public static ApplicationContext CONTEXT;
	
	public static Object getBean(String beanName){
		
		if (CONTEXT == null) {
			rebuildContext();
		}
		return CONTEXT.getBean(beanName);
	}
	
	public static URL getPluginURL() {
		try {
	        return FileLocator.toFileURL(Platform.getBundle(PLUGIN_ID).getEntry("/"));
        }
        catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        return null;
	}

	public static void rebuildContext() {
		try {
			CONTEXT = new FileSystemXmlApplicationContext( getPluginURL() + 
					                                       "src" +
			                                               File.separator + 
			                                               "applicationContext.xml" );
			
			BasicDataSource dataSource = (BasicDataSource) CONTEXT.getBean("dataSource");
			dataSource.setUrl( KeyRing.getInstance().getPropertyByAccountType("LisAccountType", "URL") );
			dataSource.setUsername( KeyRing.getInstance().getPropertyByAccountType("LisAccountType", "Database user") );
			dataSource.setPassword( KeyRing.getInstance().getPropertyByAccountType("LisAccountType", "Database password") );
			if( !KeyRing.getInstance().isLoggedInWithAccountType("LisAccountType") ) {
				throw new IllegalStateException("not logged in or the logged in user lacks LisAccount");
			}
		}
		//not running from the lis_gui project but rather running test code
		//TODO: this is ugly with a special case for running tests figure out something else maybee?
		catch(NullPointerException e) {
			CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
    }
}
