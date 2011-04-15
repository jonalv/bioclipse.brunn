package net.bioclipse.brunn.ui.dialogs;

import java.util.Collection;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.genericDAO.IMasterPlateDAO;
import net.bioclipse.brunn.pojos.AbstractBasePlate;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.PlateLayout;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class CreateMasterPlate extends TitleAreaDialog {

	private Text numOfPlatesText;
	private Combo comboMasterPlate;
	private Combo comboPlateLayout;
	private Text text;
	private PlateLayout selectedPlateLayout;
	
	private String name;
	private int numOfPlates;
	
	private PlateLayout[] plateLayouts = new PlateLayout[0];
	private Button masterPlateButton;
	private Button plateLayoutButton;
	private MasterPlate[] masterPlates = new MasterPlate[0];
	private MasterPlate selectedMasterPlate;

	public MasterPlate getSelectedMasterPlate() {
		return selectedMasterPlate;
	}

	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public CreateMasterPlate(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FormLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Group basedUponGroup = new Group(container, SWT.NONE);
		basedUponGroup.setLayout(new FormLayout());
		final FormData fd_basedUponGroup = new FormData();
		fd_basedUponGroup.bottom = new FormAttachment(0, 149);
		fd_basedUponGroup.top = new FormAttachment(0, 22);
		fd_basedUponGroup.right = new FormAttachment(0, 364);
		fd_basedUponGroup.left = new FormAttachment(0, 50);
		basedUponGroup.setLayoutData(fd_basedUponGroup);
		basedUponGroup.setText("Based upon");

		comboPlateLayout = new Combo(basedUponGroup, SWT.NONE|SWT.READ_ONLY);
		final FormData fd_comboPlateLayout = new FormData();
		fd_comboPlateLayout.bottom = new FormAttachment(0, 40);
		fd_comboPlateLayout.right = new FormAttachment(0, 268);
		fd_comboPlateLayout.left = new FormAttachment(0, 144);
		comboPlateLayout.setLayoutData(fd_comboPlateLayout);
		
		//populate the comboMasterPlate
		IPlateLayoutManager plm  = (IPlateLayoutManager) Springcontact.getBean("plateLayoutManager");
		try {
		    plateLayouts = plm.getAllPlateLayoutsNotDeleted().toArray(plateLayouts);
		}
		catch (Exception e) {
	        MessageDialog.openError( 
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
	            "An error has occured", 
	            "Could not load plate layouts. Maybe a missing refresh. "
	            + "If nothing else works please restart." );
        } 
		String[] items = new String[plateLayouts.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = plateLayouts[i].getName();
		}
		comboPlateLayout.setItems(items);

		//populate the comboMasterPlate
		IPlateManager pm  = (IPlateManager) Springcontact.getBean("plateManager");

		try {
		    masterPlates = pm.getAllMasterPlates().toArray(masterPlates);
		}
		catch (Exception e) {
	        MessageDialog.openError( 
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
	            "An error has occured", 
	            "Could not load master plates. Maybe a missing refresh. "
	            + "If nothing else works please restart." );
        } 
		String[] itemsMasterPlate = new String[masterPlates.length];
		for (int i = 0; i < itemsMasterPlate.length; i++) {
			itemsMasterPlate[i] = masterPlates[i].getName();
		}
		
		comboMasterPlate = new Combo(basedUponGroup, SWT.NONE|SWT.READ_ONLY);
		comboMasterPlate.setItems(itemsMasterPlate);
		
		final FormData fd_comboMasterPlate = new FormData();
		fd_comboMasterPlate.bottom = new FormAttachment(0, 86);
		fd_comboMasterPlate.right = new FormAttachment(0, 268);
		fd_comboMasterPlate.left = new FormAttachment(0, 144);
		comboMasterPlate.setLayoutData(fd_comboMasterPlate);
		

		plateLayoutButton = new Button(basedUponGroup, SWT.RADIO);
		final FormData fd_plateLayoutButton = new FormData();
		fd_plateLayoutButton.bottom = new FormAttachment(comboPlateLayout, 0, SWT.BOTTOM);
		fd_plateLayoutButton.left = new FormAttachment(0, 25);
		plateLayoutButton.setLayoutData(fd_plateLayoutButton);
		plateLayoutButton.setText("Plate Layout:");

		masterPlateButton = new Button(basedUponGroup, SWT.RADIO);
		final FormData fd_masterPlateButton = new FormData();
		fd_masterPlateButton.right = new FormAttachment(plateLayoutButton, 0, SWT.RIGHT);
		fd_masterPlateButton.bottom = new FormAttachment(comboMasterPlate, 0, SWT.BOTTOM);
		masterPlateButton.setLayoutData(fd_masterPlateButton);
		masterPlateButton.setText("Master Plate:");
		
		plateLayoutButton.setSelection(true);

		final Label nameLabel = new Label(container, SWT.NONE);
		final FormData fd_nameLabel = new FormData();
		nameLabel.setLayoutData(fd_nameLabel);
		nameLabel.setText("Name:");

		text = new Text(container, SWT.BORDER);
		fd_nameLabel.bottom = new FormAttachment(text, 0, SWT.BOTTOM);
		fd_nameLabel.right = new FormAttachment(text, -5, SWT.LEFT);
		final FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0, 228);
		fd_text.right = new FormAttachment(0, 326);
		fd_text.left = new FormAttachment(0, 202);
		text.setLayoutData(fd_text);

		numOfPlatesText = new Text(container, SWT.BORDER);
		final FormData fd_numOfPlatesText = new FormData();
		fd_numOfPlatesText.top = new FormAttachment(0, 185);
		fd_numOfPlatesText.right = new FormAttachment(0, 326);
		fd_numOfPlatesText.left = new FormAttachment(0, 202);
		numOfPlatesText.setLayoutData(fd_numOfPlatesText);

		final Label nameLabel_1 = new Label(container, SWT.NONE);
		final FormData fd_nameLabel_1 = new FormData();
		fd_nameLabel_1.bottom = new FormAttachment(numOfPlatesText, 0, SWT.BOTTOM);
		fd_nameLabel_1.right = new FormAttachment(numOfPlatesText, -5, SWT.LEFT);
		nameLabel_1.setLayoutData(fd_nameLabel_1);
		nameLabel_1.setText("Number of plates in batch:");
		setTitle("Create Master Plate");
		//
		if ( comboPlateLayout.getItemCount() > 0 ) {
		    comboPlateLayout.select( 0 );
		}
		return area;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(397, 427);
	}
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			if (plateLayoutButton.getSelection()) {
			    if ( comboPlateLayout.getSelectionIndex() == -1 ) {
			        MessageDialog.openInformation( 
			            PlatformUI.getWorkbench()
			                      .getActiveWorkbenchWindow()
			                      .getShell(), 
			            "Select a plate layout", 
			            "Select a plate layout to base the masterplate on" );
			        return;
			    }
				name = text.getText();
				selectedPlateLayout = plateLayouts[comboPlateLayout.getSelectionIndex()];
				try {
					numOfPlates = Integer.parseInt(numOfPlatesText.getText());
				}
				catch (NumberFormatException e) {
					MessageDialog.openInformation( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
							                       "Could not parse number of plates",
							                       "Number of plates needs to be an int \n" + 
							                       e.getMessage() );
					return;
				}
			} else {
			    if ( comboMasterPlate.getSelectionIndex() == -1 ) {
			        MessageDialog.openInformation( 
			            PlatformUI.getWorkbench()
			                      .getActiveWorkbenchWindow()
			                      .getShell(), 
			            "Select a master plate", 
			            "Select a master plate to base the masterplate on" );
			        return;
			    }
				name = text.getText();
				selectedMasterPlate = masterPlates[comboMasterPlate.getSelectionIndex()];
				try {
					numOfPlates = Integer.parseInt(numOfPlatesText.getText());
				}
				catch (NumberFormatException e) {
					MessageDialog.openInformation( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
							                       "Could not parse number of plates",
							                       "Number of plates needs to be an int \n" + 
							                       e.getMessage() );
					return;
				}
			
			}
		} 
		super.buttonPressed(buttonId);
	}

	public String getName() {
		return name;
	}

	public PlateLayout getSelectedPlateLayout() {
		return selectedPlateLayout;
	}

	public int getNumOfPlates() {
		return numOfPlates;
	}
	
	public boolean userPickedPlateLayout() {
		return (selectedMasterPlate == null);
	}
}
