package net.bioclipse.brunn.ui.editors.masterPlateEditor.masterPlateWellEditor;


import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.MasterPlateEditor;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.model.MarkersModel;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.masterPlateWellEditor.MasterPlateWellEditorDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

import de.kupzog.ktable.editors.TableCellEditorDialog;

public class MasterPlateWellEditor extends TableCellEditorDialog {

	public Well well;
	private MasterPlateEditor masterPlateEditor;
	
	public MasterPlateWellEditor(Well well, MasterPlateEditor masterPlateEditor) {
		this.well = well;
		this.masterPlateEditor = masterPlateEditor;
	}
	
	@Override
	public Dialog getDialog(Shell shell) {
	
		return new MasterPlateWellEditorDialog(shell, well, masterPlateEditor );
	}

	@Override
	public void setupShellProperties(Shell dialogShell) {
		// TODO Auto-generated method stub
		
	}

}
