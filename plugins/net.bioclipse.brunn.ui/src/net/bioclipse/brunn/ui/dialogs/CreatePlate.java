package net.bioclipse.brunn.ui.dialogs;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.genericDAO.IMasterPlateDAO;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.CellType;
import net.bioclipse.core.util.LogUtils;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class CreatePlate extends TitleAreaDialog {

	private Combo patientCellCombo;
	private Text text;
	private Text  barcodeText;
	private Combo cellTypeCombo;
	private Combo masterPlateCombo;
	private Text  nameText;
	
	private String          barCode;
	private String          name;
	private CellOrigin      cellOrigin;
	private MasterPlate     masterPlate;
	private MasterPlate[]   masterPlates = new MasterPlate[0];
	private CellOrigin[]    cellOrigins  = new  CellOrigin[0];
	private PatientOrigin[] patientCells = new PatientOrigin[0];
	private PatientOrigin   patientOrigin;
	private Button cellLineButton;
	private boolean doingPatientCells;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public CreatePlate(Shell parentShell) {
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

		final Label nameLabel = new Label(container, SWT.NONE);
		final FormData formData = new FormData();
		nameLabel.setLayoutData(formData);
		nameLabel.setText("Name:");

		Label masterPlateLabel;
		masterPlateLabel = new Label(container, SWT.NONE);
		final FormData formData_1 = new FormData();
		masterPlateLabel.setLayoutData(formData_1);
		masterPlateLabel.setText("Master Plate:");

		final Label nameLabel_1 = new Label(container, SWT.NONE);
		final FormData formData_3 = new FormData();
		nameLabel_1.setLayoutData(formData_3);
		nameLabel_1.setText("Defrosting date:");

		final Label barcodeLabel = new Label(container, SWT.NONE);
		final FormData formData_4 = new FormData();
		formData_4.right = new FormAttachment(nameLabel_1, 0, SWT.RIGHT);
		barcodeLabel.setLayoutData(formData_4);
		barcodeLabel.setText("Barcode:");

		nameText = new Text(container, SWT.BORDER);
		formData.bottom = new FormAttachment(nameText, 0, SWT.BOTTOM);
		formData.left = new FormAttachment(nameText, -36, SWT.LEFT);
		formData.right = new FormAttachment(nameText, -5, SWT.LEFT);
		final FormData formData_5 = new FormData();
		formData_5.right = new FormAttachment(100, -125);
		formData_5.left = new FormAttachment(0, 160);
		formData_5.bottom = new FormAttachment(0, 55);
		nameText.setLayoutData(formData_5);

		masterPlateCombo = new Combo(container, SWT.READ_ONLY);
		formData_1.top = new FormAttachment(masterPlateCombo, -17, SWT.BOTTOM);
		formData_1.bottom = new FormAttachment(masterPlateCombo, 0, SWT.BOTTOM);
		formData_1.right = new FormAttachment(masterPlateCombo, -5, SWT.LEFT);
		final FormData formData_6 = new FormData();
		formData_6.right = new FormAttachment(nameText, -3, SWT.RIGHT);
		formData_6.bottom = new FormAttachment(0, 100);
		formData_6.left = new FormAttachment(nameLabel, 5, SWT.RIGHT);
		masterPlateCombo.setLayoutData(formData_6);
	
		cellTypeCombo = new Combo(container, SWT.READ_ONLY);
		final FormData formData_7 = new FormData();
		formData_7.right = new FormAttachment(nameText, -3, SWT.RIGHT);
		formData_7.bottom = new FormAttachment(0, 152);
		formData_7.left = new FormAttachment(nameText, 1, SWT.LEFT);
		cellTypeCombo.setLayoutData(formData_7);
		
		barcodeText = new Text(container, SWT.BORDER);
		formData_4.bottom = new FormAttachment(barcodeText, 0, SWT.BOTTOM);
		final FormData formData_8 = new FormData();
		formData_8.right = new FormAttachment(100, -370);
		formData_8.bottom = new FormAttachment(0, 292);
		formData_8.left = new FormAttachment(cellTypeCombo, 0, SWT.LEFT);
		barcodeText.setLayoutData(formData_8);
		setTitle("Create Plate");
		
		Button patientCellButton;
		patientCellButton = new Button(container, SWT.RADIO);
		formData_3.right = new FormAttachment(patientCellButton, 0, SWT.RIGHT);
		patientCellButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				cellTypeCombo.setEnabled(false);
				patientCellCombo.setEnabled(true);
			}
		});
		final FormData fd_patientCellButton = new FormData();
		fd_patientCellButton.right = new FormAttachment(masterPlateLabel, 0, SWT.RIGHT);
		patientCellButton.setLayoutData(fd_patientCellButton);
		patientCellButton.setText("Patient cell:");
		
		patientCellCombo = new Combo(container, SWT.READ_ONLY);
		fd_patientCellButton.bottom = new FormAttachment(patientCellCombo, 0, SWT.BOTTOM);
		patientCellCombo.setEnabled(false);
		final FormData fd_patientCellCombo = new FormData();
		fd_patientCellCombo.right = new FormAttachment(nameText, -3, SWT.RIGHT);
		fd_patientCellCombo.bottom = new FormAttachment(0, 195);
		fd_patientCellCombo.left = new FormAttachment(cellTypeCombo, 0, SWT.LEFT);
		patientCellCombo.setLayoutData(fd_patientCellCombo);
		
		/*
		 * populate the comboboxes
		 */
		IPlateManager pm  = (IPlateManager) Springcontact.getBean("plateManager");
		try {
		    masterPlates = pm.getAllMasterPlatesNotDeleted().toArray(masterPlates);
		}
		catch (Exception e) {
		    LogUtils.debugTrace( Logger.getLogger( this.getClass() ), e );
		    MessageDialog.openInformation( 
		        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
		        "Ooops",
		        "Something went wrong. There might be a missing refresh " +
		            "or so. If nothing else works a restart will most " +
		            "likely fix things. \n\n"
		        + "The error was: " + e.getClass().getSimpleName() + ": " 
		        + e.getMessage() );
		}
        Arrays.sort(masterPlates, new Comparator<MasterPlate>() {
            public int compare(MasterPlate a, MasterPlate b)
               {
               return(a.getName()).compareToIgnoreCase(b.getName());
               }
            });
		String[] items = new String[masterPlates.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = masterPlates[i].getName();
		}
		masterPlateCombo.setItems(items);
		masterPlateCombo.select(0);
		
		IOriginManager om  = (IOriginManager) Springcontact.getBean("originManager");
		cellOrigins = om.getAllCellOriginsNotDeleted().toArray(cellOrigins);
		items = new String[cellOrigins.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = cellOrigins[i].getName();
		}
		cellTypeCombo.setItems(items);
		cellTypeCombo.select(0);
		
		patientCells = om.getAllPatientOriginsNotDeleted().toArray(patientCells);
		items = new String[patientCells.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = patientCells[i].getName();
		}
		patientCellCombo.setItems(items);
		patientCellCombo.select(0);

		text = new Text(container, SWT.BORDER);
		formData_3.bottom = new FormAttachment(text, 0, SWT.BOTTOM);
		text.setEnabled(false);
		text.setText("For now, set to current time, later here will be "
                   + "some sort of datetime picker.");
		final FormData formData_9 = new FormData();
		formData_9.right = new FormAttachment(nameText, 1, SWT.RIGHT);
		formData_9.top = new FormAttachment(0, 220);
		formData_9.left = new FormAttachment(cellTypeCombo, 0, SWT.LEFT);
		text.setLayoutData(formData_9);

		cellLineButton = new Button(container, SWT.RADIO);
		cellLineButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				cellTypeCombo.setEnabled(true);
				patientCellCombo.setEnabled(false);
			}
		});
		cellLineButton.setSelection(true);
		final FormData fd_cellLineButton = new FormData();
		fd_cellLineButton.bottom = new FormAttachment(cellTypeCombo, 0, SWT.BOTTOM);
		fd_cellLineButton.right = new FormAttachment(cellTypeCombo, -5, SWT.LEFT);
		cellLineButton.setLayoutData(fd_cellLineButton);
		cellLineButton.setText("Cell line:");

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
		return new Point(653, 499);
	}

	public String getBarCode() {
		return barCode;
	}

	public CellOrigin getCellOrigin() {
		return cellOrigin;
	}

	public MasterPlate getMasterPlate() {
		return masterPlate;
	}

	public String getName() {
		return name;
	}
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			this.barCode           = barcodeText.getText();
			this.name              = nameText.getText();
			this.masterPlate       = masterPlates[ masterPlateCombo.getSelectionIndex() ];
			this.doingPatientCells = !cellLineButton.getSelection();
			if( doingPatientCells ) {
				this.patientOrigin = patientCells[ patientCellCombo.getSelectionIndex() ];
			}
			else {
				this.cellOrigin = cellOrigins[ cellTypeCombo.getSelectionIndex() ];
			}
		}
		super.buttonPressed(buttonId);
	}

	public Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis()); //TODO change inte value from a datatimepicker
	}

	public PatientOrigin getPatientOrigin() {
		return patientOrigin;
	}

	public boolean isDoingPatientCells() {
		return doingPatientCells;
	}
}
