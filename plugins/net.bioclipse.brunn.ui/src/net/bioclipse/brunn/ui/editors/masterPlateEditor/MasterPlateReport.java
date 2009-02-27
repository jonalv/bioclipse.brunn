package net.bioclipse.brunn.ui.editors.masterPlateEditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import net.bioclipse.brunn.ui.editors.plateEditor.PlateReport;
import net.bioclipse.ui.BioclipseCache;

import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class MasterPlateReport extends EditorPart{
	
	private MasterPlateEditor masterPlateEditor;
	private String[][] substances;
	private String[][] masterPlateLayout;
	private String[][] markerLayout;
	private int size;
	
	public MasterPlateReport(/*MasterPlateMultiPageEditor masterPlateMultiPageEditor,*/ MasterPlateEditor masterPlateEditor) {
		super();
		this.masterPlateEditor = masterPlateEditor;
		substances = masterPlateEditor.getSubstanceNames();
		masterPlateLayout = masterPlateEditor.getMasterPlateLayout();
		markerLayout = new String[masterPlateLayout.length][masterPlateLayout[0].length];
		size = (masterPlateLayout.length-1)*(masterPlateLayout[0].length-1);
	}
	
	private void printDataSetToFile(String[][] Array, String filename) {
		convertMasterPlateLayout();
		try {
			File folder = BioclipseCache.getCacheDir();
			PrintWriter printWriter = new PrintWriter(new FileWriter(folder+File.separator+filename));
			for(int i=0; i<Array[0].length; i++) {
				printWriter.write(Array[0][i]+(i<Array.length-1?",":"Plate Name\n"));
			}
			for(int i=0; i<Array.length; i++) {
				for(int j=0; j<Array[0].length; j++) {
					printWriter.write(Array[i][j]+(j<Array.length-1?",":masterPlateEditor.getMasterPlateName()+"\n"));
				}
			}
			printWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void convertMasterPlateLayout() {
		for(int i=0; i<masterPlateLayout.length; i++) {
			for(int j=0; j<masterPlateLayout[0].length; j++) {
				String ij = masterPlateLayout[i][j];
				if(ij.startsWith("M")) {
					String[] ij2 = ij.split(" ",2);
					markerLayout[i][j] = ij2[0];
					masterPlateLayout[i][j] = ij2[1];	
				}
				else if(ij.startsWith("[a-h]")) {
					markerLayout[i][j] = ij;
				}
			}
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// TODO Auto-generated method stub
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void changeFile(String fileName, String from, String to) {
		URL url = null;
        try {
            url = FileLocator.toFileURL(PlateReport.class.getResource(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		File file = new File(url.getFile());
		FileWriter out;
		try {
			String fileAsString = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String fileLine;
			while ((fileLine = reader.readLine()) != null){
				if( fileLine.contains(from) ) {
					fileLine = fileLine.substring(0, fileLine.indexOf(from) ) + to + "</property>";
				}
				fileAsString += fileLine+"\n";
			}
			reader.close();
			
			out = new FileWriter(file);
			out.write(fileAsString);
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		printDataSetToFile(substances,"substances.csv");
		printDataSetToFile(masterPlateLayout,"masterPlateLayout.csv");
		printDataSetToFile(markerLayout,"markerLayout.csv");
		URL url = null;
        try {
        	if(size == 96) {
                url = FileLocator.toFileURL( PlateReport.class.getResource( "masterPlateReport96.rptdesign" ) );	
        	}
        	if(size == 384) {
                url = FileLocator.toFileURL( PlateReport.class.getResource( "masterPlateReport384.rptdesign" ) );
        	}
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }
        try {
        	if(size == 96) {
    			changeFile("masterPlateReport96.rptdesign","/home/jonas/runtime-bioclipse.product/tmp",BioclipseCache.getCacheDir().getAbsolutePath());	
        	}
        	if(size == 384) {
    			changeFile("masterPlateReport384.rptdesign","/home/jonas/runtime-bioclipse.product/tmp",BioclipseCache.getCacheDir().getAbsolutePath());
        	}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Browser browser = new Browser(parent, SWT.NONE);
		WebViewer.display(url.getFile(), WebViewer.HTML, browser, "frameset");
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
