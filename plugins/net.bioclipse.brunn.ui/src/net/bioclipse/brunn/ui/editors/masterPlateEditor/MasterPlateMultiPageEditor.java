package net.bioclipse.brunn.ui.editors.masterPlateEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.model.JasperCell;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.model.SampleSetCreater;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.util.FileUtil;
import net.bioclipse.core.util.LogUtils;
import net.bioclipse.jasper.editor.ReportEditor;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public class MasterPlateMultiPageEditor extends MultiPageEditorPart {

	private MasterPlateEditor masterPlateEditor;
	private ReportEditor masterPlateReport;
	private MasterPlate toBeSaved;
    private static Logger logger 
        = Logger.getLogger(MasterPlateMultiPageEditor.class);
	
	public final static String ID 
	    = "net.bioclipse.brunn.ui.editors.masterPlateEditor." +
	    		"MasterPlateMultiPageEditor"; 
	
	@Override
	protected void createPages() {
		
		net.bioclipse.brunn.ui.explorer.model.nonFolders.MasterPlate masterPlate =
			(net.bioclipse.brunn.ui.explorer.model.nonFolders.MasterPlate)getEditorInput();
		this.setPartName(masterPlate.getName()); 
		
		toBeSaved = ( (MasterPlate)masterPlate.getPOJO() ).deepCopy(); 
		
		masterPlateEditor = new MasterPlateEditor(toBeSaved);
		
		try {
			int index = this.addPage((IEditorPart) masterPlateEditor, getEditorInput());
			setPageText(index, "Overview");
			this.setActivePage(index);
		} 
		catch (PartInitException e) {
			e.printStackTrace();
		}

		masterPlateReport = new ReportEditor();

		try {
			int index = this.addPage((IEditorPart) masterPlateReport, getEditorInput());
			setPageText(index, "Report");
		} 
		catch (PartInitException e) {
			e.printStackTrace();			
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		masterPlateEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void pageChange(int newPageIndex){
	    try {
	        if(newPageIndex == 1) {
	            String reportPath 
	                = FileUtil.getFilePath( 
	                      "reports/masterplate384.jasper", 
	                      net.bioclipse.brunn.ui.Activator.PLUGIN_ID );
	            String basePath 
	                = FileUtil.getFilePath( 
	                      "reports/", 
	                      net.bioclipse.brunn.ui.Activator.PLUGIN_ID );
	            HashMap<String, String> parameters 
	                = new HashMap<String, String>();
	            parameters.put("DS_BASE_PATH",basePath);

	            masterPlateReport.openReport( 
	                                  reportPath, 
	                                  parameters, 
	                                  getCellsCollection() );
	        }
	    }
        catch ( Exception e ) {
            LogUtils.handleException( e, logger , "net.bioclipse.brunn.ui" );
        }
	}
	
	@SuppressWarnings({ "rawtypes" })
    public Collection<JasperCell> getCellsCollection() {
        List<Well> wells = new ArrayList<Well>( 
                                   masterPlateEditor.getCurrentMasterPlate()
                                                    .getWells() );
        Collections.sort( wells, new Comparator<Well>() {

            @Override
            public int compare( Well w1, Well w2 ) {

                if ( w1.getCol() != w2.getCol() ) {
                    return w1.getCol() - w2.getCol();
                }
                return w1.getRow() - w2.getRow();
            }
            
        });
        Collection<JasperCell> result = new ArrayList<JasperCell>();
        for ( Well w : wells ) {
            JasperCell c = new JasperCell();
            c.setCol( w.getCol() + "" );
            c.setRow( w.getRow() + "" );
            StringBuilder substances     = new StringBuilder();
            StringBuilder concentrations = new StringBuilder();
            StringBuilder units          = new StringBuilder();
            StringBuilder markers        = new StringBuilder();
            int i = 0, 
                j = 0;
            for ( SampleMarker sm : w.getSampleMarkers() ) {
                markers.append( sm.getName() );
                if ( sm.getSample() instanceof DrugSample ) {
                    substances.append( 
                        sm.getSample().getName() );
                    concentrations.append( 
                        ((DrugSample)sm.getSample()).getConcentration() );
                    units.append( 
                        ((DrugSample)sm.getSample()).getConcUnit().toString() );
                    
                    if ( i++ > 0 ) {
                        substances.    append( ',' ).append( ' ' );
                        concentrations.append( ',' ).append( ' ' );
                        units.         append( ',' ).append( ' ' );
                    }
                }
                if ( j++ > 0 ) {
                    markers.append( ',' ).append( ' ' );
                }
            }
            c.setSubstances(     substances.toString()     );
            c.setConcentrations( concentrations.toString() );
            c.setUnits(          units.toString()          );
            if ( "".equals( c.getSubstances() ) ) {
                c.setConcentrations( markers.toString() );
            }
            result.add( c );
        }
        return result;
    }

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}
	
//	public String[][] getSubstanceNames() {
//		return masterPlateEditor.getSubstanceNames();
//	}
	
//	public String[][] getMasterPlateLayout() {
//		return masterPlateEditor.getMasterPlateLayout();
//	}
}
